<!DOCTYPE html>
<%@ tag description="Template Site tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="localeCode" value="${pageContext.response.locale}" />
<%--<security:authorize access="hasRole('ROLE_USER')" var="isUSer"/>--%>
<security:authorize access="hasRole(T(ua.model.User).ROLE_USER)" var="isUSer"/>
<security:authorize access="hasRole(T(ua.model.User).ROLE_ADMIN)" var="isAdmin"/>
<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_USER')" var="isAuthenticated"/>
<!-- Menu -->
<header class="header">
    <div class="col-lg-12">

        <div class="text-center name-company">
            <h1><spring:message code="exchange_currency"/></h1>
        </div>

        <div class="header-menu">
            <nav>
                <div class="nav-wrapper block block-shadow">

                    <div class="left-menu">
                        <ul id="nav-menu-header-left" class="nav-menu">
                            <li class="u-home unselectable">
                                <a href="/" class="menu-tab">
                                    <span class="icon entypo-home scnd-font-color"></span>
                                    <span><spring:message code="navMenu.home"/></span>
                                </a>
                            </li>
                            <c:if test="${isUSer}">
                                <li class="u-registers unselectable">
                                    <a href="/operations" class="menu-tab">
                                        <span class="icon fontawesome-list-alt"></span>
                                        <span><spring:message code="navMenu.reports"/></span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${isAdmin}">
                                <li class="u-service-info drop-down-list unselectable">
                                    <a class="menu-tab">
                                        <span class="icon fontawesome-wrench scnd-font-color"></span>
                                        <span><spring:message code="navMenu.registers"/></span>
                                    </a>
                                    <ul>
                                        <li><a href="/currency/edit"><spring:message code="navMenu.currency_rate_editor"/></a></li>
                                        <li><a href="/operations/edit"><spring:message code="navMenu.operation_log"/></a></li>
                                    </ul>
                                </li>
                            </c:if>
                        </ul>
                    </div>

                    <div class="right-menu">
                        <ul id="nav-menu-header-right" class="nav-menu">
                            <c:if test="${localeCode=='ua'}">
                                <li class="is-active border-left-line">
                                    <abbr class="menu-tab-right" title="Українська">UA</abbr>
                                </li>
                                <li>
                                    <a href="${context}?lang=ru" class="menu-tab-right">
                                        <abbr title="Русский">RU</abbr>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${localeCode=='ru'}">
                                <li class="border-left-line">
                                    <a href="${context}?lang=ua" class="menu-tab-right">
                                        <abbr title="Українська">UA</abbr>
                                    </a>
                                </li>
                                <li class="is-active"><abbr class="menu-tab-right" title="Русский">RU</abbr></li>
                            </c:if>

                            <c:if test="${isAuthenticated}">
                                <li class="border-left-line">
                                    <a href class="menu-tab-right"><span class="user-profile-icon"></span></a>
                                </li>
                                <li class="u-logout border-left-line unselectable">
                                    <a href class="menu-tab-right">
                                        <form:form method="post" action="/logout">
                                            <button class="btn-logout" type="submit">
                                                <span class="icon entypo-logout scnd-font-color"></span>
                                                <span><spring:message code="navMenu.logout"/></span>
                                            </button>
                                        </form:form>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${not isAuthenticated}">
                                <li class="u-login border-left-line">
                                    <a href class="menu-tab-right" data-toggle="modal" data-target="#modal-login">
                                        <span class="icon entypo-login scnd-font-color"></span>
                                        <span><spring:message code="navMenu.login"/></span>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</header>
<!-- /.Menu -->

<!-- Modal Form -->
<div id="modal-login" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="loginLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form id="login-form" class="form-horizontal" action="/login" method="post" role="form">
                <div class="modal-header">
                    <h4 class="modal-title"><spring:message code="login"/></h4>
                    <button type="button" class="close" data-dismiss="modal">&#215;</button>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group required">
                                <label class="col-md-5 control-label" for="username"><spring:message code="username"/></label>
                                <div class="col-md-7">
                                    <input id="username" class="form-control" type="text" name="username" aria-required="true"/>
                                </div>
                            </div>
                            <div class="form-group required">
                                <label class="col-md-5 control-label" for="password"><spring:message code="password"/></label>
                                <div class="col-md-7">
                                    <input id="password" class="form-control" type="password" name="password" aria-required="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-7 col-md-offset-5 checkbox">
                                    <input id="rememberMe" type="checkbox" name="rememberMe" value="1" checked/>
                                    <label for="rememberMe"><spring:message code="remember_me"/></label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" name="login-button"><spring:message code="button.login"/></button>
                    <button type="submit" class="btn btn-default" data-dismiss="modal"><spring:message code="button.cancel"/></button>
                </div>
            </form:form>
        </div>
    </div>
</div>
<!-- /.Modal Form -->

<style>
    .btn-logout {
        background:none!important;
        color:inherit;
        border:none;
        padding:0!important;
        background-color: Transparent;
        font: inherit;
        /*border is optional*/
        cursor: pointer;
    }
    .btn-logout:focus {outline:0;}
</style>
