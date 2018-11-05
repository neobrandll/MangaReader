var mainimg = document.getElementById("mainimg")
var max;
var filedir
var currentP= 1

function getchapter(){
    var URL = document.URL;
    location.href= URL + "?option=getchapter"
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter',init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        max = res.max;
        filedir= res.dir;
    })
    mainimg.setAttribute("src", filedir+"\\"+ currentP+".png");
}

document.addEventListener("keydown", function(e){
    switch(e.keyCode){
        case 37:
            if(currentP > 1 ){
                currentP--
                mainimg.setAttribute("src", filedir+"\\"+ currentP+".png");
            }
            break;
        case 39:
            if(currentP < max){
                currentP++
                mainimg.setAttribute("src", filedir+"\\"+ currentP+".png");
            }
            break;
        case 13:
        getchapter();
        
        break;
    }
})