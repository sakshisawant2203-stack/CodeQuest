const registerForm = document.getElementById("registerForm");

registerForm.addEventListener("submit", async function(event) {

    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {

        const response = await fetch("http://localhost:8080/Users/register", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const result = await response.text();

        console.log("Status:", response.status);
        console.log("Response:", result);

        if (response.ok) {

            alert("Registration successful");

            window.location.href = "login.html";

        } else {

            alert("Registration failed: " + result);
        }

    } catch (error) {

        console.error("Registration error:", error);

        alert("Cannot connect to Spring Boot backend");
    }
});