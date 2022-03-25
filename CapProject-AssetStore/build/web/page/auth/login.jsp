<%-- 
    Document   : login
    Created on : Mar 4, 2022, 2:36:12 PM
    Author     : Dadvid
--%>

<jsp:include page="/page/link/header-main.jsp"/>
<main>
    <div class="mainContent">
        <h1 class="title">Login page</h1>

        <form action="Authorize" method="POST">
            <label for="username">Username: </label>
            <input type="text" name="username">

            <label for="password">Password: </label>
            <input type="password" name="password">

            <input type="submit" value="Login" class="styledBtn">
        </form>

        <form action="Navigation" method="POST" >
            <input type="hidden" name="url" value="/page/auth/register.jsp">
            <input type="submit" value="Register Here" class="styledBtn">
        </form>
        
        <h1>${errorList}</h1>
    </div>


</main>
<jsp:include page="/page/link/footer.jsp"/>
