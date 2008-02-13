<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="reservaDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='reservaDetail.heading'/>"/>
</head>

<s:form id="reservaForm" action="saveReserva" method="post" validate="true">
    
    <s:hidden name="reserva.id" value="%{reserva.id}"/>
    
    <s:hidden id="pasoReservar" value="1"/>
    

    <!-- Capa que contiene los datos de formulario del primer paso para las 
    reservas 
         -->
    <div id="paso1" class="group">    
        
        <fmt:message key="reservaForm.paso1title" class=""/>
        
        <!-- Completar con los nombre de las funciones javascript correspondientes
        e implementar dichas funciones ajax. 
             Las funciones serían: actualizarDoctores, actualizarEspecialidades
             
             Referencia el tag s:select
             < s:select label="Pets"
                    name="petIds"
                    list="petDao.pets"
                    listKey="id"
                    listValue="name"
                    multiple="true"
                    size="3"
                    required="true"
            />
            < s:select label="Months"
                   name="months"
                   headerKey="-1" headerValue="Select Month"
                   list="# {'01':'Jan', '02':'Feb', [...]}"
                   value="selectedMonth"
                   required="true"
            />
             -->
        <!-- | Select de Epecialidades | -->
        <s:select label="Especialidades" class="select" key="especialidadId"
                  headerKey="-h" headerValue="Filtrar por Especialidad"
                  id="especialidadesSelect" list="especialidades"
                  listkey="id" listvalue="nombre" name="especialidadesSelect"                   
                  onchange="javascript:void(0);"
        />
        
        <!-- | Select de Doctores | -->
        <s:select id="doctoresSelect" class="select" key="doctorId"
                  label="Doctores" name="doctoresSelect" 
                  headerKey="-h" headerValue="Seleccione el Doctor"
                  list="doctores" listkey="id" listvalue="fullName"
                  class="select" onchange="javascript:void(0);"
        />
                
        <s:button id="siguientePaso" class="button" onclick="javascript:void(0)"/>
                
    </div>
    
    <!-- Selector de Fecha para la reserva -->
    <div id="paso2" class="group" style="display:none;">
        
        <fmt:message key="reservaForm.paso2title"/>
        
        <li>
            <div>
                <s:datetimepicker key="fechaReservada" theme="ajax" 
                                  required="true" dayWidth="wide" 
                                  displayFormat="dd/MM/yyyy" toggleType="fade"
                                  onselect="javascript:ajaxGet('turnosDisp', 'viewTurnos.html', '')"
                />
            </div>
        </li>
        
        <s:hidden id="horarioId" name="horarioId" value="%{reserva.horarioAtencion.id}"/>    
        <div id="turnosDisp" class="radio" style="display:none;">
            <!-- Aquí se desplegarán los turnos disponibles
                 en base al horarioId.
            -->
            
        </div>
    </div>
    
    <s:hidden name="reserva.paciente.id" value="%{reserva.paciente.id}"/>
    
    <li class="buttonBar bottom">
        <s:submit cssClass="button" method="saveReserva" key="button.save" 
                  theme="simple" id="saveButton" style="display:none;"/>
        <s:submit cssClass="button" method="cancelReserva" key="button.cancel"  
                  theme="simple"/>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement($("reservaForm"));
    </script>