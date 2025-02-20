
document.addEventListener('DOMContentLoaded', function () {
    let buttonPress = document.getElementById('btn');
    if (buttonPress) {
        buttonPress.addEventListener('click', function () {
            const cityName = document.getElementById('input-box').value.trim();

            if (!cityName) {
                swal("Invalid Input", "Please enter a valid City Name.", "error");
                return;
            }

            console.log("Fetching weather data for City:", cityName);

            fetch(`http://localhost:8080/weather-forecast?cityName=${encodeURIComponent(cityName)}`)
                .then(response => {

                    if (!response.ok) throw new Error('Network response was not ok');
                    return response.json(); // Parse response as JSON
                })
                .then(data => {
                    console.log("Weather data received:", data);
                    showWeaterReport(data);
                })
                .catch(error => {
                    console.error("Error fetching weather data:", error);

                    swal("Error", "Failed to fetch weather data: " + error.message, "error");
                });
        });
    } else {
        console.error("Search button not found!");
    }
});

function showWeaterReport(weather) {
    let weatherBody = document.getElementById('weather-body');
    if (!weatherBody) {

        console.error("Weather body element not found!");
        return;
    }
    weatherBody.style.display = 'block';
    let todayDate = new Date();

    weatherBody.innerHTML =
        `
    <div class="location-deatils">
        <div class="city" id="city">${weather.name}, ${weather.country}</div>
        <div class="date" id="date">${dateManage(todayDate)}</div>
    </div>
    <div class="weather-status">
        <div class="temp" id="temp">${weather.temp}&deg;C</div>
        <div class="weather" id="weather">${weather.weather_main} <i class="${getIconClass(weather.weather_main)}"></i></div>
        <div class="min-max" id="min-max">${weather.temp_min}&deg;C (min) / ${weather.temp_max}&deg;C (max)</div>
        <div id="updated_on">Updated as of ${weather.updated_on}</div>
    </div>
    <hr>
    <div class="day-details">
        <div class="basic">Feels like ${weather.feels_like}&deg;C | Humidity ${weather.humidity}%  <br> Pressure ${weather.pressure} mb | Wind ${weather.wind_speed} KMPH</div>
    </div>
    `;
    changeBg(weather.weather_main);
    reset();
}

function getTime(todayDate) {
    let hour = addZero(todayDate.getHours());
    let minute = addZero(todayDate.getMinutes());
    return `${hour}:${minute}`;
}

function dateManage(dateArg) {
    let days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    let months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    let year = dateArg.getFullYear();
    let month = months[dateArg.getMonth()];
    let date = dateArg.getDate();
    let day = days[dateArg.getDay()];
    return `${date} ${month} (${day}) , ${year}`;
}

function changeBg(status) {
    const backgrounds = {
        'Clouds': 'url(../images/clouds.jpeg)',
        'Rain': 'url(../images/rain.jpeg)',
        'Clear': 'url(../images/clear.jpeg)',
        'Snow': 'url(../images/snow.jpeg)',
        'Sunny': 'url(../images/sunny.jpeg)',
        'Thunderstorm': 'url(../images/thunder.jpeg)',
        'Drizzle': 'url(../images/drizzle.jpeg)',
        'Mist': 'url(../images/mist.jpeg)',
        'Haze': 'url(../images/mist.jpeg)',
        'Fog': 'url(../images/mist.jpeg)'
    };

    document.body.style.backgroundImage = backgrounds[status] || 'url(../images/bg1.jpeg)';
}

function getIconClass(classarg) {
    const icons = {
        'Rain': 'fas fa-cloud-showers-heavy',
        'Clouds': 'fas fa-cloud',
        'Clear': 'fas fa-cloud-sun',
        'Snow': 'fas fa-snowman',
        'Sunny': 'fas fa-sun',
        'Mist': 'fas fa-smog',
        'Thunderstorm': 'fas fa-thunderstorm',
        'Drizzle': 'fas fa-thunderstorm'
    };

    return icons[classarg] || 'fas fa-cloud-sun';
}

function reset() {
    let input = document.getElementById('input-box');
    input.value = "";
}

function addZero(i) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}