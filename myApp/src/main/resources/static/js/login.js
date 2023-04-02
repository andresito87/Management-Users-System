// Call the dataTables jQuery plugin
$(document).ready(function () {
    //on ready
});

async function iniciarSesion() {

    let datos = {};
    datos.email = document.getElementById("txtEmail").value;
    datos.password = document.getElementById("txtPassword").value;


    const request = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    const respuesta = await request.text();
    if (respuesta !== "FAIL") {
        localStorage.setItem("token", respuesta); //guardamos el token en el localStorage del navegador
        localStorage.setItem("email", datos.email); //guardamos el email en el localStorage del navegador
        window.location.href = "usuarios.html";
    }else {
        alert("Usuario o contraseña incorrectos. Por favor, intente de nuevo.");
    }
}
