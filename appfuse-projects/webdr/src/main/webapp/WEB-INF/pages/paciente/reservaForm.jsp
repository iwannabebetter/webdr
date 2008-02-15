<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="reservaDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='reservaDetail.heading'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/reservas.js'/>"></script>
</head>

<s:form id="reservaForm" action="saveReserva" method="post" validate="true">
    
    <s:hidden id="pasoReservar" value="1"/>
    <s:hidden id="horarioId" name="horarioId" value=""/>    
    <s:hidden id="pacienteId" name="pacienteId" value="%{reserva.paciente.id}"/>
    
    <%-- Capas que contienen los datos de formulario del primer --%>
    <li id="paso1cab" >
        <div class="group">             
            <h3><fmt:message key="reservaForm.paso1title"/></h3>
            <%-- 
                    Completar con los nombre de las funciones javascript correspondientes
                    e implementar dichas funciones ajax. 
                    Las funciones serían: actualizarDoctores, actualizarEspecialidades
            --%>            
            <span >
                <b>
                    <fmt:message key="reservaForm.fechaRealizacion"/>: 
                </b>
                <s:label value="%{fechaRealizacionSoloFecha}"/>
            </span>         
        </div>            
    </li>  
    <%-- paso1, selects --%>
    <li id="paso1" >
        <%-- | Select de Epecialidades | --%>
        <div class="left">
            <s:select id="especialidadesSelect"  label="Especialidades" cssClass="select"
                      list="%{especialidades}" listKey="id" listValue="nombre"
                      headerKey="-999" headerValue="Todos los Doctores"
                      value="%{especialidadId}" size="10" onchange="actualizarDoctores('actualizarDoctores.html','doctoresList')"
                      />
        </div>                
        <%-- | Select de Doctores | --%>        
        <div id="doctoresList">            
            <s:select id="doctoresSelect" label="Doctores" cssClass="select"
                      list="%{doctores}" listKey="id" listValue="fullName"                     
                      value="doctorId" size="10" onselect="habilitarSegundoPaso();"
                      />
        </div>
    </li>
    
    <%-- Boton para continuar al segundo paso --%>
    <li id="siguientePasoli">
        <div>
            <input type="button" id="siguientePaso" class="button" 
                   onclick="crearReservaPaso2()" disabled="true"
                   value="<fmt:message key='reservaForm.botonpaso1'/>"  />
        </div>
    </li>
    
    <li id="volverPaso1li" style="display:none">
        <div>
            <input type="button" id="volverPaso1" class="button" 
                   onclick="volverPasoUno();" disabled="true"
                   value="<fmt:message key='reservaForm.botonpaso2volver'/>"  />
        </div>
    </li>
    <%-- Selector de Fecha para la reserva --%>
    <li id="paso2li" style="display:none;">
        <div id="paso2" class="group">  
            <h3><fmt:message key="reservaForm.paso2title"/></h3>            
            <span>
                <b>
                    <fmt:message key="reservaForm.doctorSeleccionado"/>: 
                </b>
                <div id="docSelected"></div>
            </span>
            <br>
            <span>
                    <fmt:message key="reservaForm.fechaReservaSeleccione"/>
            </span>         
            <li>
                <div>
                    <s:datetimepicker id="selectorFecha" value="%{fechaReservada}" theme="ajax" 
                                      required="true" dayWidth="wide" label="Fecha a Reservar"  
                                      displayFormat="yyyy-MM-dd" toggleType="fade"
                                      onselect="actualizarTurnos('turnosDisp', 'viewTurnos.html');"
                                      />
                    <input type="button" id="botonFecha" class="button" 
                     onclick="actualizarTurnos('turnosDisp', 'viewTurnos.html');"
                     value="Ver Turnos"  />                 
                </div>
            </li>
            
            
            <div id="turnosDisp" class="radio" style="display:none;" >
                <%-- Aquí se desplegarán los turnos disponibles
                 en base al horarioId.
                --%>
            </div>
        </div>
    </li>
    
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
