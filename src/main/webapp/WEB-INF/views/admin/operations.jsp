<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ua.model.CurrencyRate" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="currencyOperationList" scope="request" type="java.util.List<ua.model.CurrencyOperation>"/>
<page:main>
    <jsp:body>

        <div class="accrual-index">
            <div class="col-lg-12">
                <div class="block block-shadow">
                    <div class="panel-body">

                        <h2 class="left text-shadow"><spring:message code="сгккутсн_operation_log"/></h2>

                        <div id="list-accrual" data-pjax-container data-pjax-push-state data-pjax-timeout="1000">
                            <table class="table table-striped table-bordered table-box-shadow">
                                <thead>
                                <tr>
                                    <th><spring:message code="date_transaction"/></th>
                                    <th><spring:message code="currency_unit"/></th>
                                    <th><spring:message code="transaction_type"/></th>
                                    <th><spring:message code="amount"/></th>
                                    <th><spring:message code="total_amount"/> (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                    <th><spring:message code="username"/></th>
                                    <th><spring:message code="actions"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${empty currencyOperationList}">
                                    <tr>
                                        <td colspan="7"><spring:message code="operation_log_empty"/></td>
                                    </tr>
                                </c:if>

                                <c:if test="${not empty currencyOperationList}">
                                    <c:forEach items="${currencyOperationList}" var="currencyOperation">
                                        <tr data-key=="${currencyOperation.id}">
                                            <td>${currencyOperation.dateOperation}</td>
                                            <td>${currencyOperation.currencyCode}</td>
                                            <td><spring:message code="${currencyOperation.typeOperation}"/></td>
                                            <td>${currencyOperation.amount}</td>
                                            <td>${currencyOperation.totalAmount}</td>
                                            <td>${currencyOperation.username}</td>
                                            <td>
                                                <a href="/operation/delete/${currencyOperation.id}"
                                                   data-method="post"
                                                   class="delete-order"
                                                   onclick="return confirm('<spring:message code="delete_order"/>?')">
                                                    <i rel="tooltip" title="" class="glyphicon glyphicon-trash" data-original-title="<spring:message code="delete_operation"/>"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</page:main>