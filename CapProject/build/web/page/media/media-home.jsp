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
            <div class="searchBar-wrapper">
                <input type="text" id="txtSearchObject" class="searchBar" placeholder="Search For Posts"/>
            
                <input type="button" id="searchObjectBtn" class="styledBtn" value="Search"/>
                
                <input type="button" id="clearAllBtn" class="styledBtn hidden" value="Clear All"/>
            </div>
            

            <input type="button" id="newPostBtn" class="styledBtn" value="New Post" />
        </div>
        <div class="media-home-container">
            <div class="allPostContainer" id="allPostContainer">
                
            </div>
            
            <div class="trending">
                <div name="topPosts" class="topPosts" id="trendingPosts">
                    
                </div>
            
                <div name="topProfiles" class="topProfiles" id="trendingProfiles">
                    
                </div>
            </div>
            
        </div>
        

        <div id="mainModal" class="modalBackground">
        </div>
    </div>
</main>
<jsp:include page="/page/link/footer.jsp"/>
