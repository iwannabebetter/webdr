<%-- 
    Document   : horariosEdit
    Created on : 05-feb-2008, 18:53:02
    Author     : Fernando
--%>

<%@ include file="/common/taglibs.jsp"%>

<%--head>
    <title><fmt:message key="horariosForm.title"/></title>
    <meta name="heading" content="<fmt:message key='horariosForm.heading'/>"/>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head --%>

<b>Horario del Doctor <c:out value="${horarioAtencion.doctor.firstName}" />
<c:out value="${horarioAtencion.doctor.lastName}" /></b>

<s:form name="horariosFormDoctor" action="doctorHorariosSave" method="post" validate="true" >
    <s:hidden key="horarioAtencion.id"/>
    <s:param id="doctorUsername" name="doctorUsername" value="%{doctorUsername}" />
    <br>
    <%--s:textfield key="horarioAtencion.dia"  cssClass="text medium"/--%>

    <b>&nbsp;<fmt:message key="horarioAtencion.dia" />  </b>
    <s:set name="day" value="horarioAtencion.diaString" scope="page" />
    <appfuse:dias name="horarioAtencion.dia" prompt="" default="${day}"  />
    <br><br>
    
    
    <s:textfield key="horarioAtencion.horaInicio" cssClass="text medium"/>
    <s:textfield key="horarioAtencion.horaFin" cssClass="text medium"/>
    
    <s:hidden key="doctorUsername" value="%{doctorUsername}" />

    
    
    <li class="buttonBar bottom">
	<s:submit cssClass="button" method="save" key="button.save" theme="simple"/>
	<c:if test="${not empty horarioAtencion.id}">
            <s:submit cssClass="button" method="delete" key="button.delete" onclick="return confirmDelete('horarioAtencion')" theme="simple"/>
	</c:if>
         

<input type="button" style="margin-right: 5px"
    onclick="location.href='<c:url value="/doctor/doctorHorarios.html?doctorUsername=${doctorUsername}"/>'"
    value="Volver a horarios"/>

    </li>
    
</s:form>



<display:table name="horarioAtencion.turnos" class="table" id="turnosHorarioList" export="true" pagesize="25">
        <display:column property="hora" sortable="true" 
            paramId="id" paramProperty="id" titleKey="turno.hora"/>
            
        <display:column value="Borrar" sortable="true" href="doctorHorariosSave.html" 
            paramId="idTurno" paramProperty="id" />
</display:table>


<script type="text/javascript">
    Form.focusFirstElement(document.forms["horariosFormDoctor"]);
    highlightFormElements();
    
    function borrar_turno( idturno, nombre){
        if(confirm('¿Esta seguro que desea borrar el turno de las '+ nombre + '?')){
            location.href = 'doctorHorariosSave.html?idTurno='+idturno;
        }
    }
</script>

