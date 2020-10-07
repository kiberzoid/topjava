<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="POST" action='meals' name="editMeal">
    <input hidden type="text" readonly="readonly" name="id"
           value="${meal.id}" /> <br/>
    DateTime : <input
        type="datetime-local" name="dateTime"
        value="${meal.dateTime}" /> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}" /> <br/>
    Calories : <input
        type="number" min="1" name="calories"
        value="${meal.calories}" /> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>