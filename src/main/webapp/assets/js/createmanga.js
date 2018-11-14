function createmanga(){
    fetchmanga("true");
}

function updatemanga(){
    fetchmanga("false");
}


function fetchmanga(manga_request){
    let form = new FormData();
    let init;
    if (manga_request === "true") {
        let arr = document.getElementsByName("genres[]");
        let checkedarr = [];
        for(i=0; i<arr.length; i++){
            if(arr[i].checked){
            form.append("genres_id",arr[i].value);
            }
        } 
        form.append("user_id", "2");
        let name = document.getElementById("name").value;
        form.append("manga_name", name);
        let synopsis = document.getElementById("synopsis").value;
        form.append("manga_synopsis", synopsis);
        let status = document.getElementById("status").value;
        form.append("manga_status", status);
        let file = document.getElementById('file').files[0];
        form.append("file", document.getElementById("file").files[0]);
        form.append("manga_request", manga_request);
    } else{
        form.append("manga_id", "33");
        form.append("manga_name", document.getElementById("name").value);
        form.append("manga_synopsis", document.getElementById("synopsis").value);
        form.append("manga_status", document.getElementById("status").value);
        form.append("file", document.getElementById("file").files);
        form.append("manga_request", manga_request);
    }
    init ={
        method: 'POST',
        body: form
      }
    fetch("http://localhost:8080/NitroReader/CRUDMangaServlet",init)
    .then(function(res){
        return res.json()
    }).then(function(res){   
        console.log(`${res.status} ${res.message}`);
    })
}

function changeFile(){
    document.getElementById("textImage").innerHTML = document.getElementById("file").files[0].name;
}

document.getElementById("createManga").addEventListener("click", createmanga, false);
document.getElementById("updateManga").addEventListener("click", updatemanga, false);
document.getElementById("file").addEventListener("input", changeFile, false);
