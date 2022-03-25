<%-- 
    Document   : login
    Created on : Mar 4, 2022, 2:36:12 PM
    Author     : Dadvid
--%>

<jsp:include page="/page/link/header-main.jsp"/>
<main>
    <div class="mainContent">
        <h1 class="title">Login page</h1>

        <form action="public" method="post">
            <input type="hidden" name="action" value="login">

            <label for="username">Username: </label>
            <input type="text" name="username">

            <label for="password">Password: </label>
            <input type="password" name="password">

            <input type="submit" value="Login" class="styledBtn">
        </form>

        <form action="public" method="post" >
            <input type="hidden" name="action" value="toRegister">
            <input type="submit" value="Register Here" class="styledBtn">
        </form>
    </div>


</main>
<jsp:include page="/page/link/footer.jsp"/>
