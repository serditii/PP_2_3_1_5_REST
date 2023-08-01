async function editUser(userId, userName, userLastname, userAge, userEmail, userPassword, userRole
) {
    const userJSON = JSON.stringify({
        id: userId,
        name: userName,
        lastname: userLastname,
        age: parseInt(userAge, 10),
        email: userEmail,
        password: userPassword,
        role: userRole
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