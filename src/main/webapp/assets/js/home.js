var loginbtn = document.getElementById("loginbtn")
var registerbtn= document.getElementById("registerbtn")
var logoutbtn = document.getElementById("logoutbtn")

//los eventos para el redireccionado
registerbtn.addEventListener("click", function(){window.location.href ="NitroRegister.html"})
document.getElementById("homebtn").addEventListener("click", function(){window.location.href ="index.html"})
document.getElementById("gohome").addEventListener("click", function(){window.location.href ="index.html"})
loginbtn.addEventListener("click", function(){window.location.href ="NitroLogin.html"})

function main(){
    if(localStorage.user){
        var userspan= document.getElementById("userspan");
        userspan.textContent = localStorage.user;
        loginbtn.style.display = "none";
        registerbtn.style.display= "none"
    }else{
        logoutbtn.style.display = "none"
        
    }
}

logoutbtn.addEventListener("click", function(){
    

    fetch('http://localhost:8080/NitroReader/Session', {
    method: 'GET', 
    withCredentials: true,
    credentials: 'same-origin',
    headers:{
        'Content-Type': 'application/json'
    }
    }).then(res => res.json()).then(function(){
       localStorage.clear();
       window.location.replace("index.html");
    })
    .catch(error => console.error('Error:', error))
})