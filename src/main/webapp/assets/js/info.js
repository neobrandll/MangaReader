// General Stuff

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

var fragmentElement = document.getElementById("NewComment").children[1].cloneNode(true);
fragmentElement.style.width = "100%";
var child = fragmentElement.children[1];
child.removeChild(child.children[0]);
child.innerHTML += "<button type='button' class='btn btn-primary'>Update!</button>";
child.children[0].classList.add("update-btn");
// child.children[0].addEventListener("click", updateComment);
fragmentElement.children[1].innerHTML += "<button type='button' class='btn btn-dark'>Close</button>";
child.children[1].classList.add("update-btn");
// child.children[1].addEventListener("click", updateComment);


//////////////////////////////////////////////////////////////////////////

function load() {
    fetch(`http://localhost:8080/NitroReader/CRUDMangaServlet?manga=${getUrlParameter("manga")}&user_id=${localStorage.getItem("user_id")}`, {method:'GET'}).then(res => res.json())
    .then(res => {
        document.getElementById("mangaImage").src = `http://localhost:8080/NitroReader/library/${res.data.location}`;
        document.getElementById("mangaName").innerHTML = res.data.manga_name;
        document.getElementById("mangaSynopsis").innerHTML = res.data.manga_synopsis;
        if (res.status === 200) {
            if (res.data.manga_status) {
                document.getElementById("mangaStatuss").innerHTML = "<i class='fas fa-check-circle'></i> Ongoing";
            } else{
                document.getElementById("mangaStatus").classList.add("fas fa-ban");
                document.getElementById("mangaStatuss").innerHTML = "Discontinued";
            }
            res.data.genres.forEach(element => {
                let li = document.createElement("li");
                li.setAttribute("class", "genre");
                li.innerHTML = element;
                document.getElementById("mangaGenres").appendChild(li);
            });
            document.getElementById("like").setAttribute("data-original-title", `${res.data.likesManga}`);
            if (res.data.like){
                document.getElementById("like").style.backgroundColor = "green";
                document.getElementById("like").addEventListener("click", removeLike);
            } else{
                document.getElementById("like").addEventListener("click", likeManga);
            }

        }

    })
}

function showComments(){
    let data = {manga_id : getUrlParameter("manga")}
    fetch(`http://localhost:8080/NitroReader/CommentManga?manga_id=${getUrlParameter("manga")}`, {method: 'GET'})
    .then(res => res.json()).then((res) => {
        if (res.status == 200) {
            document.getElementById("NewComment").children[0].textContent = localStorage.getItem("user");
            if (res.data.hasOwnProperty("comments")) {
                res.data.comments.forEach((element) => {
                    if (`${element.id}` === localStorage.getItem("user_id")) {
                        getComments(element.name, element.comment, true);
                    }else{
                        getComments(element.name, element.comment, false);
                    }
                })
            }            
            document.getElementById("titleComments").classList.remove("hidde");
            document.getElementById("Comments").classList.remove("hidde");
            document.getElementById("NewComment").classList.remove("hidde");
            this.classList.add("hidde");
        }
    }).catch((error) => {console.log(error);})
    
}
document.getElementById("btnComments").addEventListener("click", showComments);

function getComments(name, comment, condition){
    if (condition) {
    let c = document.createElement("div");
    c.classList.add("comment");
    let n = document.createElement("div");
    n.classList.add("name");
    n.textContent = name;
    c.appendChild(n);
    let e = document.createElement("div");
    let s = document.createElement("span");
    s.addEventListener("click", editComment);
    let s2 = document.createElement("span");
    s2.addEventListener("click", deleteComment);
    let b = document.createElement("button");
    b.classList.add("btn");
    b.classList.add("btn-link");
    let b2 = document.createElement("button");
    b2.classList.add("btn");
    b2.classList.add("btn-danger");
    let i = document.createElement("i");
    let i2= document.createElement("i");
    i.classList.add("fas");
    i.classList.add("fa-pencil-alt");
    i2.classList.add("fas");
    i2.classList.add("fa-times");
    b.appendChild(i);
    b.appendChild(document.createTextNode(" Edit"));
    b2.appendChild(i2);
    s.appendChild(b);
    s2.appendChild(b2);
    e.classList.add("editions");
    e.appendChild(s);
    e.appendChild(s2);
    c.appendChild(e);
    let p = document.createElement("p");
    p.classList.add("comment-content");
    p.textContent = comment;
    c.appendChild(p);
    document.getElementById("Comments").appendChild(c);
    } else{
    let c = document.createElement("div");
    c.classList.add("comment");
    let n = document.createElement("div");
    n.classList.add("name");
    n.textContent = name;
    c.appendChild(n);
    let p = document.createElement("p");
    p.classList.add("comment-content");
    p.textContent = comment;
    c.appendChild(p);
    document.getElementById("Comments").appendChild(c);
    }
    
}
function newComment(name, comment) {
    let c = document.createElement("div");
    c.classList.add("comment");
    let n = document.createElement("div");
    n.classList.add("name");
    n.textContent = name;
    c.appendChild(n);
    let e = document.createElement("div");
    let s = document.createElement("span");
    s.addEventListener("click", editComment);
    let s2 = document.createElement("span");
    let b = document.createElement("button");
    b.classList.add("btn");
    b.classList.add("btn-link");
    let b2 = document.createElement("button");
    b2.classList.add("btn");
    b2.classList.add("btn-danger");
    let i = document.createElement("i");
    let i2= document.createElement("i");
    i.classList.add("fas");
    i.classList.add("fa-pencil-alt");
    i2.classList.add("fas");
    i2.classList.add("fa-times");
    b.appendChild(i);
    b.appendChild(document.createTextNode(" Edit"));
    b2.appendChild(i2);
    s.appendChild(b);
    s2.appendChild(b2);
    e.classList.add("editions");
    e.appendChild(s);
    e.appendChild(s2);
    c.appendChild(e);
    let p = document.createElement("p");
    p.classList.add("comment-content");
    p.textContent = comment;
    c.appendChild(p);
    let commentBox = document.getElementById("Comments");
    commentBox.insertBefore(c, commentBox.childNodes[0]);
}

