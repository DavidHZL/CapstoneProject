<%-- 
    Document   : header-main
    Created on : Mar 4, 2022, 7:29:23 PM
    Author     : Dadvid
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asset Store</title>
        <link rel="stylesheet" href="page/link/style.css" />
    </head>
    <body>
        <nav> 
            <div class="navbar">
                <ul class="navList">
                    <div class="navSection">
                        <c:if test="${currentUser != null}">
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="/page/profile/profile.jsp">
                                    <input type="submit" value="Profile" class="navButton">
                                </form>
                            </li>
                        </c:if>
                    </div>
                    <div class="navSection">
                        <c:if test="${currentUser == null}">
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="/index.jsp">
                                    <input type="submit" value="Home" class="navButton">
                                </form>
                            </li>
                        </c:if>
                        <c:if test="${currentUser != null}">
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="/page/media/media-home.jsp">
                                    <input type="submit" value="Home" class="navButton">
                                </form>
                            </li>
                            <c:if test="${currentUser.accountType eq 'admin'}">
                                <li class="navListItem">
                                    <form action="Navigation" method="POST">
                                        <input type="hidden" name="url" value="/page/admin/overview.jsp">
                                        <input type="submit" value="Overview" class="navButton">
                                    </form>
                                </li>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="navSection">
                        <c:if test="${currentUser == null}">
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="/page/auth/login.jsp">
                                    <input type="submit" value="Login" class="navButton">
                                </form>
                            </li>
                        </c:if>
                        <c:if test="${currentUser != null}">
                            <li class="navListItem">
                                <form action="Authorize" method="GET">
                                    <input type="hidden" name="url" value="/page/auth/login.jsp">
                                    <input type="submit" value="Logout" class="navButton">
                                </form>
                            </li>
                        </c:if>
                    </div>


                </ul>
            </div>
        </nav>