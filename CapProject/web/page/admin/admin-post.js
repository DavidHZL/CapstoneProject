"use strict";
var postList;

$(document).ready(() => {

    retrievePosts();
    
});

function displayPosts(postList) {
    $("#allPostContainer").html("");

    postList.forEach((element) => {
        $("#allPostContainer").append(
           `<div class="card-post">
                <div class="post-info">
                    <div class="post-main-info">
                        <h3 class="postCaption">${element.caption}</h3>
                        <p class="description-post">${element.description}</p>
                    </div>
                    <form action="AltProfileManager" method="POST" >
                        <input type="hidden" name="creatorID" value="${element.creatorID}" />
        
                        <div class="profileReference">
                            <label for="altProfileButton">Created By:</label>
                            <input type="submit" name="altProfileButton" class="altProfileButton" value="${element.creatorUserName}"/>
                        </div>
                    </form>
                    <div class="post-controls">
                        <span id="deletePost${element.postID}" data-postid="${element.postID}"><img src="resources/deleteIcon.png" class="deleteIcon" alt="deletePostIcon"></span>
                        <p id="likesOnPost${element.postID}" class="postLikes">${element.likes}</p>
                    </div>
                </div>
                <div class="post-img-container">
                    <image src="resources/${element.imageName}" class="img-post" alt="A post from a user"/>
                </div>
            </div>`);
        
        $("#deletePost" + element.postID).click(deletePost);
    });
}

function deletePost() {
    ajaxCall("EditPost",
                {"postID" : $(this).attr("data-postid")},
                "GET", (result) => {
                    retrievePosts();
    });
}

function retrievePosts() {
    postList = "";
    $.ajax({
        type: "GET",
        url: "FileUpload",
        dataType: "JSON",
        success: (result) => {
            postList = result;
            console.log(postList);
            
            displayPosts(postList);
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