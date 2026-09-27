const temperatureInput = document.getElementById("temperature");
const unitSelect = document.getElementById("unit");
const convertButton = document.getElementById("convert-btn");

const errorMessage = document.getElementById("error-message");

const celsiusResult = document.getElementById("celsius-result");
const fahrenheitResult = document.getElementById("fahrenheit-result");
const kelvinResult = document.getElementById("kelvin-result");


/* =========================================
   FORMAT RESULT
========================================= */

function formatTemperature(value) {
    return Number(value.toFixed(2));
}


/* =========================================
   DISPLAY ERROR
========================================= */

function showError(message) {
    errorMessage.textContent = message;

    celsiusResult.textContent = "—";
    fahrenheitResult.textContent = "—";
    kelvinResult.textContent = "—";
}


/* =========================================
   CLEAR ERROR
========================================= */

function clearError() {
    errorMessage.textContent = "";
}


/* =========================================
   CONVERT TEMPERATURE
========================================= */

function convertTemperature() {

    const inputValue = temperatureInput.value.trim();
    const inputUnit = unitSelect.value;

    /* Check empty input */

    if (inputValue === "") {
        showError("Please enter a temperature value.");
        return;
    }


    /* Check numeric input */

    const temperature = Number(inputValue);

    if (!Number.isFinite(temperature)) {
        showError("Please enter a valid numeric temperature.");
        return;
    }


    let celsius;


    /* =====================================
       CONVERT INPUT TO CELSIUS
    ===================================== */

    if (inputUnit === "celsius") {

        celsius = temperature;

    } else if (inputUnit === "fahrenheit") {

        celsius = (temperature - 32) * 5 / 9;

    } else if (inputUnit === "kelvin") {

        celsius = temperature - 273.15;
    }


    /* =====================================
       ABSOLUTE ZERO VALIDATION
    ===================================== */

    if (celsius < -273.15) {

        showError(
            "Temperature cannot be below absolute zero (−273.15°C)."
        );

        return;
    }


    /* =====================================
       CONVERT CELSIUS TO OTHER UNITS
    ===================================== */

    const fahrenheit = (celsius * 9 / 5) + 32;

    const kelvin = celsius + 273.15;


    /* =====================================
       DISPLAY RESULTS
    ===================================== */

    clearError();

    celsiusResult.textContent =
        `${formatTemperature(celsius)} °C`;

    fahrenheitResult.textContent =
        `${formatTemperature(fahrenheit)} °F`;

    kelvinResult.textContent =
        `${formatTemperature(kelvin)} K`;
}


/* =========================================
   BUTTON EVENT
========================================= */

convertButton.addEventListener(
    "click",
    convertTemperature
);


/* =========================================
   ENTER KEY SUPPORT
========================================= */

temperatureInput.addEventListener(
    "keydown",
    function(event) {

        if (event.key === "Enter") {
            convertTemperature();
        }

    }
);