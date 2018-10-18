document.getElementById("registerbtn").addEventListener("click", function(){window.location.href ="Nitroregister.html"})
document.getElementById("homebtn").addEventListener("click", function(){window.location.href ="index.html"})
document.getElementById("loginbtn").addEventListener("click", function(){window.location.href ="Nitrologin.html"})
document.getElementById("relogin").addEventListener("click", function(){window.location.href ="Nitrologin.html"})

document.getElementById("create").addEventListener("click", function(){
    var pwd = document.getElementById("pwd").value
    var rpwd = document.getElementById("rpwd").value
    var mail = document.getElementById("email").value
    var user = document.getElementById("user").value
    var name = document.getElementById("create").value
    if(pwd === rpwd){
    var url = 'http://localhost:8080/NitroReader/Register';
    var data = {name: name,
                password : pwd,
                mail: mail,
                user: user
                            };

    fetch(url, {
    method: 'POST', 
    body: JSON.stringify(data), 
    headers:{
        'Content-Type': 'application/json'
    }
    }).then(res => res.json()).then(function(res){
        if(res.status ==200 && validUser == true && validEmail == true && validPassword ==true && validName== true){
            console.log("Register complete!")
        }
    })
    .catch(error => console.error('Error:', error))
    .then(response => console.log('Success:', response));
        }else{alert("passwords don't match!")}

    })










