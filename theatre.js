function validateName(value) {
    var pattern = /^[A-Za-z ]{3,20}$/;
    return pattern.test(value);
}

function validateEmail(value) {
    var pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return pattern.test(value);
}

function report(errors) {
    var msg = "Please enter valid data: <br />";
    for (var i = 0; i < errors.length; i++) {
        msg += "<br />" + (i + 1) + ". " + errors[i];
    }
    document.getElementById("errors").innerHTML = msg;
    document.getElementById("errors").style.display = 'block';
}

function validateForm(form) {
    var errors = [];
    if (!(form.username.value.length > 0)) {
        errors[errors.length] = 'invalid userid';
    }
    if (!validateName(form.name.value)) {
        errors[errors.length] = 'invalid name';
    }
    if (!validateEmail(form.email.value)) {
        errors[errors.length] = 'invalid email';
    }
    if (!(form.code.value == form.security_code.value)) {
        errors[errors.length] = 'invalid security code';
    }
    if (errors.length > 0) {
        report(errors);
    }
    return (errors.length == 0);
}