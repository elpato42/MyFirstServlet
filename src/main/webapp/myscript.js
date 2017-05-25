/**
 * Created by Екатерина on 24.05.2017.
 */

$(document).ready(function () {
    var $userName;

    $("#sendButton1").click(function () {
        $.post("/xyz", {"message": $("#msg").val()}, function (data) {
            $("#chatlog").val($("#chatlog").val() + "\n" + data);
            $("#msg").val("");
        });
    });
    $("#logButton").click(function () {
        if($("#login").val !== '' && $("#password").val !== ''){
            hideDiv("authorise");
            showDiv("chat");
            $userName == $("#login").val;
        } else {
            //document.getElementById('text').setAttribute(m, 'Введите логин и пароль')

        }

    });

    $("#finishButton").click(function () {
        showDiv("authorise");
        hideDiv("chat");
    });

    function showDiv(id) {
        document.getElementById(id).style.display = 'block';
    }
    function hideDiv(id) {
        document.getElementById(id).style.display = 'none';
    }


});
