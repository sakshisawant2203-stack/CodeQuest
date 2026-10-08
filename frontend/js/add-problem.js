const problemForm =
    document.getElementById("problemForm");


problemForm.addEventListener("submit", function(event) {

    event.preventDefault();


    const title =
        document.getElementById("title").value;

    const difficulty =
        document.getElementById("difficulty").value;

    const description =
        document.getElementById("description").value;


    console.log(title);

    console.log(difficulty);

    console.log(description);


    alert("Problem data collected!");

});