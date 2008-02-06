<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="consultaList.title"/></title>
    <meta name="heading" content="<fmt:message key='consultaList.heading'/>"/>
</head>

<s:set name="consultas" value="consultas" scope="request"/>
<display:table name="consultas" class="table" requestURI="" id="consultasList" export="true" pagesize="25">
    <display:column property="id" sortable="true" href="historialDetalle.html" paramId="id" paramProperty="id" titleKey="consulta.id"/>/>
	<display:column property="fechaString" sortable="true" titleKey="consulta.fecha"/>
	<display:column property="paciente.fullName" sortable="true" titleKey="consulta.paciente"/>
    <display:column property="doctor.fullName" sortable="true" titleKey="consulta.doctor"/>

    <display:setProperty name="paging.banner.item_name" value="consulta"/>
    <display:setProperty name="paging.banner.items_name" value="consultas"/>
    
    <display:setProperty name="export.excel.filename" value="Person List.xls"/>
    <display:setProperty name="export.csv.filename" value="Person List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Person List.pdf"/>
</display:table>
<script type="text/javascript">
    highlightTableRows("consultasList");
</script>