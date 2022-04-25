<%-- 
    Document   : register
    Created on : Mar 4, 2022, 2:36:20 PM
    Author     : Dadvid
--%>

<jsp:include page="/page/link/header-main.jsp"/>
<main>
    <div class="mainContent">
        <h1 class="title">Register Here</h1>

        <form action="Register" method="Post" class="registerFrm">
            <label for="email">Email: </label>
            <input type="email" name="email" class="styledTxtInput"><br><br>

            <label for="username">Username: </label>
            <input type="text" name="username" class="styledTxtInput"><br><br>

            <label for="password">Password: </label>
            <input type="password" name="password" class="styledTxtInput"><br><br>

            <label for="passwordCheck">Re-enter Password: </label>
            <input type="password" name="passwordCheck" class="styledTxtInput"><br><br>
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
    </div>
</main>
<jsp:include page="/page/link/footer.jsp"/>
