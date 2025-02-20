// Wait for the DOM to be fully loaded before executing the script
document.addEventListener('DOMContentLoaded', function () {
    // Get the search button element by its ID
    let buttonPress = document.getElementById('btn');
    if (buttonPress) {
               // Add a click event listener to the search button
        buttonPress.addEventListener('click', function () {
            // Get the city name from the input box and trim any extra spaces
            const cityName = document.getElementById('input-box').value.trim();
            // Check if the city name is empty
            if (!cityName) {
                // Show an error message using SweetAlert if the input is invalid
                swal("Invalid Input", "Please enter a valid City Name.", "error");
                return;
            }
             // Log the city name for debugging purposes
            console.log("Fetching weather data for City:", cityName); // Debugging

            fetch(`http://localhost:8080/weather-forecast?cityName=${encodeURIComponent(cityName)}`)
                .then(response => {
                               // Check if the response is not OK (e.g., 404 or 500 error)
                    if (!response.ok) throw new Error('Network response was not ok');
                    return response.json(); // Parse response as JSON
                })
                .then(data => {
                // Log the received weather data for debugging
                    console.log("Weather data received:", data); // Debugging
                    showWeaterReport(data);// Log the received weather data for debugging
                })
                .catch(error => {
                    // Log any errors that occur during the fetch operation
                    console.error("Error fetching weather data:", error); // Debugging
                    // Show an error message using SweetAlert
                    swal("Error", "Failed to fetch weather data: " + error.message, "error");
                });
        });
    } else {
          // Log an error if the search button is not found
        console.error("Search button not found!"); // Debugging
    }
});
// Function to display the weather report
function showWeaterReport(weather) {
     // Get the weather body element by its ID
    let weatherBody = document.getElementById('weather-body');
    if (!weatherBody) {
        // Log an error if the weather body element is not found
        console.error("Weather body element not found!"); // Debugging
        return;
    }
     // Make the weather body visible
    weatherBody.style.display = 'block';
    let todayDate = new Date();
    // Update the inner HTML of the weather body with the weather data
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
     // Change the background image based on the weather condition
    changeBg(weather.weather_main);
      // Reset the input box
    reset();
}

 // Helper functions (unchanged)
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