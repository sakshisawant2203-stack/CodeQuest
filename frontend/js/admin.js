const problems = [

    {
        title: "Two Sum",
        difficulty: "Easy"
    },

    {
        title: "Palindrome Number",
        difficulty: "Easy"
    },

    {
        title: "Binary Search",
        difficulty: "Medium"
    }

];


const problemList =
    document.getElementById("adminProblemList");


function showProblems(problemArray) {

    problemList.innerHTML = "";


    problemArray.forEach(function(problem) {

        const card =
            document.createElement("div");


        card.className = "admin-problem-card";


        card.innerHTML = `

            <h3>${problem.title}</h3>

            <p>
                Difficulty: ${problem.difficulty}
            </p>

            <button class="edit-button">
                Edit
            </button>

            <button class="delete-button">
                Delete
            </button>

            <button class="testcase-button">
                Test Cases
            </button>

        `;


        problemList.appendChild(card);

    });

}


showProblems(problems);