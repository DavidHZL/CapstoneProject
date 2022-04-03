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
                <div class="profilePostHeader">
                    <span id="editPost${post.postID}"><img src="resources/editIcon.png" class="editIcon" alt="editPostIcon"></span>
                    <h3 class="postCaption">${post.caption}</h3>
                    <span id="deletePost${post.postID}"><img src="resources/deleteIcon.png" class="deleteIcon" alt="deletePostIcon"></span>
                </div>
                <image src="resources/${post.imageName}" class="img-post" alt="A post from a user"/>
                <p class="description-post">${post.description}</p>
                <div class="tags-post">
                    <p class="tag">tag Example</p>
                </div>
            </div>
            `;
    });

    $("#profileContent").append(profileContentHTML);

    displayAddCaptionForm();
    
    displayEditPostForm();
}

function displayEditPostForm(){
    
    currentUser.posts.forEach( (post) => {
        $("#editPost" + post.postID).click(function () {
            $("#mainModal").html(
                    `<div id="modalBox" class="modalContent">
                        <span id="modalCloseButton" class="closeButton">&times;</span>
                        <div id="modalContent">
                        <h1 class="modalTitle">Edit A Post</h1>
            
                        <div class="controlContainer">
                            <label for="editPostCaption">Caption: </label>
                            <input type="text" id="editPostCaption" name="editPostCaption" value="${post.caption}">
                        </div><br><br>

                        <div class="controlContainer">
                            <label for="editPostDescription">Description: </label>
                            <input type="text" id="editPostDescription" name="editPostDescription" value="${post.description}">
                        </div><br><br>

                            <div class="controlContainer">
                                <input type="button" id="editPostBtn" class="styledBtn" data-postid="${post.postID}" value="Edit Post">
                            </div>
                        </div>
                    </div>`
            );
    
            $("#editPostBtn").click(() => {
                $.ajax({
                    type: "POST",
                    url: "EditPost",
                    data: {"postID" : $(this).attr("data-postid"),
                            "editedPostCaption" : $("#editPostCaption").val(),
                            "editedPostDescription" : $("#editPostDescription").val()},
                    dataType: "JSON",
                    success: (result) => {
                        $("#mainModal").fadeOut(500);
                    },
                    error: function (jqXHR, ex) {
                        console.log(jqXHR);
                    }
                });
            });
    
            $("#modalCloseButton").click(() => {
                $("#mainModal").fadeOut(500);
                loadCurrentUser();
            });

            $("#mainModal").fadeIn(200);
        });
        
    });
    
}

function displayAddCaptionForm() {
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