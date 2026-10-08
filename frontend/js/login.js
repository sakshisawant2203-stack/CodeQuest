const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {

        const response = await fetch("http://localhost:8080/Users/login", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error("Invalid username or password");
        }

        const token = await response.text();

        // Store JWT token
        localStorage.setItem("token", token);

        alert("Login successful");

        // Go to problems page
        window.location.href = "problem.html";

    } catch (error) {

        console.error("Login error:", error);

        alert(error.message);
    }
});