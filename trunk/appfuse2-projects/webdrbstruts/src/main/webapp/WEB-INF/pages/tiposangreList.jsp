<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="tiposangreList.title"/></title>
    <meta name="heading" content="<fmt:message key='tiposangreList.heading'/>"/>
</head>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/editTipoSangre.html"/>'"
        value="<fmt:message key="button.add"/>"/>

    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<s:set name="tiposangres" value="tiposangres" scope="request"/>
<display:table name="tiposangres" class="table" requestURI="" id="tiposangreList" export="true" pagesize="25">
    <display:column property="id" sortable="true" href="editTipoSangre.html"
        paramId="id" paramProperty="id" titleKey="tiposangre.id"/>
    <display:column property="TipoSangre" sortable="true" titleKey="tiposangre.tiposangre"/>

    <display:setProperty name="paging.banner.item_name" value="tiposangre"/>

    <display:setProperty name="export.excel.filename" value="Person List.xls"/>
    <display:setProperty name="export.csv.filename" value="Person List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Person List.pdf"/>
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("tiposangreList");
</script>