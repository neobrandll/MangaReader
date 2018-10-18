


document.getElementById("create").addEventListener("click", function(){
    var pwd = document.getElementById("pwd").value
    var rpwd = document.getElementById("rpwd").value
    var email = document.getElementById("email").value
    var user = document.getElementById("user").value
    var name = document.getElementById("create").value
    if(pwd === rpwd){
    var url = 'http://localhost:8080/NitroReader/Register';
    var data = {name: name,
                pwd : pwd,
                email: email,
                user: user
                            };

    fetch(url, {
    method: 'POST', 
    body: JSON.stringify(data), 
    headers:{
        'Content-Type': 'application/json'
    }
    }).then(res => res.json())
    .catch(error => console.error('Error:', error))
    .then(response => console.log('Success:', response));
        }else{alert("las contrasenas no coinciden!")}

    })










