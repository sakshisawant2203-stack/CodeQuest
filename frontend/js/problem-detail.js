// =========================================================
// CODEQUEST - PROBLEM DETAIL PAGE
// =========================================================


// =========================================================
// GLOBAL VARIABLES
// =========================================================

let currentProblem = null;

let selectedTestCase = null;


// =========================================================
// GET SLUG FROM URL
// Example:
// problem-detail.html?slug=find-maximum-element
// =========================================================

function getProblemSlug() {

    const params = new URLSearchParams(window.location.search);

    return params.get("slug");
}


// =========================================================
// GET AUTH TOKEN
// =========================================================

function getToken() {

    return localStorage.getItem("token");
}


// =========================================================
// CREATE HEADERS
// =========================================================

function getHeaders() {

    const headers = {
        "Content-Type": "application/json"
    };

    const token = getToken();

    if (token) {

        headers["Authorization"] = "Bearer " + token;
    }

    return headers;
}


// =========================================================
// LOAD PROBLEM
// =========================================================

async function loadProblem() {

    const slug = getProblemSlug();

    if (!slug) {

        showProblemError("Problem slug not found.");

        return;
    }


    try {

        const response = await fetch(
            `http://localhost:8080/problems/${encodeURIComponent(slug)}`,
            {
                method: "GET",
                headers: getHeaders()
            }
        );


        if (!response.ok) {

            throw new Error(
                "Problem not found."
            );
        }


        const problem = await response.json();


        currentProblem = problem;


        displayProblem(problem);


        loadTestCases(problem);


        loadBoilerplate();


    } catch (error) {

        console.error(
            "Error loading problem:",
            error
        );

        showProblemError(
            error.message
        );
    }
}


// =========================================================
// DISPLAY PROBLEM
// =========================================================

function displayProblem(problem) {

    document.getElementById(
        "problemLoading"
    ).classList.add("hidden");


    document.getElementById(
        "problemContent"
    ).classList.remove("hidden");


    document.getElementById(
        "problemTitle"
    ).textContent =
        problem.title || "Untitled Problem";


    document.getElementById(
        "problemDifficulty"
    ).textContent =
        problem.difficulty || "UNKNOWN";


    document.getElementById(
        "problemSolved"
    ).textContent =
        `${problem.solved || 0} solved`;


    document.getElementById(
        "problemDescription"
    ).textContent =
        problem.description || "";


    document.getElementById(
        "inputFormat"
    ).textContent =
        problem.inputFormat || "";


    document.getElementById(
        "outputFormat"
    ).textContent =
        problem.outputFormat || "";


    document.getElementById(
        "constraints"
    ).textContent =
        problem.constraints || "";


    document.getElementById(
        "sampleInput"
    ).textContent =
        problem.sampleInput || "";


    document.getElementById(
        "sampleOutput"
    ).textContent =
        problem.sampleOutput || "";
}


// =========================================================
// SHOW ERROR
// =========================================================

function showProblemError(message) {

    const loading =
        document.getElementById(
            "problemLoading"
        );


    loading.textContent = message;

    loading.style.color = "#dc2626";
}


// =========================================================
// LOAD TEST CASES
// =========================================================

function loadTestCases(problem) {

    const container =
        document.getElementById(
            "testCasesContainer"
        );


    container.innerHTML = "";


    if (
        !problem.testCases ||
        !Array.isArray(problem.testCases)
    ) {

        container.innerHTML =
            `<p class="testcase-loading">
                No test cases available.
             </p>`;

        return;
    }


    // IMPORTANT:
    // Only visible test cases are displayed.
    //
    // hidden = false -> display
    // hidden = true  -> DO NOT display

    const visibleTestCases =
        problem.testCases.filter(
            testCase =>
                testCase.hidden === false
        );


    if (visibleTestCases.length === 0) {

        container.innerHTML =
            `<p class="testcase-loading">
                No visible test cases available.
             </p>`;

        return;
    }


    visibleTestCases.forEach(
        (testCase, index) => {

            const testCaseCard =
                document.createElement(
                    "div"
                );


            testCaseCard.className =
                "testcase-card";


            testCaseCard.innerHTML = `

                <div class="testcase-header">

                    <span class="testcase-title">
                        Test Case ${index + 1}
                    </span>

                    <span>
                        Visible
                    </span>

                </div>


                <div class="testcase-body">

                    <span class="testcase-label">
                        Input
                    </span>

                    <pre>${escapeHtml(
                        testCase.input || ""
                    )}</pre>


                    <span class="testcase-label">
                        Expected Output
                    </span>

                    <pre>${escapeHtml(
                        testCase.output || ""
                    )}</pre>

                </div>
            `;


            // When user clicks a test case,
            // show it in the editor's bottom panel.

            testCaseCard.addEventListener(
                "click",
                () => {

                    selectTestCase(
                        testCase
                    );

                }
            );


            container.appendChild(
                testCaseCard
            );

        }
    );
}


// =========================================================
// SELECT TEST CASE
// =========================================================

function selectTestCase(testCase) {

    selectedTestCase =
        testCase;


    const container =
        document.getElementById(
            "selectedTestCase"
        );


    container.innerHTML = `

        <h3>
            Selected Test Case
        </h3>


        <pre>${escapeHtml(
            testCase.input || ""
        )}</pre>


        <h3>
            Expected Output
        </h3>


        <pre>${escapeHtml(
            testCase.output || ""
        )}</pre>

    `;
}


// =========================================================
// LOAD BOILERPLATE
// =========================================================

