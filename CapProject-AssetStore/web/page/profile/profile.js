"use strict";

var currentUser;

$(document).ready(() => {

    $(window).click(function (e) {
        if (e.target.id === "mainModal") {
            $("#mainModal").fadeOut(100);
        }
    });

    loadCurrentUser();
});

function loadCurrentUser() {
    currentUser = "";
    $.ajax({
        type: "GET",
        url: "ProfileManager",
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
            <h1 class="profileHeader">
                <div class="profileName">${currentUser.email}</div>
                <div class="profileEmail">${currentUser.profileName}</div>
                <div class="followerCount">${currentUser.followerCount} Followers</div>
            </h1>

            `;

    if (typeof currentUser.profileCaption !== "undefined") {
        profileContentHTML +=
                `
            <div class="profileCaption">
                    <p >${currentUser.profileCaption}</p>
            </div>
            `;
    } else {
        profileContentHTML +=
                `
            <div class="profileCaption">
                    <p>Click here to add a caption!</p>
                    <input type="button" class="styledBtn" id="addCaptionButton" value="Add Caption" />
            </div>
            `;
    }



    currentUser.posts.forEach((post) => {
        profileContentHTML +=
            `
            <div class="card-post">
                <h3 class="postCaption">${post.caption}</h3>
                <image src="resources/${post.imageName}" class="img-post" alt="A post from a user"/>
                <p class="description-post">${post.description}</p>
                <div class="tags-post">
                    <p class="tag">tag Example</p>
                </div>
            </div>
            `;
    });

    $("#profileContent").append(profileContentHTML);

        $("#addCaptionButton").click(function () {
            $("#mainModal").html(
                    `<div id="modalBox" class="modalContent">
                        <span id="modalCloseButton" class="closeButton">&times;</span>
                        <div id="modalContent">
                            <h1 class="modalTitle">Add a caption for your Profile!</h1>

                            <div class="controlContainer">
                                <textarea id="newProfileCaption" class="newCaptionTextArea">
                                    Add a Caption here!
                                </textarea>
                            </div><br><br>

                            <div class="controlContainer">
                                <input type="button" id="createCaptionBtn" class="styledBtn" data-profileid="${currentUser.profileID}" value="Add Caption">
                            </div>
                        </div>
                    </div>`
                    );
            
            $("#createCaptionBtn").click(function() {
                $.ajax({
                   type:"Post",
                   url:"CaptionManager",
                   data : {"newCaption" : $("#newProfileCaption").val(),
                            "profileID" : $(this).attr("data-profileid")},
                   dataType: "JSON",
                    success: (result) => {
                        $("#mainModal").fadeOut(500);
                        loadCurrentUser();
                    },
                    error: function (jqXHR, ex) {
                        console.log(jqXHR);
                    }
                });
            });

            $("#modalCloseButton").click(() => {
                $("#mainModal").fadeOut(500);
            });

            $("#mainModal").fadeIn(200);
        });
}