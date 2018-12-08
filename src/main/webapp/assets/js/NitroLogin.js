var loginbtn = document.getElementById("loginbtn")
var registerbtn= document.getElementById("registerbtn")
var logoutbtn = document.getElementById("logoutbtn")

//los eventos para el redireccionado
registerbtn.addEventListener("click", function(){window.location.href ="NitroRegister.html"})

document.getElementById("rediregister").addEventListener("click", function(){window.location.href ="NitroRegister.html"})

document.getElementById("homebtn").addEventListener("click", function(){window.location.href ="index.html"})
document.getElementById("gohome").addEventListener("click", function(){window.location.href ="index.html"})
loginbtn.addEventListener("click", function(){window.location.href ="NitroLogin.html"})


document.getElementById("gologin").addEventListener("click", loguear)
document.addEventListener("keyup",keyU);

    function keyU(e){
        if (e.keyCode == 13 ){
            if(document.activeElement == document.getElementById("pwd") || (document.activeElement == document.getElementById("user"))){
                loguear()
            }
        }
    }




    function loguear(){
        var pwd = document.getElementById("pwd").value
        var user = document.getElementById("user").value
        var url = 'http://localhost:8080/NitroReader/Session';
        var data = { password : pwd,
                    user: user
                                };
                                
        fetch(url, {
        method: 'POST', 
        body: JSON.stringify(data), 
        headers:{
            'Content-Type': 'application/json'
        }
        }).then(res => res.json()).then(function(res){
            if(res.status ==200){
                console.log(res.data.name)
                localStorage.setItem("user", res.data.name);
                localStorage.setItem("user_id", res.data.id);
                console.log("login complete!")
               window.location.replace("index.html");
            }
        })
        .catch(error => console.error('Error:', error))
    }


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