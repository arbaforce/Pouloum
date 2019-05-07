var userID;

function load_userID(userID) {
    $.ajax({
        url: './AjaxAction',
        type: 'POST',
        data: {
            'action': 'getUserIdSession',
        },
        datatype: 'json'
    }).done(function (data){
        if (data.result) {
            userID = data.userID;
            ready_userID(userID);
        }
        else {
            window.location.href = './index.html';
        }
    });
}

$(document).ready(function() {
    load_userID();
});

var activityID;

function load_activityID() {
    $.ajax({
        url: './AjaxAction',
        type: 'POST',
        data: {
            'action': 'getActivityIdSession',
        },
        datatype: 'json'
    }).done(function (data){
        if (data.result){
            activityID = data.activityID;
            ready_activityID(activityID);
        }
        else {
            window.location.href = './activities_browser.html';
        }
    });
}

var eventID;

function load_eventID() {
    $.ajax({
        url: './AjaxAction',
        type: 'POST',
        data: {
            'action': 'getEventIdSession',
        },
        datatype: 'json'
    }).done(function (data) {
        if (data.result) {
            eventID = data.eventID;
            ready_eventID(eventID);
        } else {
            history.back();
        }
    });
}

