<%@tag description="Template Site tag" pageEncoding="UTF-8"%>
<%@taglib prefix="headerTamplate" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%--<meta name="_csrf" content="${_csrf.token}"/>--%>
    <%--<!-- default header name is X-CSRF-TOKEN -->--%>
    <%--<meta name="_csrf_header" content="${_csrf.headerName}"/>--%>

    <link rel="shortcut icon" type="image/png" href="/resources/home.png"/>

    <script type="text/javascript" src="/resources/jquery-3.4.1/jquery.min.js/"></script>
    <script type="text/javascript" src="/resources/bootstrap-3.3.7/js/bootstrap.js"></script>
    <%--<script type="text/javascript" src="/resources/sweetalert/sweetalert.min.js"></script>--%>

    <%--<script type="text/javascript" src="/resources/jquery-ui-1.12.1/jquery-ui.js"></script>--%>

    <%--<link rel="stylesheet" type="text/css" href="/resources/sweetalert/sweetalert.css">--%>
    <link rel="stylesheet" type="text/css" href="/resources/bootstrap-3.3.7/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/jquery-ui-1.12.1/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css"/>

</head>
<body>
<div class="wrap">
    <headerTamplate:header/>
    <article class="article">
        <jsp:doBody/>
    </article>
</div>
</div>
</body>
</html>