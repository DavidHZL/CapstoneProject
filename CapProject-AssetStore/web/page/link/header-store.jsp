<%-- 
    Document   : header
    Created on : Feb 28, 2022, 11:46:17 AM
    Author     : Dadvid
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Asset Store</title>
        <link rel="stylesheet" type="text/css" href="page/link/style.css">
    </head>
    <body>
        <nav> 
            <div class="navbar">
                <ul class="navList">
                    <div class="navSection">
                        <c:if test="${currentUser != null}">
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="toCart">
                                    <input type="submit" value="Cart" class="navButton">
                                </form>
                            </li>
                        </c:if>
                    </div>
                    <div class="navSection">
                        <c:if test="${currentUser != null}">
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="toStoreHome">
                                    <input type="submit" value="Store Home" class="navButton">
                                </form>
                            </li>
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="toStoreBrowse">
                                    <input type="submit" value="Browse" class="navButton">
                                </form>
                            </li>
                            <li class="navListItem">
                                <form action="Navigation" method="POST">
                                    <input type="hidden" name="url" value="/page/media/media-home.jsp">
                                    <input type="submit" value="Social" class="navButton">
                                </form>
                            </li>
                        </c:if>
                    </div>
                    <div class="navSection">
                        <c:if test="${currentUser != null}">
                            <li class="navListItem">
                                <form action="Navigation" method="post">
                                    <input type="hidden" name="action" value="logout">
                                    <input type="submit" value="Logout" class="navButton">
                                </form>
                            </li>
                        </c:if>
                    </div>


                </ul>
            </div>
        </nav>
