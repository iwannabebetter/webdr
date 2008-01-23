<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="consultaDetail.title"/></title>
	<meta name="heading" content="<fmt:message key='consultaDetail.heading'/>"/>
</head>

<s:form id="consultaForm" action="saveConsulta" method="post" validate="true">
	<s:hidden name="consulta.id" value="%{consulta.id}"/>
	<s:textfield key="consulta.firstName" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.lastName" required="true" cssClass="text medium"/>
	<li class="buttonBar bottom">
		<s:submit cssClass="button" method="save" key="button.save" theme="simple"/>
		<c:if test="${not empty consulta.id}">
			<s:submit cssClass="button" method="delete" key="button.delete" onclick="return confirmDelete('consulta')" theme="simple"/>
		</c:if>
		<s:submit cssClass="button" method="cancel" key="button.cancel" theme="simple"/>
	</li>
</s:form>

<script type="text/javascript">
	Form.focusFirstElement($("consultaForm"));
</script>
