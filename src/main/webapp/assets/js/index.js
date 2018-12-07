function load() {
    fetch('/NitroReader/HomeServlet', {method: 'GET'}).then(res => res.json()).then(res => {
        if (res.status === 200){
            res.data.searchManga.forEach(element => {
                putMangas(element);
            })
        } 
    })
}


function reElement(type) {
    return document.createElement(type);
}

function putMangas(element) {
    let li = reElement('li');
    let article = reElement('article');
    let a = reElement('a');
    a.href = `/NitroReader/MangaInfo.html?manga=${element.manga_id}`;
    let img = reElement('img');
    img.src = `http://localhost:8080/NitroReader/library/${element.manga_location}`;
    let h3 = reElement('h3');
    h3.textContent = element.manga_name;
    a.appendChild(img);
    a.appendChild(h3);
    article.appendChild(a);
    li.appendChild(article);
    document.getElementById('mangas').appendChild(li);
}

