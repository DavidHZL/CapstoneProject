"use strict";

var tagList = [];
var postList;

$(document).ready(() => {
    $("#newPostBtn").click(showForm);

    $(window).click(function (e) {
        if (e.target.id === "mainModal") {
            $("#mainModal").fadeOut(100);
        }
    });
    
    retrievePosts();
});

function showForm() {
    $("#mainModal").html(
            `<div id="modalBox" class="modalContent">
                <span id="modalCloseButton" class="closeButton">&times;</span>
                <div id="modalContent">
                    <h1 class="modalTitle">Create A Post</h1>
            
                    <div class="controlContainer">
                        <label for="newPostCaption">Caption: </label>
                        <input type="text" id="newPostCaption" name="newPostCaption">
                    </div><br><br>

                    <div class="controlContainer">
                        <label for="newPostDescription">Description: </label>
                        <input type="text" id="newPostDescription" name="newPostDescription">
                    </div><br><br>

                    <div class="controlContainer">
                        <div>
                            <label for="newPostTag">Tags: </label>
                            <input type="text" id="newPostTag" name="newPostTag">
                            <ul class="tagContainer" id="tagContainer">

                            </ul>
                        </div>
                        <div>
                            <input type="button" id="addTag" class="styledBtn" value="Add Tag"><br>
                            
                        </div>
                    </div><br><br>

                    <form id="newPost" action="FileUpload" method="POST" enctype="multipart/form-data">
                        <div class="controlContainer">
                            <label for="newAssetLink">Asset: </label>
                            <input type="file" id="newFile" name="newFile">
                        </div>
                        <div class="controlContainer">
                            <input type="submit" id="submitPostBtn" class="styledBtn" value="Create Post">
                        </div>
                    </form>
                </div>
            </div>`
            );
    $("#addTag").click(addTag);

    $("#submitPostBtn").click(function (event) {
        event.preventDefault();
        var form = $("#newPost")[0];

        var data = new FormData(form);

        data.append("newCaption", $("#newPostCaption").val());
        data.append("newDescription", $("#newPostDescription").val());

        for (var i = 0; i < tagList.length; i++) {
            data.append("tag[]", tagList[i]);
        }

        $.ajax({
            type:"POST",
            enctype: "multipart/form-data",
            url:"FileUpload",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: (result) => {
                $("#mainModal").fadeOut(500);
                retrievePosts();
            },
            error: function (e) {
                $("#result").text(e.responseText);
                console.log("ERROR : ", e);
            }
        });
    });

    $("#modalCloseButton").click(() => {
        $("#mainModal").fadeOut(500);
    });

    $("#mainModal").fadeIn(200);
}

function displayPosts(postList) {
    $("#allPostContainer").html("");

    postList.forEach(element => (
        $("#allPostContainer").append(
           `<div class="card-post">
                <div class="post-info">
                    <div class="post-main-info">
                        <h3 class="postCaption">${element.caption}</h3>
                        <p class="description-post">${element.description}</p>
                    </div>
                    <div class="tags-post">
                        <p class="tag">tag</p>
                        <p class="tag">tag</p>
                    </div>
                    <div class="post-controls">
                        <p>Like</p>
                    </div>
                </div>
                <div class="post-img-container">
                    <image src="resources/${element.imageName}" class="img-post" alt="A post from a user"/>
                </div>
            </div>`)
    ));
}

function addTag() {
    var tagContainer = document.querySelector("#tagContainer");
    var newTag = document.querySelector("#newPostTag").value;

    tagList.push(newTag);

    var li = document.createElement("li");
    li.classList.add("newTag");
    li.append(newTag);

    tagContainer.append(li);

    document.querySelector("#newPostTag").value = "";
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

