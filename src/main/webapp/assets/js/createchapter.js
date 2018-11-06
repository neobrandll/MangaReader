document.getElementById("crtchapter").addEventListener("click", create)

function create(){
    var data = {
        message : document.getElementById("numchap").value
    }
    var init ={
        method: 'POST',
        body: JSON.stringify(data)
    }
    fetch('http://localhost:8080/NitroReader/Chapter',init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        console.log(res.message)
       localStorage.setItem("currentChap", res.message)
       window.location.href ="EditChapter.html"
    })
}

function loadnumchapter(){
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter?option=getnumchapters',init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        var count = Object.keys(res).length;
        console.log(res)
        for(let i=0; i<count ; i++){
            let a = document.createElement("a");
            a.setAttribute("id", "capitulo"+ res["nombre"+i])
            a.setAttribute("class", "dropdown-item")
            a.setAttribute("href", "#")
            a.textContent = res["nombre"+i];
            a.addEventListener("click",function(){
                localStorage.setItem("currentChap", res["nombre"+i])
                window.location.href ="Reader.html"
                 })
            document.getElementById("dropmenu").appendChild(a);
        }
        
    })
    
    
}