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
        form.append("manga_id", localStorage.manga_id);
        form.append("manga_name", document.getElementById("name").value);
        form.append("manga_synopsis", document.getElementById("synopsis").value);
        form.append("manga_status", document.getElementById("status").value);
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
        if (res.status === 201) {
            window.location.href = `/NitroReader/MangaInfo.html?manga=${res.data.manga_id}`;
        }else if (res.status === 200) {
            window.location.href = `/NitroReader/MangaInfo.html?manga=${localStorage.manga_id}`;
        }
    })
}

function changeFile(){
    document.getElementById("textImage").innerHTML = document.getElementById("file").files[0].name;
}

document.getElementById("createManga").addEventListener("click", createmanga, false);
document.getElementById("updateManga").addEventListener("click", updatemanga, false);
document.getElementById("file").addEventListener("input", changeFile, false);
