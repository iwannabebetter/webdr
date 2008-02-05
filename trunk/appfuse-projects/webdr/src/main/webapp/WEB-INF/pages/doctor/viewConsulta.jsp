<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="consultaDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='consultaDetail.heading'/>"/>
</head>

<s:form id="consultaForm" action="saveConsulta" method="post" validate="true">
    <s:label key="consulta.fecha" cssClass="text medium"/>
    <s:label key="consulta.medidasPaciente.pesoActual" cssClass="text medium"/>
    <s:label key="consulta.medidasPaciente.alturaActual" cssClass="text medium"/>
    <s:label key="consulta.medidasPaciente.edadActual" cssClass="text medium"/>
    <s:label key="consulta.medidasPaciente.edadEnMeses" cssClass="text medium"/>
    <s:label key="consulta.notas.sintomas" cssClass="text medium"/>
    <s:label key="consulta.notas.diagnostico" cssClass="text medium"/>
    <s:label key="consulta.notas.recetario" cssClass="text medium"/>
    <s:label key="consulta.notas.indicaciones" cssClass="text medium"/>
    <s:label key="consulta.paciente.fullName" cssClass="text medium"/>
    <s:label key="consulta.doctor.fullName" cssClass="text medium"/>
</s:form>