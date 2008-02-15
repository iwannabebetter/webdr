/* Esta funcion ejecuta la llamada Ajax a la Acción de actualizar Doctores
   en base a la especialidad elegida en el Formulario de Creación de la reserva
   */
function actualizarDoctores(url,aaZone) {
    /*
    var especialidadId = document.getElementById("especialidadesSelect").value;
    
    var urlNew = url + "?especialidadId="+especialidadId;
    
    ajaxAnywhere.getAJAX(urlNew,aaZone);
    */
    var id = $('especialidadesSelect').value;
    ajaxGet(aaZone, url, 'especialidadId=' + id);
    var sigte = document.getElementById("siguientePaso");
    
    if(!sigte.disabled) {
        sigte.disabled=true;
    }    
}

function habilitarSegundoPaso() {
    var sigte = document.getElementById("siguientePaso");
    sigte.disabled=false;    
}

function crearReservaPaso2() {
    // agregar un elemento que recuerde a que doctor se eligió
    var indexSelected = document.getElementById("doctoresSelect").selectedIndex;
    var doctorName = document.getElementById("doctoresSelect").options[indexSelected].innerHTML;
    document.getElementById("docSelected").innerHTML=doctorName;
    document.getElementById("volverPaso1li").style.display="";
    document.getElementById("paso2li").style.display="";
    document.getElementById("volverPaso1").disabled=false;
    document.getElementById("paso1").style.display="none";
    document.getElementById("paso1cab").style.display="none";
    document.getElementById("siguientePasoli").style.display="none";    
}

function volverPasoUno() {
    document.getElementById("volverPaso1li").style.display="none";
    document.getElementById("paso2li").style.display="none";
    document.getElementById("volverPaso1").disabled=true;
    document.getElementById("paso1").style.display="";
    document.getElementById("paso1cab").style.display="";
    document.getElementById("siguientePasoli").style.display="";    

}

function actualizarTurnos(zona,url) {
    // verificar que se haya seleccionado una fecha
    //var fechaReservada = dojo.widget.byId("selectorFecha").getValue();
    var fechaReservada = document.getElementById("fechaField").value;
    
    var urlFinal = url;
    var id = $('doctoresSelect').value;
    
    ajaxGet(zona, url, 'fechaReservadaTimestamp='+fechaReservada+'&doctorId='+id);
}

// se ejecuta al seleccionar un turno
function habilitarSubmit() {
    document.getElementById("saveReserva").style.display="";    
}