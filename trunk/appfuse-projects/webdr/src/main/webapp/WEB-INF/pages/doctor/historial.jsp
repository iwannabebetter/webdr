<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="consultaList.title"/></title>
    <meta name="heading" content="<fmt:message key='consultaList.heading'/>"/>
</head>

<s:set name="consultas" value="consultas" scope="request"/>
<display:table name="consultas" class="table" requestURI="" id="consultasList" export="true" pagesize="25">
    <!--display:column property="id" sortable="true" titleKey="consulta.id" href="viewConsulta.html" paramId="id" paramProperty="id" titleKey="consulta.id"/>/-->
	<display:column property="fechaString" sortable="true" titleKey="consulta.fecha"/>
	<display:column property="paciente.fullName" sortable="true" titleKey="consulta.paciente"/>
    <display:column property="doctor.fullName" sortable="true" titleKey="consulta.doctor"/>

    <!--display:column property="medidasPaciente.pesoActual" titleKey="consulta.medidasPaciente.pesoActual"/-->
    <!--display:column property="medidasPaciente.alturaActual" titleKey="consulta.medidasPaciente.alturaActual"/-->
    <!--display:column property="consulta.medidasPaciente.edadActual" titleKey="consulta.medidasPaciente.edadActual"/-->
    <!--display:column property="consulta.medidasPaciente.edadEnMeses" titleKey="consulta.medidasPaciente.edadEnMeses"/-->
    <!--display:column property="notas.sintomas" titleKey="consulta.notas.sintomas"/-->
    <!--display:column property="notas.diagnostico" titleKey="consulta.notas.diagnostico"/-->
    <!--display:column property="notas.recetario" titleKey="consulta.notas.recetario"/-->
    <display:column property="notas.indicaciones" titleKey="consulta.notas.indicaciones">
	<s:textarea key="notas.recetario" required="true" cssClass="text medium"/>
	</display:column>
    <display:setProperty name="paging.banner.item_name" value="consulta"/>
    <display:setProperty name="paging.banner.items_name" value="consultas"/>
    
    <display:setProperty name="export.excel.filename" value="Person List.xls"/>
    <display:setProperty name="export.csv.filename" value="Person List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Person List.pdf"/>
</display:table>