function loadBoilerplate() {

    if (!currentProblem) {

        return;
    }


    const selectedLanguage =
        document.getElementById(
            "language"
        ).value;


    const sourceCode =
        document.getElementById(
            "sourceCode"
        );


    if (
        !currentProblem.defaultCodes ||
        !Array.isArray(
            currentProblem.defaultCodes
        )
    ) {

        sourceCode.value = "";

        return;
    }


    const defaultCode =
        currentProblem.defaultCodes.find(
            code =>
                code.language.toLowerCase()
                === selectedLanguage.toLowerCase()
        );


    if (defaultCode) {

        sourceCode.value =
            defaultCode.code || "";

    } else {

        sourceCode.value = "";
    }
}


// =========================================================
// LANGUAGE CHANGE
// =========================================================

function handleLanguageChange() {

    loadBoilerplate();
}

// =========================================================
// SUBMIT SOLUTION
// =========================================================

async function submitSolution() {

    if (!currentProblem) {

        showSubmissionMessage(
            "Problem is not loaded.",
            "error"
        );

        return;
    }


    const language =
        document.getElementById("language").value;


    const sourceCode =
        document.getElementById("sourceCode").value;


    if (!sourceCode.trim()) {

        showSubmissionMessage(
            "Please write your solution first.",
            "error"
        );

        return;
    }


    const submitButton =
        document.getElementById("submitButton");


    submitButton.disabled = true;

    submitButton.textContent =
        "Submitting...";


    const requestBody = {

        problemId: currentProblem.id,

        language: language,

        sourceCode: sourceCode

    };


    console.log(
        "Sending submission:",
        requestBody
    );


    try {

        // =========================================
        // STEP 1
        // SEND CODE TO SPRING BOOT
        // =========================================

        const response =
            await fetch(
                "http://localhost:8080/submissions/submit",
                {
                    method: "POST",

                    headers: getHeaders(),

                    body: JSON.stringify(requestBody)
                }
            );


        if (!response.ok) {

            const errorText =
                await response.text();

            throw new Error(
                errorText ||
                "Submission failed."
            );
        }


        // =========================================
        // STEP 2
        // GET CREATED SUBMISSION
        // =========================================

        const submission =
            await response.json();


        console.log(
            "Submission created:",
            submission
        );


        // =========================================
        // STEP 3
        // SHOW RUNNING
        // =========================================

        showSubmissionMessage(
            "Running... ⏳",
            "running"
        );


        // =========================================
        // STEP 4
        // CHECK RESULT
        // =========================================

        checkSubmissionStatus(
            submission.id
        );


    } catch (error) {

        console.error(
            "Submission error:",
            error
        );


        showSubmissionMessage(
            error.message ||
            "Something went wrong while submitting.",
            "error"
        );


        finishSubmission();
    }
}

// =========================================================
// CHECK SUBMISSION STATUS
// =========================================================

async function checkSubmissionStatus(submissionId) {

    try {

        const response =
            await fetch(
                `http://localhost:8080/submissions/${submissionId}`,
                {
                    method: "GET",
                    headers: getHeaders()
                }
            );


        if (!response.ok) {

            throw new Error(
                "Unable to check submission status."
            );
        }


        const submission =
            await response.json();


        console.log(
            "Current submission:",
            submission
        );


        const status =
            submission.status;


        // =========================================
        // ACCEPTED
        // =========================================

        if (status === "ACCEPTED") {

            showSubmissionMessage(
                "Accepted ✅",
                "success"
            );

            finishSubmission();

            return;
        }


        // =========================================
        // REJECTED
        // =========================================

        if (status === "REJECTED") {

            showSubmissionMessage(
                "Rejected ❌",
                "error"
            );

            finishSubmission();

            return;
        }


        // =========================================
        // STILL RUNNING
        // =========================================

        showSubmissionMessage(
            "Running... ⏳",
            "running"
        );


        // Check again after 1.5 seconds

        setTimeout(
            function () {

                checkSubmissionStatus(
                    submissionId
                );

            },
            1500
        );


    } catch (error) {

        console.error(
            "Status checking error:",
            error
        );


        showSubmissionMessage(
            "Unable to get submission result.",
            "error"
        );


        finishSubmission();
    }
}

// =========================================================
// FINISH SUBMISSION
// =========================================================

function finishSubmission() {

    const submitButton =
        document.getElementById(
            "submitButton"
        );


    submitButton.disabled = false;

    submitButton.textContent =
        "Submit";
}

// =========================================================
// SHOW SUBMISSION MESSAGE
// =========================================================

function showSubmissionMessage(
    message,
    type
) {

    const element =
        document.getElementById(
            "submissionMessage"
        );


    element.textContent =
        message;


    element.className =
        "submission-message " +
        type;


    element.classList.remove(
        "hidden"
    );
}


// =========================================================
// ESCAPE HTML
// =========================================================

function escapeHtml(value) {

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


// =========================================================
// TAB SUPPORT IN CODE EDITOR
// =========================================================

function enableTabSupport() {

    const editor =
        document.getElementById(
            "sourceCode"
        );


    editor.addEventListener(
        "keydown",
        function (event) {

            if (event.key === "Tab") {

                event.preventDefault();


                const start =
                    this.selectionStart;


                const end =
                    this.selectionEnd;


                this.value =
                    this.value.substring(
                        0,
                        start
                    )
                    + "    "
                    + this.value.substring(
                        end
                    );


                this.selectionStart =
                    this.selectionEnd =
                        start + 4;

            }

        }
    );
}


// =========================================================
// INITIALIZE PAGE
// =========================================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        const language =
            document.getElementById(
                "language"
            );


        const submitButton =
            document.getElementById(
                "submitButton"
            );


        language.addEventListener(
            "change",
            handleLanguageChange
        );


        submitButton.addEventListener(
            "click",
            submitSolution
        );


        enableTabSupport();


        loadProblem();

    }
);

