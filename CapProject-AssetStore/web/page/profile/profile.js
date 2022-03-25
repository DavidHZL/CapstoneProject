"use strict";

var currentUser;

$(document).ready(() => {
    loadCurrentUser();
});

function loadCurrentUser() {
    currentUser = "";
    $.ajax({
        type: "GET",
        url: "Profile",
        dataType: "JSON",
        success: (result) => {
            currentUser = result;
            displayProfile();
        },
        error: function (jqXHR, ex) {
            console.log(jqXHR);
        }
    });
}

function displayProfile() {
    $("#profileContent").empty();
    
    var profileContentHTML = 
            `
            <input type="text" id="profileName" class="profileDisplayBox" value="${currentUser.profileName}" readonly>
            <br><br>
            <input type="text" id="profileEmail" class="profileDisplayBox" value="${currentUser.email}" readonly>
            <br><br>
            <label id="profileFollowerCount">${currentUser.followerCount}</label>
            `;
    
    $("#profileContent").append(profileContentHTML);
}