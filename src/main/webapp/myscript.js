$(document).ready(function () {
    var $chatLog = $('#log'), $message = $('#message'), $btn = $('#send');

    $btn.click(function () {
        $message.attribute('disabled', true);

        $.post("/xyz", {"message": $message.val()}, function (data) {
            $message.attribute('disabled', false);
            $message.val('');
        });
    });

    var checkIncoming = function () {
        $.get('/xyz', function (data) {
            for (var o in data) if (data.hasOwnProperty(o)) {
                var row = data[o];
                $chatLog.append(
                    '<div class="row">' +
                        '<span class="date">' + row['date'] + '</span>' +
                        '<span class="user">' + row['user'] + '</span>' +
                        '<span class="message">' + row['message'] + '</span>' +
                    '</div>'
                )
            }
        }, 'json');
    };

    setInterval(checkIncoming, 3000);
});
