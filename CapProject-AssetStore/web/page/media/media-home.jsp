<%-- 
    Document   : media-home
    Created on : Mar 24, 2022, 7:07:35 PM
    Author     : Dadvid
--%>

<jsp:include page="/page/link/header-main.jsp"/>
<main>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/CapProject-AssetStore/page/media/post.js"></script>

    <div class="mainContent">
        <div class="title">
            <h1>Browse All Media Here!</h1>

            <input type="button" id="newPostBtn" class="styledBtn" value="New Post" />
        </div>

        <div class="allPostContainer" id="allPostContainer">
        </div>

        <div id="mainModal" class="modalBackground">
        </div>
    </div>
</main>
<jsp:include page="/page/link/footer.jsp"/>
