document.getElementById("crtchapter").addEventListener("click", create)

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};
function getmangaid(){
    var mangaid = getUrlParameter("manga")
    localStorage.setItem("mangaid", mangaid)
}


function create(){
    var value = document.getElementById("numchap").value
    console.log(value)
    if( value > 0 && Number.isInteger(Number(value))){
        var data = {
            chapternum : value,
            mangaid : localStorage.mangaid
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
           localStorage.setItem("currentChap", res.chapternum)
           window.location.href ="EditChapter.html"
        })
    }else{
        alert("solo se permiten numeros enteros y mayores que 0")
    }
    
}

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
        console.log("numero de capitulos: " + count)
        console.log(res)
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
        
    })
    
    
}

