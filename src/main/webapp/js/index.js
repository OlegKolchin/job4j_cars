$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/ad.do',
        dataType: 'json'
    }).done(function (data) {
        for (var ad of data) {
            var fileName = ad.user.id + '_' + ad.car.brand.name + '_' + ad.car.name + '.png';
            var badgeId = '"badge_' + 'user_' + ad.user.id + "_ad" + ad.id +'"';
            var checkboxId = '"checkbox_' + 'user_' + ad.user.id + "_ad" + ad.id +'"';

            var badge = '';
            if (ad.sold === false) {
                badge = '<td><span class="badge bg-success" id=' + badgeId + '>В продаже</span></td>';
            } else {
                badge = '<td><span class="badge bg-secondary" id=' + badgeId + '>Продано</span></td>';
            }

            $('#ads').append('<tr>\n' +
                '            <td><img src="http://localhost:8080/cars/download.do?name=' + fileName
                + '"' +'class="img-fluid" ' + 'onclick="window.open(this.src)"/> </td>\n' +
                '            <td>\n' +
                '                <p>\n' +
                '                <button class="btn btn-primary" type="button" data-bs-toggle="collapse" ' +
                'data-bs-target="#collapse' + ad.id + '"\n' +
                '                        aria-expanded="false">\n' +
                '                    Подробности\n' +
                '                </button>\n' +
                '            </p>\n' +
                '                <div class="collapse bg-light" id="collapse' + ad.id + '">\n' +
                '                    <div class="card card-body bg-light border-light">\n' + ad.description +
                '                        \n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </td>\n' +
                '            <td>' + ad.car.name + '</td>\n' +
                '            <td>' + ad.car.brand.name + '</td>\n' +
                '            <td>' + ad.created + '</td>\n' +
                badge +
                '            <td>' + '<div class="form-check">\n' +
                '  <input class="form-check-input" type="checkbox" value="" id=' + checkboxId + ' disabled onchange="editItem(this)">\n' +
                '  <label class="form-check-label" for="flexCheckDefault">\n' +
                '  </label>\n' +
                '</div>' + '</td>\n' +
                '        </tr>')
        }
        auth();
        enableCheckboxes();
    }).fail(function (err) {
        console.log(err);
    });
});

function logout() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/logout.do',
    }).done(function (data) {
    }).fail(function (err) {
        console.log(err);
    });
    sessionStorage.clear();
    location.href = 'http://localhost:8080/cars/';
}

function auth() {
    var user = sessionStorage.getItem('user_name');
    if (user != null) {
        var firstLetter = user.charAt(0)
        $('#upperNav').replaceWith('<nav class="navbar navbar-expand-lg navbar-light bg-light">\n' +
            '    <div class="container-fluid">\n' +
            '        <a class="navbar-brand" href="http://localhost:8080/cars/">\n' +
            '            <img src="icons/car.svg" width="64" height="64"/>\n' +
            '            UsedAuto.ru\n' +
            '        </a>\n' +
            '        <div class="collapse navbar-collapse" id="navbarSupportedContent">\n' +
            '            <ul class="navbar-nav me-auto mb-2 mb-lg-0">\n' +
            '                <li class="nav-item ui-corner-right">\n' +
            '                    <a class="nav-link" href="http://localhost:8080/cars/add.html">Разместить объявление</a>\n' +
            '                </li>\n' +
            '            </ul>\n' +
            '            <ul class="navbar-nav ml-auto">\n' +
            '                <li class="nav-item">\n' +
            '                    <button type="button" class="btn btn-primary rounded-circle" disabled>'+ firstLetter +'</button>\n' +
            '                </li>\n' +
            '                <li class="nav-item ui-corner-right">\n' +
            '                    <a class="nav-link" href="#" onclick="logout()">Выход</a>\n' +
            '                </li>\n' +
            '            </ul>\n' +
            '        </div>\n' +
            '    </div>\n' +
            '</nav>')
    }
}

function enableCheckboxes() {
    var checkboxes = document.querySelectorAll('input[type=checkbox]');
    var user_id = 'user_' + sessionStorage.getItem("user_id")
    for (var i of checkboxes) {
        if (i.id.includes(user_id)) {
            i.disabled = false;
        }
    }
}



