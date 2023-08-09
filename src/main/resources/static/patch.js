async function editUser() {
    const form = document.forms["editForm"];
    const userJSON = JSON.stringify({
        id: form.elements["id"].value,
        name: form.elements["name"].value,
        lastname: form.elements["lastname"].value,
        age: parseInt(form.elements["age"].value, 10),
        email: form.elements["email"].value,
        password: form.elements["password"].value,
        role: form.elements["role"].value
    });
    const response = await fetch("http://localhost:8080/api/admin/users", {
        method: "PATCH",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: userJSON
    });
    if (response.ok === true) {
        await getUsers();
    }
}