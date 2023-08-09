async function deleteUser() {
    const form = document.forms["deleteForm"];
    const id = form.elements["id"].value;
    const response = await fetch("http://localhost:8080/api/admin/users/" + id, {
        method: "DELETE"
    });
    if (response.ok === true) {
        await getUsers();
    }
}
