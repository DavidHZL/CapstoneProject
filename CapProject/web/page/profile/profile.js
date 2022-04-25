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

    profileContentHTML += `<div class="allPostContainer">`;

    currentUser.posts.forEach((post) => {
        profileContentHTML +=
            `
            <div class="card-post">
                <div class="post-info">
                    <div class="post-main-info">
                        <h3 class="postCaption">${post.caption}</h3>
                        <p class="description-post">${post.description}</p>
                    </div>

                    <div class="post-controls">
                        <span id="editPost${post.postID}"><img src="resources/editIcon.png" class="editIcon" alt="editPostIcon"></span>
                        <span id="deletePost${post.postID}" data-postid="${post.postID}"><img src="resources/deleteIcon.png" class="deleteIcon" alt="deletePostIcon"></span>
                    </div>
                </div>
                <div class="post-img-container">
                    <image src="resources/${post.imageName}" class="img-post" alt="A post from a user"/>
                </div>
            </div>
            `;
        
    });

    profileContentHTML += `</div>`;

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
                            <input type="text" id="editPostCaption" class="styledTxtInput" name="editPostCaption" placeholder="Add a Caption" value="${post.caption}">
                        </div><br><br>

                        <div class="controlContainer">
                            <label for="editPostDescription">Description: </label>
                            <input type="text" class="styledTxtInput styledTextArea" id="editPostDescription" name="editPostDescription" value="${post.description}">
                        </div><br><br>

                        <input type="button" id="editPostBtn" class="styledBtn" data-postid="${post.postID}" value="Edit Post">
                        </div>
                    </div>`
            );
    
            $("#editPostBtn").click(editPost);
    
            $("#modalCloseButton").click(() => {
                $("#mainModal").fadeOut(500);
                loadCurrentUser();
            });

            $("#mainModal").fadeIn(200);
        });
        
        $("#deletePost" + post.postID).click(deletePost);
        
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
                                <textarea id="newProfileCaption" class="styledTextArea" placeholder="Add a Caption">
                                    
                                </textarea>
                            </div><br><br>

                            <div class="controlContainer">
                                <input type="button" id="createCaptionBtn" class="styledBtn" data-profileid="${currentUser.profileID}" value="Add Caption">
                            </div>
                        </div>
                    </div>`
            );
            
            $("#createCaptionBtn").click(createCaption);

            $("#modalCloseButton").click(() => {
                $("#mainModal").fadeOut(500);
            });

            $("#mainModal").fadeIn(200);
        });
}

function deletePost() {
    ajaxCall("EditPost",
                {"postID" : $(this).attr("data-postid")},
                "GET", (result) => {
                    currentUser = result;
                    $("#mainModal").fadeOut(500);
                    displayProfile();
    });
}

function editPost() {
    console.log($(this).attr("data-postid"));
    ajaxCall("EditPost",
                {"editedPostID" : $(this).attr("data-postid"),
                "editedPostCaption" : $("#editPostCaption").val(),
                "editedPostDescription" : $("#editPostDescription").val()},
                "Post", (result) => {
                    currentUser = result;
                    $("#mainModal").fadeOut(500);
                    displayProfile();
            });
}

function createCaption() {
    ajaxCall("CaptionManager",
                {"newCaption" : $("#newProfileCaption").val(),
                "profileID" : $(this).attr("data-profileid")},
                "Post", (result) => {
                    $("#mainModal").fadeOut(500);
                    loadCurrentUser();
                }
            );
}

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

var ajaxCall = (url, data, type, callback) => {
    $.ajax({
        type: type,
        url: url,
        data: data,
        dataType: "JSON",
        success: callback,
        error: function (jqXHR, ex) {
            console.log(jqXHR);
        }
    });
};