<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="especialidadList.title"/></title>
    <meta name="heading" content="<fmt:message key='especialidadList.heading'/>"/>
</head>
<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
           onclick="location.href='<c:url value="editEspecialidad.html?from=list"/>'"
           value="<fmt:message key="button.add"/>"/>
    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
           value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />
<s:set name="especialidades" value="especialidades" scope="request"/>
<display:table name="especialidades" class="table" requestURI="" id="especialidadList" export="true"
               pagesize="25">
    <display:column property="nombre" sortable="true" titleKey="especialidad.nombre"
                    url="editEspecialidad.html?from=list" paramId="id" paramProperty="id"/>
    <display:column property="descripcion" sortable="true" titleKey="especialidad.descripcion"/>
    
    <display:setProperty name="paging.banner.item_name" value="especialidad"/>
    <display:setProperty name="paging.banner.items_name" value="especialidades"/>
    <display:setProperty name="export.excel.filename" value="Espacialidades.xls"/>
    <display:setProperty name="export.csv.filename" value="Espacialidades.csv"/>
    <display:setProperty name="export.pdf.filename" value="Espacialidades.pdf"/>
</display:table>
<c:out value="${buttons}" escapeXml="false" />

<script type="text/javascript">
    highlightTableRows("especialidadList");
</script>