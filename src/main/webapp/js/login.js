function auth() {
    var email = $('#email').val();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/auth.do',
        data: {
            email: email,
            password: $('#password').val(),
        }
    }).done(function () {
        setSessionName(email);
    }).fail(function (err) {
        if (err.status === 400) {
            alert('Неверный email или пароль!')
        }
        console.log(err);
    });
}

function validate() {
    var email = $('#email').val();
    var password = $('#password').val();
    if (email == '' || password == "") {
        alert('Заполните все поля!');
        return false;
    }
    return true;
}

function setSessionName(email) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/auth.do',
        dataType: 'json',
        data: {
            email: email,
        }
    }).done(function (data) {
        sessionStorage.setItem('user_name', data.name);
        sessionStorage.setItem('user_id', data.id);
        location.href = 'http://localhost:8080/cars/';
    }).fail(function (err) {
        console.log(err);
    });
}

