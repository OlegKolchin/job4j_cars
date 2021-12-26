function reg() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/reg.do',
        data: {
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val(),
        }
    }).done(function () {
        window.location.href = 'http://localhost:8080/cars/login.html';
    }).fail(function (err) {
        if (err.status === 409) {
            alert('Пользователь с указанным email или именем уже существует!');
        }
        console.log(err);
    });
}

function validate() {
    var name = $('#name').val();
    var email = $('#email').val();
    var password = $('#password').val();
    if (name == '' || email == '' || password == "") {
        alert('Заполните все поля!');
        return false;
    }
    return true;
}