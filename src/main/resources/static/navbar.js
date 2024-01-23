const searchInput = document.getElementById("voice-search");
const searchButton = document.getElementById("search-btn");

searchInput.addEventListener("keyup", function(event) {
  if (event.key === "Enter" ||  event.keyCode === 13) {
    event.preventDefault();
    location.href = `/search?query=${searchInput.value}`;
  }
});
searchButton.addEventListener("click", () => {location.href = `/search?query=${searchInput.value}`;});