<%@ include file="/common/taglibs.jsp"%>

<input type="button" style="margin-right: 5px"
    onclick="location.href='<c:url value="/doctor/doctorHorariosEdit.html?doctorUsername=${doctorUsername}"/>'"
    value="<fmt:message key="button.add"/>"/>

<display:table name="horariosDoctor" class="table" requestURI="" id="horariosDoctorList" export="true" pagesize="25">
    <display:column property="dia" sortable="true" href="doctorHorariosEdit.html" 
        paramId="id" paramProperty="id" titleKey="horarioAtencion.dia"/>
        
    <display:column property="horaInicioString" sortable="true" titleKey="horarioAtencion.horaInicio"/>
    <display:column property="horaFinString" sortable="true" titleKey="horarioAtencion.horaFin"/>
    <display:column property="doctor.username" sortable="true" titleKey="horarioAtencion.doctor.username"/>

    <display:setProperty name="paging.banner.item_name" value="horario del doctor"/>
    <display:setProperty name="paging.banner.items_name" value="horarios del doctor"/>

    <display:setProperty name="export.excel.filename" value="HorarioDr.xls"/>
    <display:setProperty name="export.csv.filename" value="HorarioDr.csv"/>
    <display:setProperty name="export.pdf.filename" value="HorarioDr.pdf"/>
</display:table>

<script type="text/javascript">
    highlightTableRows("horariosDoctorList");
</script>
