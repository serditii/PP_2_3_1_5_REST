async function getAdmin() {
    const response = await fetch("http://localhost:8080/api/admin/user", {
        method: "GET", headers: {"Accept": "application/json"}
    });

    if (response.ok === true) {
        const user = await response.json();
        //Поля в шапку
        document.getElementById('1').innerText = user.email;
        document.getElementById('2').innerText = user.role;

        //Заголовок таблицы
        let Link6 = document.getElementById('6');
        Link6.textContent = "All users";

        //Кнопка Admin
        let link3 = document.getElementById('3');
        const adminLink = document.createElement("a");
        adminLink.setAttribute("href", "#");
        adminLink.setAttribute("aria-current", "page");
        adminLink.setAttribute("class", "nav-link active");
        adminLink.append("Admin");
        adminLink.addEventListener("click", e => {
            e.preventDefault();
            getUser1();
        });
        link3.replaceChildren(adminLink);

        //Кнопка User
        let link4 = document.getElementById('4');
        const userLink = document.createElement("a");
        userLink.setAttribute("href", "/user");
        userLink.setAttribute("class", "nav-link");
        userLink.append("User");
        link4.replaceChildren(userLink);

        //Кнопка Users table
        let linkUsersTable = document.getElementById('Users table');
        const usersTableLink = document.createElement("a");
        usersTableLink.setAttribute("href", "#");
        usersTableLink.setAttribute("class", "nav-link px-2 text-dark border");
        usersTableLink.append("Users table");
        linkUsersTable.replaceChildren(usersTableLink);

        //Кнопка New user
        let linkNewUser = document.getElementById('New user');
        const newUserLink = document.createElement("a");
        newUserLink.setAttribute("href", "#");
        newUserLink.setAttribute("class", "nav-link");
        newUserLink.append("New User");
        newUserLink.addEventListener("click", e => {
            e.preventDefault();
            newUser();
        });
        linkNewUser.replaceChildren(newUserLink);
    }
}

