<%@ include file="/common/taglibs.jsp"%>
<head>
	<title><fmt:message key="consultaDetail.title"/></title>
	<meta name="heading" content="<fmt:message key='consultaDetail.heading'/>"/>
</head>

<s:form id="consultaForm" action="saveConsulta" method="post" validate="true">
    <s:hidden name="consulta.id" value="%{consulta.id}"/>
	<s:label key="consulta.fecha" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.medidasPaciente.pesoActual" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.medidasPaciente.alturaActual" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.medidasPaciente.edadActual" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.medidasPaciente.edadEnMeses" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.notas.sintomas" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.notas.diagnostico" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.notas.recetario" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.notas.indicaciones" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.paciente.fullName" disabled="true" cssClass="text medium"/>
	<s:label key="consulta.doctor.fullName" disabled="true" cssClass="text medium"/>
</s:form>