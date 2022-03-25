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

                    <form method="post" enctype="multipart/form-data" id="newPost">

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

    $("#modalCloseButton").click(() => {
        $("#mainModal").fadeOut(500);
    });

    $("#mainModal").fadeIn(200);
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
