var mainimg = document.getElementById("mainimg")
var max;
var filedir
var currentP= 1
var chapters;
var readerChapter;

var fragmentElement = document.getElementById("NewComment").children[1].cloneNode(true);
fragmentElement.style.width = "100%";
var child = fragmentElement.children[1];
child.removeChild(child.children[0]);
child.innerHTML += "<button type='button' class='btn btn-primary'>Update!</button>";
child.children[0].classList.add("update-btn");
fragmentElement.children[1].innerHTML += "<button type='button' class='btn btn-dark'>Close</button>";
child.children[1].classList.add("update-btn");


 function getchapter(){
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter?option=getchapter&currentChap='+localStorage.currentChap+"&mangaid="+localStorage.mangaid,init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        console.log(res)
        max = res.max;
        if(max>0){
            if(localStorage.retrocediendo === "si"){
                currentP = max
                filedir= res.filedir;
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }else{
                currentP=1
                filedir= res.filedir;
                 mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }
            
        }else{
            alert("este capitulo se encuentra vacio!")
        }
        
        
    })
    
    
}




document.addEventListener("keydown", function(e){
    switch(e.keyCode){
        case 37:
            if(currentP > 1 ){
                currentP--
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }else{
                ordenChap()
                if(chapters["nombre"+(readerChapter-1)]){
                    localStorage.setItem("retrocediendo", "si") 
                    alert("redirigiendo al capitulo anterior")
                    localStorage.setItem("currentChap", chapters["nombre"+(readerChapter-1)])
                     window.location.href ="Reader.html"
                }
            }
            break;
        case 39:
            if(currentP < max){
                currentP++
                mainimg.setAttribute("src", filedir+"/"+ currentP+".png");
            }else{
                ordenChap()
                if(chapters["nombre"+(readerChapter+1)]){
                    localStorage.setItem("retrocediendo", "no") 
                    alert("capitulo finalizado, ha sido redirigido al siguiente capitulo")
                    localStorage.setItem("currentChap", chapters["nombre"+(readerChapter+1)])
                     window.location.href ="Reader.html"
                }else{
                    alert("capitulo finalizado, ha llegado al final del manga.")
                }
                
            }
            break;
    }
})


function loadnumchapter(){
    var init ={
        method: 'GET'
    }
    fetch('http://localhost:8080/NitroReader/Chapter?option=getnumchapters&mangaid='+localStorage.mangaid,init)
    .then(function(res){
        return res.json()
    }).then(function(res){
        var count = Object.keys(res).length;
        chapters = res;
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
function getChapter_id(){
     let tags = document.getElementById("dropmenu").getElementsByTagName("a");
    for (let i = 0; i < tags.length ; i++) {
        if (localStorage.currentChap === tags[i].textContent){
            return localStorage.currentChap;
        }
    }
}
ordenChap = function(){
    for(let i=0; i<Object.keys(chapters).length; i++){
        if(localStorage.currentChap == chapters["nombre"+i]){
            readerChapter = i;
        }
    }
}
function showComments(){
    fetch(`http://localhost:8080/NitroReader/CommentChapterServ?chapter_id=${localStorage.getItem("currentChap")}`, {method: 'GET'})
        .then(res => res.json()).then((res) => {
        if (res.status === 200) {
            document.getElementById("NewComment").children[0].textContent = localStorage.getItem("user");
            if (res.data.logged){ //If is loggedd put the buttons of delete and edit on the comment
                document.getElementById("sndComment").addEventListener("click", sendComment);
                if (res.data.hasOwnProperty("comments")){
                    res.data.comments.forEach((element) => {
                        if (element.owned) {
                            getComments(element.name, element.comment, true);
                        }else{
                            getComments(element.name, element.comment, false);
                        }
                    })
                }
            }else{
                document.getElementById("sndComment").setAttribute("data-toggle", "modal");
                document.getElementById("sndComment").setAttribute("data-target", "#notLogged");
                if (res.data.hasOwnProperty("comments")){
                    res.data.comments.forEach((element) => {
                        getComments(element.name, element.comment, false);
                    })
                }
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
    let commentBox = document.getElementById("Comments");
    commentBox.insertBefore(c, commentBox.childNodes[0]);
}
function sendComment() {
    document.getElementById("sndComment").removeEventListener("click", sendComment);
    let commentContent = document.getElementById("comment-text").value;
    console.log(commentContent);
    let chapter = localStorage.getItem("currentChap");
    let data = {user_id: localStorage.getItem("user_id"),
        chapter_id: chapter,
        newComment: commentContent,
    };
    fetch("http://localhost:8080/NitroReader/CommentChapterServ", {method:'POST', body:JSON.stringify(data), headers: {'Content-Type': 'application/json'}})
        .then(res => res.json()).then((res) => {
        if (res.status === 201) {
            newComment(res.data.user_name, res.data.comment);
            document.getElementById("sndComment").addEventListener("click", sendComment);
        }
    }).catch((error) => {
        console.log(error);
    })

}
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
        chapter_id: localStorage.getItem("currentChap"),
        comment: p3.children[2].textContent};

    fetch("http://localhost:8080/NitroReader/CommentChapterServ",{method:'PUT', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}})
        .then(res => res.json()).then((res) => {
        if (res.status === 200) {
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
    data = {user_id: localStorage.getItem("user_id"), chapter_id: localStorage.getItem("currentChap"), comment: p2.children[2].textContent};
    fetch("http://localhost:8080/NitroReader/CommentChapterServ", {method:'DELETE', body:JSON.stringify(data), headers:{'Content-Type': 'application/json'}})
        .then(res => res.json()).then((res) => {
        if (res.status === 200) {
            p3.removeChild(p2);
        }
    }).catch((error) => {
        console.log(error);
    })

}