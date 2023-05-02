<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml" xmlns:sec="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <base href="/">
        <link rel="stylesheet"  type="text/css" href="css/styles.css">
    </base>

</head>
<body>

<div class="topnav "id="myNav">
    <div class="topnav-centered">
        <a><h1>Bookstore 23</h1></a>
    </div>
    <a th:href="@{/}">Home</a>
    <a th:href="@{/listBooks}">Books</a>
    <a sec:authorize="hasRole('ADMIN')" th:href="@{/listUsers}">Users</a>
    <a sec:authorize="hasRole('ADMIN')" th:href="@{/listOrders}">Orders</a>
    <div class="topnav-right">
        <a sec:authorize="!isAuthenticated()" th:href="@{/register}">Register</a>
        <a sec:authorize="!isAuthenticated()" th:href="@{/login}">Log in</a>
        <a sec:authorize="isAuthenticated()" th:href="@{/logout}">Log out</a>
        <button class="darkModeButton" id="darkModeButton">Dark</button>
    </div>
</div>


    <div class="center" sec:authorize="isAuthenticated()">
        <h1>Welcome  <span sec:authentication="name"></span></h1>
    </div>


<div class="gallery">
  <div style="background-image: url('images/image-1.jpg');"></div>
  <div style="background-image: url('images/image-2.jpg');"></div>
  <div style="background-image: url('images/image-3.jpg');"></div>
  <div style="background-image: url('images/image-4.jpg');"></div>
  <div style="background-image: url('images/image-5.jpg');"></div>
</div>

<script src="scripts/scripts.js"></script>
</body>
</html>