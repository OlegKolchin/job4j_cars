function add() {
    var form = $('#data')[0];
    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "http://localhost:8080/cars/ad.do",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            alert('Успешно!')
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function editItem(obj) {
    var id = obj.id;
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/edit.do',
        data: {
            id: id
        }
    }).done(function () {
        var badgeId = 'badge_' + id.split('checkbox_')[1];
        if (document.getElementById(badgeId).innerText === 'В продаже') {
            $('#' + badgeId).replaceWith('<span class="badge bg-secondary" id="' + badgeId + '"' + '>Продано</span>');
        } else {
            $('#' + badgeId).replaceWith('<span class="badge bg-success" id="' + badgeId + '"' + '>В продаже</span>');
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function validate() {
    var array = [$('#description').val(), $('#brand').val(), $('#body').val(), $('#model').val, $('#formFile').val()];
    for (var i of array) {
        if (i === '' || i === null) {
            alert('Заполните все поля');
            return false;
        }
        return true;
    }
}



