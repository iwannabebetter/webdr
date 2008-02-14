<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="reservaDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='reservaDetail.heading'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>">
    </script>
</head>

<s:form id="reservaForm" action="saveReserva" method="post" validate="true">
    
    <%-- <s:hidden name="reserva.id" value="%{reserva.id}"/>    --%>
    <s:hidden id="pasoReservar" value="1"/>
    
    <%-- Capa que contiene los datos de formulario del primer --%>
    <div id="paso1" class="group"> 
        
        <span><fmt:message key="reservaForm.paso1title"/></span>
        
        <%-- 
        Completar con los nombre de las funciones javascript correspondientes
        e implementar dichas funciones ajax. 
        Las funciones serían: actualizarDoctores, actualizarEspecialidades
        --%>
        <br>
        <span ><b><fmt:message key="reservaForm.fechaRealizacion"/>: </b><s:label value="%{fechaRealizacionSoloFecha}"/></span>
        <br>      
        
        <%-- | Select de Epecialidades | --%>
        <s:select id="especialidadesSelect" label="Especialidades" cssClass="select"
                  list="%{especialidades}" listKey="id" listValue="nombre"
                  headerKey="-999" headerValue="Seleccione la Especialidad"
                  value="especialidadId" onselect="javascript:void(0)"
        />
        <%-- | Select de Doctores | --%>
        <s:select id="doctoresSelect" label="Doctores" cssClass="select"
                  list="%{doctores}" listKey="id" listValue="fullName"
                  headerKey="-999" headerValue="Seleccione el Doctor"
                  value="doctorId"
        />
        <input type="button" id="siguientePaso" class="button" value="<fmt:message key='reservaForm.botonpaso1'/>" onclick= "javascript:void(0)"/>       
    </div>
    
    <%-- Selector de Fecha para la reserva --%>
    <div id="paso2" class="group" style="display:none;">  
        <%--span><fmt:message key="reservaForm.paso2title"/></span--%>
        
        <li>
            <div>
                <s:datetimepicker value="%{fechaReservadaTimestamp}" theme="ajax" 
                                  required="true" dayWidth="wide" 
                                  displayFormat="dd/MM/yyyy" toggleType="fade"
                                  onselect="javascript:ajaxGet('turnosDisp', 'viewTurnos.html', '')"
                />
            </div>
        </li>
        <!--- ver aka que tratamos de hacer.... no va a funcionar asi -->
        <s:hidden id="horarioId" name="horarioId" value=""/>    
        
        <div id="turnosDisp" class="radio" style="display:none;">
            <%-- Aquí se desplegarán los turnos disponibles
                 en base al horarioId.
            --%>
        </div>
    </div>
    
    <s:hidden id="pacienteId" name="pacienteId" value="%{reserva.paciente.id}"/>
    
    <li class="buttonBar bottom">
        <s:submit cssClass="button" method="saveReserva" key="button.save" 
                  theme="simple" id="saveButton" cssStyle="display:none;"/>
        <s:submit cssClass="button" method="cancelReserva" key="button.cancel"  
                  theme="simple"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement($("reservaForm"));
</script>