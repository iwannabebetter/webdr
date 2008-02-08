<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="reservaDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='reservaDetail.heading'/>"/>
</head>

<s:form id="reservaForm" action="saveReserva" method="post" validate="true">
    <s:hidden name="reserva.id" value="%{reserva.id}"/>
    <s:textfield key="reserva.fechaRealizacion" required="true" cssClass="text medium"/>
    <s:textfield key="reserva.fecha" required="true" cssClass="text medium"/>
    <s:textfield key="consulta.medidasPaciente.alturaActual" required="true" cssClass="text medium"/>
    <s:textfield key="consulta.medidasPaciente.edadActual" required="true" cssClass="text medium"/>
    <s:textfield key="consulta.medidasPaciente.edadEnMeses" required="true" cssClass="text medium"/>
    <s:textfield key="consulta.notas.sintomas" required="true" cssClass="text medium"/>
    <s:textarea key="consulta.notas.diagnostico" required="true" cssClass="text medium"/>
    <s:textarea key="consulta.notas.recetario" required="true" cssClass="text medium"/>
    <s:textarea key="consulta.notas.indicaciones" required="true" cssClass="text medium"/>
    <s:textfield key="consulta.paciente.fullName" required="true" cssClass="text medium"/>
    <s:textfield label="Doctor Consultado" key="userNameDoctor" required="true" disabled="true" cssClass="text medium"/>
    <s:hidden name="consulta.paciente.id" value="%{consulta.paciente.id}"/>
    <s:hidden name="consulta.doctor.id" value="%{consulta.doctor.id}"/>
    
    <li class="buttonBar bottom">
        <s:submit cssClass="button" method="save" key="button.save" theme="simple"/>
        <c:if test="${not empty consulta.id}">
            <s:submit cssClass="button" method="delete" key="button.delete" onclick="return confirmDelete('esta consulta')" theme="simple"/>
        </c:if>
        <s:submit cssClass="button" method="cancel" key="button.cancel" theme="simple"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement($("consultaForm"));
    </script>