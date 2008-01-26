<%-- 
    Document   : doctorList
    Created on : 25/01/2008, 05:33:29 PM
    Author     : ghuttemann
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

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
<display:table name="doctores" class="table" requestURI="" id="doctorList" export="true" pagesize="10">
    <display:column property="id" sortable="true" href="editDoctor.html" paramId="id" paramProperty="id" titleKey="doctor.id"/>
    <display:column property="firstName" sortable="true" titleKey="doctor.firstName"/>
    <display:column property="lastName" sortable="true" titleKey="doctor.lastName"/>
    <display:setProperty name="paging.banner.item_name" value="doctor"/>
    <display:setProperty name="paging.banner.items_name" value="people"/>
    <display:setProperty name="export.excel.filename" value="Doctores.xls"/>
    <display:setProperty name="export.pdf.filename" value="Doctores.pdf"/>
</display:table>
<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("doctorList");
</script>
