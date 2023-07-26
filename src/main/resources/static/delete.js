async function deleteUser(id) {
    const response = await fetch("http://localhost:8080/users/" + id, {
        method: "DELETE",
        headers: {"Accept": "application/json"}
    });
    if (response.ok === true) {
        await getUsers();
    }
}
