<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="consultaDetail.title"/></title>
	<meta name="heading" content="<fmt:message key='consultaDetail.heading'/>"/>
</head>

<s:form id="consultaForm" action="saveConsulta" method="post" validate="true">
	<s:hidden name="consulta.id" value="%{consulta.id}"/>
	<s:textfield key="consulta.fecha" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.horaInicio" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.horaFin" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.medidasPaciente.pesoActual" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.medidasPaciente.alturaActual" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.medidasPaciente.edadActual" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.medidasPaciente.edadEnMeses" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.notas.sintomas" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.notas.diagnostico" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.notas.recetario" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.notas.indicaciones" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.paciente.username" required="true" cssClass="text medium"/>
	<s:textfield key="consulta.doctor.username" required="true" cssClass="text medium"/>	

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
