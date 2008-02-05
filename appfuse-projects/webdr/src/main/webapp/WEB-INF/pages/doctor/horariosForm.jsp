<%-- 
    Document   : horariosEdit
    Created on : 05-feb-2008, 18:53:02
    Author     : Fernando
--%>

<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="horariosForm.title"/></title>
    <meta name="heading" content="<fmt:message key='horariosForm.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<b><c:out value="${horarioAtencion.doctor.firstName}" />
<c:out value="${horarioAtencion.doctor.lastName}" /></b>

<s:form name="horariosFormDoctor" action="saveHorario" method="post" validate="true">
    <s:hidden key="horarioAtencion.id"/>
        
    <s:textfield key="horarioAtencion.dia" theme="xhtml" required="true" cssClass="text medium"/>
    <s:textfield key="horarioAtencion.HoraInicio" theme="xhtml" required="true" cssClass="text medium"/>
    <s:textfield key="horarioAtencion.HoraFin" theme="xhtml" required="true" cssClass="text medium"/>
    <s:textfield key="horarioAtencion.doctor.username" theme="xhtml" required="true" cssClass="text medium"/>
</s:form>
