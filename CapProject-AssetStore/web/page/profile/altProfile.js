"use strict";

$(document).ready(() => {
    
    console.log(altUserProfile);
    
    loadAltProfile();
    followingStatus(altUserProfile.profileID);
});

function loadAltProfile() {
    $("#profileContent").empty();
    
    var profileContentHTML =
            `
            <h1 class="profileHeader">
                <div class="profileName">${altUserProfile.email}</div>
                <div class="profileEmail">${altUserProfile.profileName}</div>
                
                <div class="followerCount">
                    <div class="followBtn" id="followBtn" data-followercount="${altUserProfile.followerCount}" data-profileID="${altUserProfile.profileID}">Follow ${altUserProfile.profileName}</div>
                    <p id="followerNum">${altUserProfile.followerCount} Followers</p>
                </div>
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
                    <p>No Caption Available</p>
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
    
    $("#followBtn").click(followProfile);

}

function followProfile() {
    $("#followBtn").attr("disable", true);
    
    ajaxCall("Follow", 
        {"followingProfileID": $(this).attr("data-profileid"),
        "followerCount" : $(this).attr("data-followercount")},
        "POST", (result) => {
            $("#followBtn").hide();
            $("#followerNum").html(`${result} followers`);
        }
    );
}

function followingStatus(profileID) {
    ajaxCall("Follow",
        {"followingProfileID" : profileID},
        "GET", (result) => {
            var status = result;
            if (status !== false) {
                $("#followBtn").hide();
            }
        }
    );
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