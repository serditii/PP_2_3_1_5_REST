async function getUser(id) {
    const response = await fetch("http://localhost:8080/users/" + id, {
        method: "GET",
        headers: {"Accept": "application/json"}
    });
    if (response.ok === true) {
        const user = await response.json();
        const form = document.forms["editForm"];
        form.elements["id"].value = user.id;
        form.elements["name"].value = user.name;
        form.elements["lastname"].value = user.lastname;
        form.elements["age"].value = user.age;
        form.elements["email"].value = user.email;
        form.elements["role"].value = user.role;
        const form1 = document.forms["deleteForm"];
        form1.elements["id"].value = user.id;
        form1.elements["name"].value = user.name;
        form1.elements["lastname"].value = user.lastname;
        form1.elements["age"].value = user.age;
        form1.elements["email"].value = user.email;
        form1.elements["role"].value = user.role;
    }
}
