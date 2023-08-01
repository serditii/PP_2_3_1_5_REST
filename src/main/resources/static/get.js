async function getUsers() {
    const response = await fetch("http://localhost:8080/api/admin/users/", {
        method: "GET",
        headers: {"Accept": "application/json"}
    });
    if (response.ok === true) {
        const users = await response.json();

        const table = document.getElementById("table");
        const div1 = document.createElement("div");
        div1.setAttribute("class", "table-responsive small  ms-3 border");
        const div2 = document.createElement("div");
        div2.setAttribute("class", "my-3 mx-3");
        const tableTag = document.createElement("table");
        tableTag.setAttribute("class", "table table-striped table-sm border");
        const thead = document.createElement("thead");
        const tr = document.createElement("tr");

        const thId = document.createElement("th");
        thId.append("ID");
        tr.append(thId)

        const thFirstName = document.createElement("th");
        thFirstName.append("FirstName");
        tr.append(thFirstName)

        const thLastName = document.createElement("th");
        thLastName.append("LastName");
        tr.append(thLastName)

        const thAge = document.createElement("th");
        thAge.append("Age");
        tr.append(thAge)

        const thEmail = document.createElement("th");
        thEmail.append("Email");
        tr.append(thEmail)

        const thRoles = document.createElement("th");
        thRoles.append("Roles");
        tr.append(thRoles)

        const thEdit = document.createElement("th");
        thEdit.append("Edit");
        tr.append(thEdit)

        const thDelete = document.createElement("th");
        thDelete.append("Delete");
        tr.append(thDelete)

        const tbody = document.createElement("tbody");

        thead.append(tr);
        tableTag.append(thead);
        tableTag.append(tbody);
        div2.append(tableTag);
        div1.append(div2);
        table.replaceChildren(div1);
        let rows = document.querySelector("tbody");
        users.forEach(user => {
            rows.append(row(user));
        });
    }
}

// создание строки для таблицы
function row(user) {

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

    // кнопка Edit в таблице
    const linksTd1 = document.createElement("td");
    const editLink = document.createElement("button");
    editLink.setAttribute("data-id", user.id);
    editLink.setAttribute("type", "button");
    editLink.setAttribute("class", "btn btn-success btn-sm");
    editLink.setAttribute("data-bs-toggle", "modal");
    editLink.setAttribute("data-bs-target", "#exampleModal");
    editLink.append("Edit");
    editLink.addEventListener("click", e => {
        e.preventDefault();
        getUser(user.id);
    });
    linksTd1.append(editLink);
    tr.appendChild(linksTd1);

    // кнопка Edit в модальном окне
    document.forms["editForm"].addEventListener("submit", e => {
        e.preventDefault();
        const form = document.forms["editForm"];
        const id = form.elements["id"].value;
        const name = form.elements["name"].value;
        const lastname = form.elements["lastname"].value;
        const age = form.elements["age"].value;
        const email = form.elements["email"].value;
        const password = form.elements["password"].value;
        const role = form.elements["role"].value;
        editUser(id, name, lastname, age, email, password, role);
    });

// кнопка Delete
    const linksTd2 = document.createElement("td");
    const removeLink = document.createElement("button");
    removeLink.setAttribute("data-id", user.id);
    removeLink.setAttribute("type", "button");
    removeLink.setAttribute("class", "btn btn-danger btn-sm");
    removeLink.setAttribute("data-bs-toggle", "modal");
    removeLink.setAttribute("data-bs-target", "#exampleModalDelete");
    removeLink.append("Delete");
    removeLink.addEventListener("click", e => {
        e.preventDefault();
        getUser(user.id);
    });
    linksTd2.append(removeLink);
    tr.appendChild(linksTd2);

    // кнопка Delete в модальном окне
    document.forms["deleteForm"].addEventListener("submit", e => {
        e.preventDefault();
        const form = document.forms["editForm"];
        const id = form.elements["id"].value;
        deleteUser(id);
    });
    return tr;
}
