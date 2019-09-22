<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ua.model.CurrencyRate" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:useBean id="currencyRateList" scope="request" type="java.util.List<ua.model.CurrencyRate>"/>
<jsp:useBean id="currencyRateOtherLis" scope="request" type="java.util.List<ua.model.CurrencyRate>"/>
<security:authorize access="hasRole('ROLE_USER')" var="isUSer"/>
<c:set var="BASE_CCY" value="<%=CurrencyRate.BASE_CCY%>" />
<page:main>
    <jsp:body>

        <div class="accrual-index">
            <div class="col-lg-12">
                <div class="block block-shadow">
                    <div class="panel-body">

                        <h2 class="left text-shadow"><spring:message code="currency_rates"/></h2>

                        <div id="list-accrual" data-pjax-container data-pjax-push-state data-pjax-timeout="1000">
                            <table class="table table-striped table-bordered table-box-shadow">
                                <thead>
                                <tr>
                                    <th><spring:message code="currency_unit"/></th>
                                    <th><spring:message code="currency_sell"/>  (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                    <th><spring:message code="currency_buy"/>  (<spring:message code="${CurrencyRate.BASE_CCY}_short"/>)</th>
                                    <c:if test="${isUSer}">
                                        <th colspan="2"><spring:message code="actions"/></th>
                                    </c:if>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${empty currencyRateList}">
                                    <tr>
                                        <td colspan="3"><spring:message code="currency_rates_not_available"/></td>
                                    </tr>
                                </c:if>

                                <c:if test="${not empty currencyRateList}">
                                    <c:forEach items="${currencyRateList}" var="currencyRate">
                                        <tr data-key=="${currencyRate.currencyCode}">
                                            <td>${currencyRate.currencyCode}</td>
                                            <td>${currencyRate.sellRate}</td>
                                            <td>${currencyRate.buyRate}</td>
                                            <c:if test="${isUSer}">
                                                <td>
                                                    <a data-key="/currency/buy/${currencyRate.currencyCode}"
                                                       data-sell-rate="${currencyRate.sellRate}"
                                                       class="currency-buy"
                                                       data-toggle="modal"
                                                       data-target="#modal-operation">
                                                        <i rel="tooltip" title="" class="glyphicon glyphicon-plus" data-original-title="<spring:message code="buy_action"/>"></i>
                                                    </a>
                                                </td>
                                                <td>
                                                    <a data-key="/currency/sell/${currencyRate.currencyCode}"
                                                       data-buy-rate="${currencyRate.buyRate}"
                                                       class="currency-sell"
                                                       data-toggle="modal"
                                                       data-target="#modal-operation">
                                                        <i rel="tooltip" title="" class="glyphicon glyphicon-minus" data-original-title="<spring:message code="sell_action"/>"></i>
                                                    </a>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${not empty currencyRateOtherLis}">
                                    <tr>
                                        <td colspan="${isUSer ? 5 : 3}"><spring:message code="NBU_exchange_rate"/></td>
                                    </tr>
                                    <c:forEach items="${currencyRateOtherLis}" var="currencyRate">
                                        <tr data-key=="${currencyRate.currencyCode}">
                                            <td>${currencyRate.currencyCode}</td>
                                            <td>${currencyRate.sellRate}</td>
                                            <td>${currencyRate.buyRate}</td>
                                            <c:if test="${isUSer}">
                                                <td></td>
                                                <td></td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                        <!-- Modal Form -->
                        <div id="modal-operation" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="loginLabel">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form:form id="form-operation" class="form-horizontal" action="" method="post" role="form">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">×</button>
                                            <h4 id="title" class="modal-title"></h4>
                                        </div>

                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group field-currency-amount required">
                                                        <label class="col-md-6 control-label" for="currency-amount"><spring:message code="amount"/></label>
                                                        <div class="col-md-6">
                                                            <input type="number" pattern="^\d*(\.\d{0,2})?$" id="currency-amount" name="currency-amount" class="form-control" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%--<div class="col-md-12">--%>
                                                    <%--<div class="form-group field-compensation-description">--%>
                                                        <%--<label class="col-sm-3 control-label">description</label>--%>
                                                        <%--<div class="col-sm-9"></div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            </div>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary"><spring:message code="button.add"/></button>
                                            <button type="submit" class="btn btn-default" data-dismiss="modal"><spring:message code="button.cancel"/></button>
                                        </div>

                                    </form:form>
                                </div>
                            </div>
                        </div>
                        <script type="text/javascript">
                            $('.currency-buy').on('click', function() {
                                var form = $('#form-operation');
                                form.attr('action', $(this).data('key'));
                                form.find('#title').text('<spring:message code="operation_buy"/>');
                            });
                            $('.currency-sell').on('click', function() {
                                var form = $('#form-operation');
                                form.attr('action', $(this).data('key'));
                                form.find('#title').text('<spring:message code="operation_sell"/>');
                            });
                            // ajax
                            $('#modal-operation').on('submit', '#form-operation', function (event) {
                                event.preventDefault();
                                var form = $(this);
                                var formData = form.serialize();
                                $.ajax({
                                    url: form.attr("action"),
                                    type: "POST",
                                    data: formData,
                                    dataType: 'json',
                                    success: function (data) {
                                        if (data && data.success === true) {
                                            $("#modal-operation").modal("toggle");
                                            form.trigger('reset');
                                            // location.reload();
                                        } else {
                                            alert("error");
                                        }
                                    },
                                    error: function () {
                                        alert("invalid request");
                                    }
                                });
                                return false;
                            });
                            $(function () {
                                //var tol = $('[rel=tooltip]');
                                //tol.tooltip({ trigger: "hover", html:true });
                                // $('[rel=tooltip]').tooltip({ trigger: "hover" });
                                $("*[rel=tooltip]").tooltip();
                            });
                        </script>
                        <!-- /.Modal Form -->

                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</page:main>