//Метод Add new user
function newUser() {

    //Заголовок таблицы
    let Link6 = document.getElementById('6');
    Link6.textContent = "Add new user";

    //Кнопка Users table
    let linkUsersTable1 = document.getElementById('Users table');
    const usersTableLink1 = document.createElement("a");
    usersTableLink1.setAttribute("href", "#");
    usersTableLink1.setAttribute("class", "nav-link");
    usersTableLink1.append("Users table");
    usersTableLink1.addEventListener("click", e => {
        e.preventDefault();
        getUsers();
    });
    linkUsersTable1.replaceChildren(usersTableLink1);

    //Кнопка New user
    let linkNewUser1 = document.getElementById('New user');
    const newUserLink1 = document.createElement("a");
    newUserLink1.setAttribute("href", "#");
    newUserLink1.setAttribute("class", "nav-link px-2 text-dark border");
    newUserLink1.append("New User");
    linkNewUser1.replaceChildren(newUserLink1);

    //Таблица
    const table = document.getElementById("table");
    table.replaceChildren();
    const form1 = document.createElement("form");

    const div1 = document.createElement("div");
    div1.setAttribute("class", "ms-3");

    const form2 = document.createElement("form");
    form2.setAttribute("class", "border");
    form2.setAttribute("name", "userForm");

    const div2 = document.createElement("div");
    div2.setAttribute("class", "border");

//Поле name
    const divName = document.createElement("div");
    divName.setAttribute("class", "form-group text-center fw-bold mt-3");

    const labelName = document.createElement("label");
    labelName.setAttribute("for", "name");
    labelName.textContent = "First name";

    const inputName = document.createElement("input");
    inputName.setAttribute("class", "form-control w-25 p-1 " + "text-dark bg-opacity-25 mx-auto");
    inputName.setAttribute("type", "text");
    inputName.setAttribute("id", "name");
    inputName.setAttribute("placeholder", "First name");
    inputName.setAttribute("name", "name");
    divName.append(labelName);
    divName.append(inputName);
    div2.append(divName);

    //Поле LastName
    const divLastName = document.createElement("div");
    divLastName.setAttribute("class", "form-group text-center fw-bold mt-2");

    const labelLastName = document.createElement("label");
    labelLastName.setAttribute("for", "lastname");
    labelLastName.textContent = "Last name";

    const inputLastName = document.createElement("input");
    inputLastName.setAttribute("class", "form-control w-25 p-1 " + "text-dark bg-opacity-25 mx-auto");
    inputLastName.setAttribute("type", "text");
    inputLastName.setAttribute("id", "lastname");
    inputLastName.setAttribute("placeholder", "Last name");
    inputLastName.setAttribute("name", "lastname");
    divLastName.append(labelLastName);
    divLastName.append(inputLastName);
    div2.append(divLastName);

    //Поле Age
    const divAge = document.createElement("div");
    divAge.setAttribute("class", "form-group text-center fw-bold mt-2");

    const labelAge = document.createElement("label");
    labelAge.setAttribute("for", "age");
    labelAge.textContent = "Age";

    const inputAge = document.createElement("input");
    inputAge.setAttribute("class", "form-control w-25 p-1 " + "text-dark bg-opacity-25 mx-auto");
    inputAge.setAttribute("type", "number");
    inputAge.setAttribute("id", "age");
    inputAge.setAttribute("placeholder", "Age");
    inputAge.setAttribute("name", "age");
    divAge.append(labelAge);
    divAge.append(inputAge);
    div2.append(divAge);

    //Поле Email
    const divEmail = document.createElement("div");
    divEmail.setAttribute("class", "form-group text-center fw-bold mt-3");

    const labelEmail = document.createElement("label");
    labelEmail.setAttribute("for", "email");
    labelEmail.textContent = "Email";

    const inputEmail = document.createElement("input");
    inputEmail.setAttribute("class", "form-control w-25 p-1 " + "text-dark bg-opacity-25 mx-auto");
    inputEmail.setAttribute("type", "text");
    inputEmail.setAttribute("id", "email");
    inputEmail.setAttribute("placeholder", "Email");
    inputEmail.setAttribute("name", "email");
    divEmail.append(labelEmail);
    divEmail.append(inputEmail);
    div2.append(divEmail);

    //Поле Password
    const divPassword = document.createElement("div");
    divPassword.setAttribute("class", "form-group text-center fw-bold mt-3");

    const labelPassword = document.createElement("label");
    labelPassword.setAttribute("for", "password");
    labelPassword.textContent = "Password";

    const inputPassword = document.createElement("input");
    inputPassword.setAttribute("class", "form-control w-25 p-1 " + "text-dark bg-opacity-25 mx-auto");
    inputPassword.setAttribute("type", "password");
    inputPassword.setAttribute("id", "password");
    inputPassword.setAttribute("placeholder", "Password");
    inputPassword.setAttribute("name", "password");
    divPassword.append(labelPassword);
    divPassword.append(inputPassword);
    div2.append(divPassword);

    //Поле Role
    const divRole = document.createElement("div");
    divRole.setAttribute("class", "form-group text-center fw-bold mt-3");

    const labelRole = document.createElement("label");
    labelRole.setAttribute("for", "role");
    labelRole.textContent = "Role";

    const selectRole = document.createElement("select");
    selectRole.setAttribute("class", "form-control w-25  p-1 " + "text-dark bg-opacity-25 mx-auto");
    selectRole.setAttribute("size", "2");
    selectRole.setAttribute("id", "role");
    selectRole.setAttribute("aria-label", "а");
    selectRole.setAttribute("name", "role");

    const optionRole = document.createElement("option");
    optionRole.setAttribute("value", "ROLE_USER");
    optionRole.textContent = "USER";

    const optionRole1 = document.createElement("option");
    optionRole1.setAttribute("value", "ROLE_ADMIN");
    optionRole1.textContent = "ADMIN";
    selectRole.append(optionRole);
    selectRole.append(optionRole1);
    divRole.append(labelRole);
    divRole.append(selectRole);
    div2.append(divRole);

//Кнопка добавить юзера
    const divAdd = document.createElement("div");
    divAdd.setAttribute("class", "d-grid gap-2 col-2 mx-auto mt-4");

    const inputAdd = document.createElement("input");
    inputAdd.setAttribute("class", "btn btn-success");
    inputAdd.setAttribute("type", "submit");
    inputAdd.setAttribute("value", "Add new user");
    divAdd.append(inputAdd);
    div2.append(divAdd);
    form2.append(div2);
    div1.append(form2);
    form1.append(div1);
    table.replaceChildren(form1);

    //Отправка формы
    document.forms["userForm"].addEventListener("submit", e => {
        e.preventDefault();
        const form = document.forms["userForm"];
        const name = form.elements["name"].value;
        const lastname = form.elements["lastname"].value;
        const age = form.elements["age"].value;
        const email = form.elements["email"].value;
        const password = form.elements["password"].value;
        const role = form.elements["role"].value;
        createUser(name, lastname, age, email, password, role);

    });
}
