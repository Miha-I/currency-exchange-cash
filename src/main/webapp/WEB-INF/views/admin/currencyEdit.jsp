<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ua.model.CurrencyRate" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:useBean id="currencyRateForm" scope="request" type="ua.form.CurrencyRateForm"/>
<page:main>
    <jsp:body>

        <div class="accrual-index">
            <div class="col-lg-12">
                <div class="block block-shadow">
                    <div class="panel-body">

                        <h2 class="left text-shadow"><spring:message code="currency_rate_editor"/></h2>
                        <a href="/currency/edit/load-nbu" class="btn btn-success right btn-shadow"><spring:message code="download_NBU_course"/></a>
                        <a href="/currency/edit/load-other" class="btn btn-success right btn-shadow"><spring:message code="download_PB_course"/></a>

                        <div id="list-currency-rate" data-pjax-container data-pjax-push-state data-pjax-timeout="1000">
                            <form:form class="form-horizontal" action="/currency/save" method="post" role="form" modelAttribute="currencyRateForm">
                                <table class="table table-striped table-bordered table-box-shadow">
                                    <thead>
                                    <tr>
                                        <th><spring:message code="currency_unit"/></th>
                                        <th><spring:message code="currency_sell"/>  (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                        <th><spring:message code="currency_buy"/>  (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${empty currencyRateForm}">
                                        <tr>
                                            <td colspan="3"><spring:message code="currency_rates_absent"/></td>
                                        </tr>
                                    </c:if>

                                    <c:if test="${not empty currencyRateForm}">
                                        <c:forEach items="${currencyRateForm.currencyRateList}" var="currencyRate" varStatus="status">
                                            <tr data-key=="${currencyRate.currencyCode}">
                                                <input type="hidden" name="currencyRateList[${status.index}].currencyCode" value="${currencyRate.currencyCode}"/>
                                                <td>${currencyRate.currencyCode}</td>
                                                <td>
                                                    <div class="col-md-offset-4 col-md-3">
                                                        <input type="number"
                                                               min="0"
                                                               pattern="[^\d{1,10}([,\.]{1}\d{1,6})?$+"
                                                               step="0.000001"
                                                               class="form-control"
                                                               name="currencyRateList[${status.index}].sellRate"
                                                               value="${currencyRate.sellRate}"/>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="col-md-offset-4 col-md-3">
                                                        <input type="number"
                                                               min="0"
                                                               pattern="^\d{1,10}([,\.]{1}\d{1,6})?$"
                                                               step="0.000001"
                                                               class="form-control"
                                                               name="currencyRateList[${status.index}].buyRate"
                                                               value="${currencyRate.buyRate}"/>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    </tbody>
                                </table>
                                <div class="panel">
                                    <button type="submit" class="btn btn-primary"><spring:message code="button.save"/></button>
                                </div>
                            </form:form>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</page:main>
