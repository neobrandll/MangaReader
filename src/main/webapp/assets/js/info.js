function $(a){
    return document.getElementById(a);
}

function load() {
    fetch("/MangaServlet?manga_id=3", {method:'GET'}).then(res => res.json())
    .then(res => {
        $("mangaImage").src = res.data.direction;
        $("mangaName").innerHTML = res.data.manga_name;
        $("mangaSynopsis").innerHTML = res.data.manga_synopsis;
        if (res.data.status) {
            $("mangaStatus").classList.add("fas fa-check-circle");
            $("mangaStatuss").innerHTML = "Ongoing"
        } else{
            $("mangaStatus").classList.add("fas fa-ban");
            $("mangaStatuss").innerHTML = "Discontinued";
        }
    })
}