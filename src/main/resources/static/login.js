document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.querySelector("form");

    loginForm.addEventListener("submit", async function (event) {
        event.preventDefault(); // Prevent form submission

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const loginData = {
            username: username,
            password: password
        };

        try {
            const response = await fetch("http://localhost:8080/auth/signin", {
                method: "POST", // Ensure this is a POST request
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginData)
            });

            const result = await response.json();

            if (response.ok) {
                alert("Login successful!");
                localStorage.setItem("token", result.token); // Save the JWT token
                window.location.href = "landing.html"; // Redirect to the landing page
            } else {
                alert(result.error || "Login failed! Please check your credentials.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred. Please try again later.");
        }
    });
});