<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="pacienteList.title"/></title>
    <meta name="heading" content="<fmt:message key='pacienteList.heading'/>"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editPaciente.html"/>'"
        value="<fmt:message key="button.add"/>"/>
    
    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<s:set name="pacientes" value="pacientes" scope="request"/>
<display:table name="pacientes" class="table" requestURI="" id="pacienteList" export="true" pagesize="25">
    <display:column property="id" sortable="true" href="editPaciente.html" 
        paramId="id" paramProperty="id" titleKey="paciente.id"/>
    <display:column property="username" sortable="true" titleKey="paciente"/>
    <display:column property="firstName" sortable="true" titleKey="paciente"/>
    <display:column property="lastName" sortable="true" titleKey="paciente"/>

    <display:setProperty name="person" value="person"/>
    <display:setProperty name="paging.banner.items_name" value="people"/>

    <display:setProperty name="export.excel.filename" value="Person List.xls"/>
    <display:setProperty name="export.csv.filename" value="Person List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Person List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("pacienteList");
</script>
