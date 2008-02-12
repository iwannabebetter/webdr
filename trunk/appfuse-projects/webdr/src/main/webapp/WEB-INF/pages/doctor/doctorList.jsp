<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="doctorList.title"/></title>
    <meta name="heading" content="<fmt:message key='doctorList.heading'/>"/>
</head>
<s:if test="%{editAccess}">    
    <c:set var="buttons">
        <input type="button" style="margin-right: 5px"
               onclick="location.href='<c:url value="/editDoctor.html?from=list"/>'"
               value="<fmt:message key="button.add"/>"/>
        <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
               value="<fmt:message key="button.done"/>"/>
    </c:set>
    <c:out value="${buttons}" escapeXml="false"/>
</s:if>


<s:set name="doctores" value="doctores" scope="request"/>
<display:table name="doctores" class="table" requestURI="" id="doctorList" pagesize="15">
    <display:column titleKey="doctor.registro" sortable="true">
        <a onclick="javascript:ajaxGet('detalles', 'viewDoctor.html', 'id=${doctorList.id}')">
            <c:out value="${doctorList.registro}"/>
        </a>
    </display:column>
    
    <display:column property="firstName" sortable="true" titleKey="doctor.firstName"/>
    <display:column property="lastName" sortable="true" titleKey="doctor.lastName"/>
    <display:column property="fechaNacimientoString" sortable="true" titleKey="doctor.fechaNacimiento"/>
    
    <display:setProperty name="paging.banner.item_name" value="doctor"/>
    <display:setProperty name="paging.banner.items_name" value="doctores"/>
    
    <display:setProperty name="export.excel.filename" value="Doctores.xls"/>
    <display:setProperty name="export.pdf.filename" value="Doctores.pdf"/>
    <display:setProperty name="export.csv.filename" value="Doctores.csv"/>
</display:table>

<script type="text/javascript">
    highlightTableRows("doctorList");
</script>

<div id="detalles" style="display: inline; overflow: auto; display: none;"></div>