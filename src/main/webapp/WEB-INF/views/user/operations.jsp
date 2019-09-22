<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ua.model.CurrencyOperation" %>
<%@ page import="ua.model.CurrencyRate" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="groupCurrencyOperation" scope="request" type="java.util.Map<java.time.LocalDate,
java.util.Map<java.lang.String, java.util.Map<java.lang.String, ua.model.TotalAmountBuyAndSale>>>"/>
<%--<jsp:useBean id="operationByDate" type="java.util.Map<java.lang.String, java.util.Map<java.lang.String,ua.model.TotalAmountBuyAndSale>>"/>--%>
<page:main>
    <jsp:body>

        <div class="accrual-index">
            <div class="col-lg-12">
                <div class="block block-shadow">
                    <div class="panel-body">

                        <h2 class="left text-shadow"><spring:message code="сгккутсн_reports"/></h2>

                        <div class="form-inline form-group text-center">
                            <p style="font-size: 16px;">
                                <span><spring:message code="sort_by"/></span> -
                                <select onchange="location = this.value;" >
                                    <option ${groupBy == 'day' ? 'selected' : ''} value="?groupBy=day"><spring:message code="day"/></option>
                                    <option ${groupBy == 'month' ? 'selected' : ''} value="?groupBy=month"><spring:message code="month"/></option>
                                    <option ${groupBy == 'year' ? 'selected' : ''} value="?groupBy=year"><spring:message code="year"/></option>
                                </select>
                            </p>
                        </div>

                        <div id="list-accrual" data-pjax-container data-pjax-push-state data-pjax-timeout="1000">
                            <table class="table table-condensed table-bordered table-box-shadow table-default">
                                <thead>
                                <tr>
                                    <th><spring:message code="date_transaction"/></th>
                                    <th><spring:message code="currency_unit"/></th>
                                    <th><spring:message code="currency_sell_in_amount"/> (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                    <th><spring:message code="currency_buy_in_amount"/> (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                </tr>
                                </thead>
                                <c:if test="${empty groupCurrencyOperation}">
                                    <tbody>
                                    <tr>
                                        <td colspan="4"><spring:message code="operation_log_empty"/></td>
                                    </tr>
                                    </tbody>
                                </c:if>
                                <c:if test="${not empty groupCurrencyOperation}">
                                    <c:forEach items="${groupCurrencyOperation}" var="operationByDate" varStatus="loopEven">
                                        <c:set var="className" value="${loopEven.index % 2 == 0 ? 'even' : 'odd'}"/>
                                        <tbody>
                                        <c:forEach items="${operationByDate.value}" var="operationByCurency" varStatus="loop">
                                            <c:set var="currencySell" value="${empty operationByCurency.value.get(CurrencyOperation.TYPE_SELL) ? '0': operationByCurency.value.get(CurrencyOperation.TYPE_SELL)}"/>
                                            <c:set var="currencyBuy" value="${empty operationByCurency.value.get(CurrencyOperation.TYPE_BUY) ? '0': operationByCurency.value.get(CurrencyOperation.TYPE_BUY)}"/>
                                            <c:set var="className" value="${loopEven.index % 2 == 0 ? 'even' : 'odd'}"/>
                                            <c:if test="${loop.first}">
                                                <tr class="${className}">
                                                    <td rowspan="${operationByDate.value.size()}">${operationByDate.key}</td>
                                                    <td>${operationByCurency.key}</td>
                                                    <td>${currencySell}</td>
                                                    <td>${currencyBuy}</td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${not loop.first}">
                                                <tr class="${className}">
                                                    <td>${operationByCurency.key}</td>
                                                    <td>${currencySell}</td>
                                                    <td>${currencyBuy}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </c:forEach>
                                </c:if>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</page:main>