let slider = document.getElementById("eloSlider");
let indicator = document.getElementById("eloRangeSpan");
let eloRatingString = document.getElementById("eloRating").innerText;
eloRatingString = eloRatingString.replace(",", "");
let eloRating = parseInt(eloRatingString);
console.log(eloRating);
let eloFloor = document.getElementById("eloFloor");
let eloCeiling = document.getElementById("eloCeiling");

slider.oninput = function () {

    let eloRange = this.value * 25;
    indicator.innerHTML = eloRange;
    eloFloor.innerHTML = eloRating - eloRange;
    eloCeiling.innerHTML = eloRating + eloRange;

    if (eloRating - eloRange < 0){
        eloFloor.innerHTML = 0;
    }

}