function sendComment() {
    document.getElementById("sndComment").removeEventListener("click", sendComment);
    let commentContent = document.getElementById("comment-text").value;
    console.log(commentContent);
    let manga = getUrlParameter("manga");
    let data = {user_id: localStorage.getItem("user_id"),
            manga_id: manga,
            newComment: commentContent,
            }
    fetch("http://localhost:8080/NitroReader/CommentManga", {method:'POST', body:JSON.stringify(data), headers: {'Content-Type': 'application/json'}})
    .then(res => res.json()).then((res) => {
        if (res.status == 201) {
            newComment(res.data.user_name, res.data.comment);
            document.getElementById("sndComment").addEventListener("clicl", sendComment);
        }
    }).catch((error) => {
        console.log(error);        
    })

}
document.getElementById("sndComment").addEventListener("click", sendComment);

function editComment() {
    let p1 = this.parentNode;
    let p2 = p1.parentNode;
    let comment = p2.children[2];    
    comment.classList.add("hidde");
    a = fragmentElement.cloneNode(true);
    a.children[0].value = comment.textContent;
    a.children[1].children[0].addEventListener("click", updateComment);
    a.children[1].children[1].addEventListener("click", removeUpdate);
    p2.appendChild(a);
    this.removeEventListener("click", editComment);
}

function removeUpdate() {
    let p1 = this.parentNode;
    let p2 = p1.parentNode;
    let p3 = p2.parentNode;
    p3.children[1].children[0].addEventListener("click", editComment);
    p3.children[2].classList.remove("hidde");
    p3.removeChild(p2);
}
function updateComment() {
    let p1 = this.parentNode;
    let p2 = p1.parentNode;
    let p3 = p2.parentNode;
    let newC = p2.children[0].value;
    let data = {newComment: newC,
                user_id:localStorage.getItem("user_id"),
                manga_id:getUrlParameter("manga"),
                comment: p3.children[2].textContent}

    fetch("http://localhost:8080/NitroReader/CommentManga",{method:'PUT', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}})
    .then(res => res.json()).then((res) => {
        if (res.status == 200) {
            p3.children[2].textContent = newC;
            p3.children[2].classList.remove("hidde");
            p3.children[1].children[0].addEventListener("click", editComment);
            p3.removeChild(p2);
        }
    }).catch((error) => {
        console.log(error);
    })
}
function deleteComment() {
    let p1 = this.parentNode;
    let p2 = p1.parentNode;
    let p3 = p2.parentNode;
    data = {user_id: localStorage.getItem("user_id"), manga_id: getUrlParameter("manga"), comment: p2.children[2].textContent}
    fetch("http://localhost:8080/NitroReader/CommentManga", {method:'DELETE', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}})
    .then(res => res.json()).then((res) => {
        if (res.status === 200) {
            p3.removeChild(p2);
        }
    }).catch((error) => {
        console.log(error);
    })

}
function likeManga() {
    this.removeEventListener("click", likeManga);
    data = {manga_id: getUrlParameter("manga"), user_id:localStorage.getItem("user_id")}
    fetch("http://localhost:8080/NitroReader/LikeManga", {method:'POST', body: JSON.stringify(data), headers:{'Content-Type': 'application/json'}})
        .then(res => res.json()).then((res) =>{
            if (res.status === 201){
                document.getElementById("like").setAttribute("data-original-title", `${res.data.likesManga}`);
                if (res.data.like){
                    document.getElementById("like").style.backgroundColor = "green";
                }
                document.getElementById("like").addEventListener("click", removeLike);
            }
    }).catch((error) =>{
        console.log(error);
    })
}
function removeLike() {
    this.removeEventListener("click", removeLike);
    data = {manga_id: getUrlParameter("manga"), user_id:localStorage.getItem("user_id")}
    fetch("http://localhost:8080/NitroReader/LikeManga", {method:'DELETE', body: JSON.stringify(data), headers:{'Content-Type': 'application/json'}})
        .then(res => res.json()).then((res) =>{
        if (res.status === 200){
            document.getElementById("like").setAttribute("data-original-title", `${document.getElementById("like").getAttribute("data-original-title") - "1"}`);
            if (!res.data.like){
                document.getElementById("like").style.backgroundColor = "black";
            }
            document.getElementById("like").addEventListener("click", likeManga);
        }
    }).catch((error) =>{
        console.log(error);
    })
}
