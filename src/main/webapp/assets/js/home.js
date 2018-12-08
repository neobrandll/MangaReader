var loginbtn = document.getElementById("loginbtn")
var registerbtn= document.getElementById("registerbtn")
var logoutbtn = document.getElementById("logoutbtn")

//los eventos para el redireccionado
registerbtn.addEventListener("click", function(){window.location.href ="NitroRegister.html"})
document.getElementById("homebtn").addEventListener("click", function(){window.location.href ="index.html"})
document.getElementById("gohome").addEventListener("click", function(){window.location.href ="index.html"})
loginbtn.addEventListener("click", function(){window.location.href ="NitroLogin.html"})
document.getElementById('search').addEventListener('click', search);

function main(){
    if(localStorage.user){
        var userspan= document.getElementById("userspan");
        userspan.addEventListener('click', function () {
            window.location.href = '/NitroReader/myMangas.html';
        })
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

function search(e) {
    e.preventDefault();
    let manga_name = document.getElementById('searchInput').value.trim();
    if (manga_name != "") {
        localStorage.setItem('manga_name', document.getElementById('searchInput').value);
        window.location.href = '/NitroReader/Search.html';
    }
}

function toCreate() {
    if (localStorage.getItem('user') != null){
        window.location.href = '/NitroReader/CreateManga.html';
    } else {
        alert('no estas logeado');
    }
}
document.getElementById('toCreate').addEventListener('click', toCreate);