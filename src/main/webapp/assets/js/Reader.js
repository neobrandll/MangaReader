var mainimg = document.getElementById("mainimg")
var max;
var filedir
var currentP= 1
var chapters;
var readerChapter;


 function getchapter(pages){
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter?option=getchapter&currentChap='+localStorage.currentChap+"&mangaid="+localStorage.mangaid,init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        console.log(res)
        max = res.max;
        if(max>0){
            switch(localStorage.estadoReader){
                case "up":
                currentP=1
                filedir= res.filedir;
                 mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
                break;
                case "down":
                currentP = max
                filedir= res.filedir;
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
                break;
                case "tracker":
                let chapid = getChapter_id();
                currentP = pages["p"+chapid];
                filedir= res.filedir;
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
                break;
            }
            
        }else{
            alert("este capitulo se encuentra vacio!")
            chapterfinished(localStorage.mangaid, getChapter_id(), currentP)
        }
        
        
    })
    
    
}




document.addEventListener("keydown", function(e){
    switch(e.keyCode){
        case 37:
            if(currentP > 1 ){
                currentP--
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }else{
                ordenChap()
                if(chapters["nombre"+(readerChapter-1)]){
                    localStorage.setItem("estadoReader", "down") 
                    alert("redirigiendo al capitulo anterior")
                    localStorage.setItem("currentChap", chapters["nombre"+(readerChapter-1)])
                     window.location.href ="Reader.html"
                }
            }
            break;
        case 39:
            if(currentP < max){
                currentP++
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }else{
                ordenChap()
                if(chapters["nombre"+(readerChapter+1)]){
                    localStorage.setItem("estadoReader", "up") 
                    alert("capitulo finalizado, ha sido redirigido al siguiente capitulo")
                    chapterfinished(localStorage.mangaid, getChapter_id(), currentP)
                    localStorage.setItem("currentChap", chapters["nombre"+(readerChapter+1)])
                     window.location.href ="Reader.html"
                }else{
                    alert("capitulo finalizado, ha llegado al final del manga.")
                    mangafinished(localStorage.mangaid, true)
                    chapterfinished(localStorage.mangaid, getChapter_id(), currentP)
                }
                
            }
            break;
    }
})


function loadnumchapter(){
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter?option=getnumchapters&mangaid='+localStorage.mangaid,init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        var count =0
        for (key in res){
            if(key.indexOf("nombre")!= -1){
                count++
            }
        }
        chapters = res;
        for(let i=0; i<count ; i++){
            let a = document.createElement("a");
            a.setAttribute("id", res["id"+i])
            a.setAttribute("class", "dropdown-item")
            a.setAttribute("href", "#")
            a.textContent = res["nombre"+i];
            a.addEventListener("click",function(){
                localStorage.setItem("currentChap", res["nombre"+i])
                localStorage.setItem("estadoReader", "tracker")
                window.location.href ="Reader.html"
                 })
            document.getElementById("dropmenu").appendChild(a);
        }
        
    }).then(()=> startReader())
    
    
}

ordenChap = function(){
    var count =0
        for (key in chapters){
            if(key.indexOf("nombre")!= -1){
                count++
            }
        }
    for(let i=0; i<count; i++){
        if(localStorage.currentChap == chapters["nombre"+i]){
            readerChapter = i;
        }
    }
}

function mangafinished(manga_id, finished){
        let data = {manga_id: manga_id, finished: finished   }
        
    let init = {method:'POST', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}}

    fetch("http://localhost:8080/NitroReader/TrackerMangaServl",init)
    .then(res => res.json()).then((res) => {console.log(res.message)
    })
    }

function chapterfinished(manga_id , chapter_id, page_tracker){

    let data = {manga_id: manga_id,
                chapter_id: chapter_id,
                page_tracker: page_tracker ,
                finished: true                  
                                        }
    let init = {method:'POST', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}}

fetch("http://localhost:8080/NitroReader/TrackerChapterServl",init)
.then(res => res.json()).then((res) => {console.log(res.message)
})
    }

  function updateChapterTracker(manga_id, chapter_id, page_tracker){
    let data = {manga_id: manga_id,
        chapter_id: chapter_id,
        page_tracker: page_tracker ,
        finished: false                  
                                }
let init = {method:'POST', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}}

fetch("http://localhost:8080/NitroReader/TrackerChapterServl",init)
.then(res => res.json()).then((res) => {console.log(res.message)
})
  }


  document.getElementById("saveTracker").addEventListener("click",function(){
    updateChapterTracker(localStorage.mangaid, getChapter_id() , currentP)
  }) 


  function getChapter_id(){
   let tags = document.getElementById("dropmenu").getElementsByTagName("a")
   for(let i = 0; i<tags.length; i++){
       if(localStorage.currentChap == tags[i].textContent){
           return tags[i].getAttribute("id")
       }
   }
  }


  function startReader(){
    
    let tags = document.getElementById("dropmenu").getElementsByTagName("a")
    let count  = tags.length;
    let data={"numchaps":count, "manga_id": parseInt(localStorage.mangaid)  }
    for(let i = 0; i<count; i++){
        data["id"+i] = parseInt(tags[i].getAttribute("id"));
    }
    let init = {method:'POST', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}}
    console.log(init.body)
    fetch("http://localhost:8080/NitroReader/TrackerChapterGETServl",init)
    .then(res => res.json()).then((res) => {
        for(let i=0; i<count; i++){
           
            let id = tags[i].getAttribute("id")
            console.log(res)
            console.log(res[id])
            if(res[id] == true){
                document.getElementById(id).style.backgroundColor = "lightcoral"
            }
        }
        getchapter(res);
    })
  }