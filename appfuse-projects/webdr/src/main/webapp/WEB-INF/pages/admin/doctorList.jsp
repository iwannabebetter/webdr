<%-- 
    Document   : doctorList
    Created on : 25/01/2008, 05:33:29 PM
    Author     : ghuttemann
--%>

<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="doctorList.title"/></title>
    <meta name="heading" content="<fmt:message key='doctorList.heading'/>"/>
</head>
<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
           onclick="location.href='<c:url value="/editDoctor.html"/>'"
           value="<fmt:message key="button.add"/>"/>
    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
           value="<fmt:message key="button.done"/>"/>
</c:set>
<c:out value="${buttons}" escapeXml="false"/>
<s:set name="doctores" value="doctores" scope="request"/>
<display:table name="doctores" class="table" requestURI="" id="doctorList" export="true" pagesize="15">
    <display:column property="registro" escapeXml="true" sortable="true" titleKey="doctor.registro" style="width: 15%"
        url="/editDoctor.html?from=list" paramId="id" paramProperty="id"/>
    <display:column property="firstName" sortable="true" titleKey="doctor.firstName"/>
    <display:column property="lastName" sortable="true" titleKey="doctor.lastName"/>
    <display:column property="fechaNacimiento" sortable="true" titleKey="doctor.fechaNacimiento"/>
    
    <display:setProperty name="paging.banner.item_name" value="doctor"/>
    <display:setProperty name="paging.banner.items_name" value="doctores"/>
    
    <display:setProperty name="export.excel.filename" value="Doctores.xls"/>
    <display:setProperty name="export.pdf.filename" value="Doctores.pdf"/>
    <display:setProperty name="export.csv.filename" value="Doctores.csv"/>
</display:table>
<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("doctorList");
</script>
