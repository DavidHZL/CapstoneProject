"use strict";

$(document).ready(() => {
    
    console.log(altUserProfile);
    
    loadAltProfile();
});

function loadAltProfile() {
    $("#profileContent").empty();
    
    var profileContentHTML =
            `
            <h1 class="profileHeader">
                <div class="profileName">${altUserProfile.email}</div>
                <div class="profileEmail">${altUserProfile.profileName}</div>
                <div class="followerCount">${altUserProfile.followerCount} Followers</div>
            </h1>

            `;
    
    if (typeof altUserProfile.profileCaption !== "undefined") {
        profileContentHTML +=
                `
            <div class="profileCaption">
                    <p >${altUserProfile.profileCaption}</p>
            </div>
            `;
    } else {
        profileContentHTML +=
                `
            <div class="profileCaption">
                    
            </div>
            `;
    }
    
    profileContentHTML += `<div class="allPostContainer">`;
    
    altUserProfile.posts.forEach((post) => {
        profileContentHTML +=
            `
            <div class="card-post">
                <div class="post-info">
                    <div class="post-main-info">
                        <h3 class="postCaption">${post.caption}</h3>
                        <p class="description-post">${post.description}</p>
                    </div>
                    <div class="post-controls">
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

}