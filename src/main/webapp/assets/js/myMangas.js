function load() {
    fetch('/NitroReader/MyMangas', {method:'GET'}).then(res => res.json()).then(res => {
        if (res.status === 200) {
            res.data.searchManga.forEach(element => {
                mangaContent(element);
            });
        }
    })
}



function mangaContent(element) {
    let div1 = reElement('div');
    div1.classList.add('manga-content');
    let div1_1 = reElement('div');
    let a = reElement('a');
    a.href = `../NitroReader/MangaInfo.html?manga=${element.manga_id}`;
    let img = reElement('img');
    img.src = `http://localhost:8080/NitroReader/library/${element.manga_location}`;
    a.appendChild(img);
    div1_1.appendChild(a);
    let div1_2 = reElement('div');
    let h3 = reElement('h3');
    h3.textContent = element.manga_name;
    let p = reElement('p');
    p.textContent = element.manga_synopsis;
    div1_2.appendChild(h3);
    div1_2.appendChild(p);
    div1.appendChild(div1_1);
    div1.appendChild(div1_2);
    document.getElementById('allMangas').appendChild(div1);
}

function reElement(type) {
    return document.createElement(type);
}