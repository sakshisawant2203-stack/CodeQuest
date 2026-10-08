const submissions = [

    {
        problem: "Two Sum",
        status: "Accepted",
        language: "Java"
    },

    {
        problem: "Binary Search",
        status: "Wrong Answer",
        language: "Java"
    },

    {
        problem: "Palindrome Number",
        status: "Accepted",
        language: "Java"
    }

];


const submissionList =
    document.getElementById("submissionList");


function showSubmissions(submissionArray) {

    submissionList.innerHTML = "";


    submissionArray.forEach(function(submission) {

        const card =
            document.createElement("div");


        card.className = "submission-card";


        let statusClass = "";


        if (submission.status === "Accepted") {

            statusClass = "accepted";

        } else if (submission.status === "Wrong Answer") {

            statusClass = "wrong";

        } else {

            statusClass = "running";

        }


        card.innerHTML =  `

            <h3>${submission.problem}</h3>

            <div class="submission-details">

                <p class="${statusClass}">
                    ${submission.status}
                </p>

                <p>
                    Language: ${submission.language}
                </p>

            </div>

        `;


        submissionList.appendChild(card);

    });

}


showSubmissions(submissions);