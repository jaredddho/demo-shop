function changeLocale(locale, callback) {
    const xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            callback(xmlHttp.responseText)
        }
    }
    xmlHttp.open('GET', `/locale?locale=${locale}`,
        true);
    xmlHttp.send(null);
}

function login(username, password, callback) {
    $.ajax({
        url: '/api/customers/login',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            'username': username,
            'password': password
        })
    }).done(function (data) {
        callback(data)
    });
}

function toggleAuthState(authenticated) {
    if (authenticated) {
        $.ajax({
            url: '/api/customers/me',
            method: 'POST'
        }).done(function (data) {
            console.log(JSON.stringify(data));
        });
    } else {

    }
}

$(document).ready(function () {});