let slider = document.getElementById("eloSlider");
let indicator = document.getElementById("eloRangeSpan");
let link = document.getElementById("matchmakeLink");

//indicator.innerHTML = slider.value;

slider.oninput = function () {
    indicator.innerHTML = this.value * 25;
    let valueToInsert = this.value * 25;
    let linkString = "/matchMaking?maxDiff=" + valueToInsert;

    link.setAttribute("href", linkString);

}