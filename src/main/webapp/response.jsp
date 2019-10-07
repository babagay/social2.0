<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="preload" href="static/style.css" as="fetch">
        <title class="t1">Hello Page</title>
    </head>
    <body>
        <h2>Hello, ${user}!</h2>
        <c:if test="${invalid}">
            Data invalid: ${invalid}
        </c:if>
    </body>
</html>