const token = localStorage.getItem("token");

if (!token) {
    alert("Please login first.");
    window.location.href = "login.html";
}

async function loadProblems() {

    try {

        const response = await fetch("http://localhost:8080/problems", {

            method: "GET",

            headers: {
                "Authorization": "Bearer " + token
            }
        });

        if (response.status === 401 || response.status === 403) {
            alert("You are not authorized. Please login again.");
            localStorage.removeItem("token");
            window.location.href = "login.html";
            return;
        }

        if (!response.ok) {
            throw new Error("Failed to fetch problems");
        }

        const problems = await response.json();

        console.log("Problems received:", problems);

        displayProblems(problems);

    } catch (error) {

        console.error("Error loading problems:", error);

        alert("Cannot connect to Spring Boot backend.");
    }
}


function displayProblems(problems) {

    const problemList = document.getElementById("problemList");

    problemList.innerHTML = "";

    problems.forEach(function(problem) {

        const problemItem = document.createElement("div");

        problemItem.innerHTML = `
            <h3>${problem.title}</h3>
            <p>Difficulty: ${problem.difficulty}</p>
            <button onclick="openProblem('${problem.slug}')">
                Solve
            </button>
        `;

        problemList.appendChild(problemItem);
    });
}


function openProblem(slug) {
    window.location.href =
        `problem-detail.html?slug=${encodeURIComponent(slug)}`;
}


loadProblems();