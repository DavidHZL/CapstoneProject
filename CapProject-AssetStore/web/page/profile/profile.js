"use strict";

var currentUser;

$(document).ready(() => {
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
    
    if (typeof currentUser.profileCaption !== "undefined"){
        profileContentHTML += 
            `
            <div class="profileCaption">
                    <p class="profileCaption">${currentUser.profileCaption}</p>
            </div>
            `;
    } else {
        profileContentHTML += 
            `
            <div class="profileCaption">
                    <p class="profileCaption">Click here to add a caption!</p>
                    <button class="styledBtn" id="addCaptionButton">Add Caption</button>
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
    
}
