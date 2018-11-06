var mainimg = document.getElementById("mainimg")
var max;
var filedir
var currentP= 1

 function getchapter(){
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter?option=getchapter',init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        console.log(res)
        max = res.max;
        filedir= res.filedir;
        mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
        
    })
    
    
}




document.addEventListener("keydown", function(e){
    switch(e.keyCode){
        case 37:
            if(currentP > 1 ){
                currentP--
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }
            break;
        case 39:
            if(currentP < max){
                currentP++
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }
            break;
    }
})