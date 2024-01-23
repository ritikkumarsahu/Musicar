
const playProgressSlider = document.getElementById("play-slider-progress");
const playPauseButton = document.getElementById("play-pause-button");
const audioSlider = document.getElementById("play-slider");
const playIcon = document.querySelector('.play-icon');
const pauseIcon = document.querySelector('.pause-icon');
const currentSongName = document.getElementById("play-song");
const currentSongArtist = document.getElementById("play-artist");
const currentSongCover = document.getElementById("play-cover");
const elapsedTime = document.getElementById('play-elapsed');
const totalTime = document.getElementById('play-total');
const playPreviousButton = document.getElementById('play-previous');
const playNextButton = document.getElementById('play-next');
const volumeProgressSlider = document.getElementById('volume-slider-progress');
const volumeSlider = document.getElementById('volume-slider');

let currentSong = {};
const audio = new Audio();

function playSong() {
    if (audio.paused) {
        playIcon.classList.add('hidden');
        pauseIcon.classList.remove('hidden');
        audio.play();
    } else {
        playIcon.classList.remove('hidden');
        pauseIcon.classList.add('hidden');
        audio.pause();
    }
}

function formatTime(timeSeconds) {
    const minutes = Math.floor(timeSeconds / 60);
    const seconds = Math.floor(timeSeconds % 60);
    return `${minutes}:${seconds.toString().padStart(2, '0')}`;
}

function changeSong(song) {
    currentSong = song;
    if (currentSong.song.length > 12) {
        currentSongName.text = `${currentSong.song.substring(0,9)}...`;
    } else {
        currentSongName.text = currentSong.song;
    }
    if (currentSong.primary_artists.length > 20) {
        currentSongArtist.text = currentSong.primary_artists.substring(0,17)+'...';
    } else {
        currentSongArtist.text = currentSong.primary_artists;
    }

    audio.src = currentSong.media_url;
    currentSongCover.src = currentSong.image;
    totalTime.innerText = formatTime(currentSong.duration);
}

function initPlayer() {
    let seeking = false;
    let audioSrc =  /*[[${hotAlbum.songs[1].media_url}]]*/ "https://aac.saavncdn.com/807/95e27ab27ac1b3d99756adfb889a91a2_320.mp4";
    audio.src = audioSrc;

    audio.loop = false;
    // audio.play();
    playPauseButton.addEventListener('click', () => {
        playSong();
    });
    audio.addEventListener("timeupdate", () => {
        if (!seeking) {
            let curTime = (audio.currentTime/audio.duration)*100;
            playProgressSlider.style.width = curTime + '%';
            audioSlider.value = curTime;
            elapsedTime.innerText = formatTime(audio.currentTime);
        }
    });
    audio.addEventListener('ended', function() {
        playIcon.classList.remove('hidden');
        pauseIcon.classList.add('hidden');
    });
    playProgressSlider.style.width = audioSlider.value + '%';
    audioSlider.addEventListener('input', e => {
        playProgressSlider.style.width = e.target.value+ '%';
    });

    volumeProgressSlider.style.width = volumeSlider.value + '%';
    volumeSlider.addEventListener('input', e => {
        volumeProgressSlider.style.width = e.target.value+ '%';
        audio.volume = e.target.value/100;
    });

    audioSlider.addEventListener("mousedown", function(){ seeking=true;});
	audioSlider.addEventListener("mouseup",function(e){ seek(e); });
    function seek(e){
        if(seeking){
            audio.currentTime = e.target.value/100 * audio.duration;
            seeking=false;
        }
    }

    document.querySelectorAll('.item-play-button').forEach(item => {
        item.addEventListener('click', event => {
            const songId = item.getAttribute("data-item-id");
            for (const song of topWeeklySongs){
                if (song.id === songId) {
                    changeSong(song);
                    playSong();
                    break;
                }
            }
        });
    });
  }

  initPlayer();

