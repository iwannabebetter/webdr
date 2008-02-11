<%-- 
    Document   : especialidadForm
    Created on : 31-ene-2008, 11:33:44
    Author     : Fernando
--%>

<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="especialidadDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='especialidadDetail.heading'/>"/>
</head>

<s:form id="especialidadForm" action="saveEspecialidad" method="post" validate="true">
    <s:hidden name="especialidad.id" value="%{especialidad.id}"/>
    <s:textfield key="especialidad.nombre" required="true" cssClass="text large"/>
    <s:textfield key="especialidad.descripcion" required="true" cssClass="text large"/>
    <li class="buttonBar bottom">
        <s:submit cssClass="button" method="save" key="button.save" theme="simple"/>
        <c:if test="${not empty especialidad.id}">
            <s:submit cssClass="button" method="delete" key="button.delete" onclick="return confirmDelete('esta especialidad')" theme="simple"/>
        </c:if>
        <s:submit cssClass="button" method="cancel" key="button.cancel" theme="simple"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement($("especialidadForm"));
</script>
