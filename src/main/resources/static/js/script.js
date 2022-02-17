var stompClient = null;
var divId = 0;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
        document.getElementById("recipient").value="";
        document.getElementById("subject").value="";
        document.getElementById("textMessage").value="";
    });


});

function connect() {
    var socket = new SockJS('/new-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });

    });
}



function sendMessage() {
    console.log("sending private message");
    stompClient.send("/ws/messages", {},
        JSON.stringify({'recipient': $("#recipient").val(),
            'subject': $("#subject").val(),
            'textMessage': $("#textMessage").val()
        }));
}

function dateFormat(date){
    let d = new Date(date);
    return d.getHours().toString().padStart(2, '0')
        + ":" + d.getMinutes().toString().padStart(2,'0')
        + " " + d.getDate().toString().padStart(2,'0')
        + "." + (d.getMonth()+1).toString().padStart(2,'0')
        + "." + d.getFullYear();
}

function showMessage(message) {
    $("#messages").prepend(
        "<div class=\"container mb-2 p-3 card\" data-toggle=\"collapse\" href=\"#newmesssage"+ divId +"\" role=\"button\"\n" +
        "     aria-expanded=\"false\" aria-controls=\"collapseExample\">\n" +
        "    <div class=\"row\">\n" +
        "        <div class=\"col-2\">\n" +
        "            <b>From: </b>"+
        message.sender  +
        "</div>\n" +
        "        <div class=\"col-2\">\n" +
        "            <b>Date: </b>"+
        dateFormat(message.sendDate)+
        "</div>\n" +
        "        <div class=\"col-8\">\n" +
        "            <b>Subject: </b>" +
        message.subject +
        "</div>\n" +
        "    </div>\n" +
        "    <div class=\"row collapse mt-2\" id=\"newmesssage"+ divId +"\">\n" +
        "        <div class=\"col\">" +
        message.textMessage +
        "</div>\n" +
        "    </div>\n" +
        "</div>"
    );

    divId++;
}








