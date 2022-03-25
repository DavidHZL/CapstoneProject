<%-- 
    Document   : register
    Created on : Mar 4, 2022, 2:36:20 PM
    Author     : Dadvid
--%>

<jsp:include page="/page/link/header-main.jsp"/>
<main>
    <h1 class="register-title">Register Here</h1>

    <form action="public" method="post">
        <input type="hidden" name="action" value="register">

        <label for="email">Email: </label>
        <input type="email" name="email">

        <label for="username">Username: </label>
        <input type="text" name="username">

        <label for="password">Password: </label>
        <input type="text" name="password">

        <label for="passwordCheck">Re-enter Password: </label>
        <input type="text" name="passwordCheck">
        <br>
        <label for="accountType">Account Type: </label>
        <input type="radio" name="accountType" value="user" checked>
        <label for="user">User</label> 
        <input type="radio" name="accountType" value="admin" >
        <label for="admin">Admin</label> 
        <br>
        <input type="submit" value="Register" class="styledBtn">
    </form>

    <h1>${errorList}</h1>
</main>
<jsp:include page="/page/link/footer.jsp"/>
