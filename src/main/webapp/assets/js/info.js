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

function load() {
    fetch(`http://localhost:8080/NitroReader/MangaServlet?manga=${getUrlParameter("manga")}`, {method:'GET'}).then(res => res.json())
    .then(res => {
        document.getElementById("mangaImage").src = `http://localhost:8080/NitroReader/library/${res.data.location}`;
        document.getElementById("mangaName").innerHTML = res.data.manga_name;
        document.getElementById("mangaSynopsis").innerHTML = res.data.manga_synopsis;
        console.log(res.data.manga_status);
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
        
    })
}