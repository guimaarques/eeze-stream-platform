const form = document.querySelector('#video-form');
const videoDiv = document.querySelector('#video-player');
const videoScreen = document.querySelector('#video-screen');
const url = 'http://localhost:8080/eeze-platform/videos'

const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));

fetch(url)
    .then(response => response.text())
    .then(result => {

        var obj = JSON.parse(result);   

        const myVids = document.querySelector('#your-videos');
        if(obj.length > 0){
            for(let vid of obj){
                const li = document.createElement('LI');
                const link = document.createElement('A');
                link.innerText = vid.name;
                link.href = window.location.origin + window.location.pathname + '?video=' + vid.name;
                li.appendChild(link);
                myVids.appendChild(li);
            }
        }else{
            myVids.innerHTML = 'No videos found';
        }

    });

if(queryParams.video){

    videoScreen.src = url + `/${queryParams.video}/play`;
    videoDiv.style.display = 'block';
    //document.querySelector('#now-playing')
    //    .innerText = 'Now playing ' + queryParams.video;

}

const video = document.querySelector("video");

video.addEventListener("ended", (event) => {
    console.log('enough and more')
    fetch(url + `/${queryParams.video}/view`, {
        method: 'PATCH',
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
          },
        
    });    
});