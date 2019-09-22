<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<page:main>
    <jsp:body>

        <div class="col-lg-12">
            <div class="site-error">
                <div class="alert alert-danger">
                    <h1><spring:message code="you_do_not_have_access_to_this_page"/></h1>
                </div>
            </div>
        </div>

    </jsp:body>
</page:main>
