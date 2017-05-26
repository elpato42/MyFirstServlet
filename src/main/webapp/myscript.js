$(document).ready(function () {
    var $chatLog = $('#log'), $message = $('#message'), $btn = $('#send');

    $btn.click(function () {
        $.post("/xyz", {"message": $message.val()}, function (data) {
            $message.val('');
        });
    });

    var getCallback = function (data) {
        for (var o in data) if (data.hasOwnProperty(o)) {
            var row = data[o];
            buildHtml(row);
        }
    };

    var buildHtml = function (row) {
        $chatLog.append(
            '<div class="row">' +
            '<span class="date">' + row['date'] + '</span>' +
            '<span class="user">' + row['user'] + '</span>' +
            '<span class="message">' + row['message'] + '</span>' +
            '</div>'
        )
    };

    var checkIncoming = function () {
        $.get('/xyz', getCallback, 'json');
    };

    $.get('/xyz', {'limit': 10}, getCallback, 'json');
    setInterval(checkIncoming, 3000);
});
