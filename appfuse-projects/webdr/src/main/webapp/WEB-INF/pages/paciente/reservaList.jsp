<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="reservaList.title"/></title>
    <meta name="heading" content="<fmt:message key='reservaList.heading'/>"/>
</head>
<s:if test="%{soloVista!='ok'}">
    <c:set var="buttons">
        <input type="button" style="margin-right: 5px"
               onclick="location.href='<c:url value="reservas.html"/>'"
               value="<fmt:message key="button.add"/>"/>
        <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
               value="<fmt:message key="button.done"/>"/>
    </c:set>
    <c:out value="${buttons}" escapeXml="false" />
</s:if>


<display:table name="reservas" class="table" requestURI="" id="reservasList" export="true" pagesize="25">
    <s:if test="%{soloVista=='ok'}">
        <display:column property="id" sortable="true" href="reservas.html" 
                        paramId="id" paramProperty="id" titleKey="reserva.id"/>
    </s:if>
    <s:else>
        <display:column property="id" sortable="true" href="reservas.html" 
                        paramId="id" paramProperty="id" titleKey="reserva.id"/>
    </s:else>
    <display:column property="fechaRealizacion" sortable="true" titleKey="reserva.fechaRealizacion"/>
    <display:column property="fechaReservada" sortable="true" titleKey="reserva.fechaReservada"/>
    <display:column property="consulta.doctor.fullName" sortable="true" titleKey="reserva.doctor"/>
    <display:column property="paciente.fullName" sortable="true" titleKey="reserva.paciente"/>
    
    <display:setProperty name="paging.banner.item_name" value="reserva"/>
    <display:setProperty name="paging.banner.items_name" value="reservas"/>
    
    <display:setProperty name="export.excel.filename" value="Reserva List.xls"/>
    <display:setProperty name="export.csv.filename" value="Reserva List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Reserva List.pdf"/>
</display:table>

<s:if test="%{soloVista!='ok'}">
    <c:out value="${buttons}" escapeXml="false" />
</s:if>
<script type="text/javascript">
    highlightTableRows("reservasList");
</script>