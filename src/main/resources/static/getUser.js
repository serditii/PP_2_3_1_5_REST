async function getUser1() {
    const response = await fetch("http://localhost:8080/api/admin/user", {
        method: "GET",
        headers: {"Accept": "application/json"}
    });

    if (response.ok === true) {
        const user = await response.json();
        document.getElementById('1').innerText = user.email;
        document.getElementById('2').innerText = user.role;
        const exp = /ADMIN/;
        const result = exp.test(user.role);
        if (result) {
            let Link3 = document.getElementById('3');
            const adminLink = document.createElement("a");
            adminLink.setAttribute("href", "/admin");
            adminLink.setAttribute("class", "nav-link");
            adminLink.append("Admin");
            Link3.append(adminLink);

            let Link4 = document.getElementById('4');
            const userLink = document.createElement("a");
            userLink.setAttribute("href", "#");
            userLink.setAttribute("aria-current", "page");
            userLink.setAttribute("class", "nav-link active");
            userLink.append("User");
            Link4.append(userLink);

        } else {
            let Link3 = document.getElementById('3');
            const userLink = document.createElement("a");
            userLink.setAttribute("href", "#");
            userLink.setAttribute("aria-current", "page");
            userLink.setAttribute("class", "nav-link active");
            userLink.append("User");
            Link3.append(userLink);
        }
        document.querySelector("tbody").append(rowUser(user));
    }
}

function rowUser(user) {

    const tr = document.createElement("tr");
    tr.setAttribute("data-rowid", user.id);

    const idTd = document.createElement("td");
    idTd.append(user.id);
    tr.append(idTd);

    const nameTd = document.createElement("td");
    nameTd.append(user.name);
    tr.append(nameTd);

    const lastnameTd = document.createElement("td");
    lastnameTd.append(user.lastname);
    tr.append(lastnameTd);

    const ageTd = document.createElement("td");
    ageTd.append(user.age);
    tr.append(ageTd);

    const emailTd = document.createElement("td");
    emailTd.append(user.email);
    tr.append(emailTd);

    const roleTd = document.createElement("td");
    roleTd.append(user.role);
    tr.append(roleTd);

    return tr;
}