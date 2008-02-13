/*
 * ReservaAction.java
 *
 * Created on 17 de enero de 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import edu.fpuna.model.DiaDeSemana;
import edu.fpuna.model.DiaDeSemana;
import edu.fpuna.model.Reserva;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.Turno;
import edu.fpuna.service.ConsultaManager;
import edu.fpuna.service.ReservaManager;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.EspecialidadManager;
import edu.fpuna.service.HorarioAtencionManager;
import edu.fpuna.service.PacienteManager;
import edu.fpuna.service.TurnoManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Action de la clase Reserva.
 * @author Hugo Meyer, Marcelo Rodas
 */
public class ReservaAction extends BaseAction {
    private Set<HorarioAtencion> horarios;

    /*
     * Managers utilizados en las funcionalidades de Reserva
     */
    private ReservaManager manager;
    private DoctorManager doctorManager;
    private EspecialidadManager especialidadManager;
    private PacienteManager pacienteManager;
    private ConsultaManager consultaManager;
    private HorarioAtencionManager horarioAtencionManager;
    private TurnoManager turnoManager;
    
    /*
     * Listados a utilizar
     */
    private List<Reserva> reservas;
    private List<Turno> turnosDisp;
    private List<Doctor> doctores;
    private List<Especialidad> especialidades;
    
    /*
     * Otros objetos a utilizar. POJOs singles de los conjuntos sobre los que se opera...
     * 
     */
    private Turno turno;
    private Doctor doctor;    
    private Reserva reserva;
    private Especialidad especialidad; 
    
    /* Otras variables importantes */
    private Long id;
    private String doctorId;
    private String especialidadId;
    private String turnoId;
    private Timestamp fechaReservada;
    private Timestamp fechaFin;
    private String soloVista = null;
    

    public void setReservaManager(ReservaManager reservaManager) {
        this.manager = reservaManager;
    }

    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }

    public void setPacienteManager(PacienteManager pacienteManager) {
        this.pacienteManager = pacienteManager;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserNamePaciente(String username) {
        this.doctorId = username;
    }
    public String getUserNamePaciente() {
        return this.doctorId;
    }
    
    public Reserva getReserva() {
        return reserva;
    }
    
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaReservada = fechaInicio;
    }
    
    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setReserva(Reserva reserva) {
        log.debug("EJECUTANDO 1... "+reserva);
        log.debug("EJECUTANDO 2... "+this.reserva);
        this.reserva = reserva;
    }

    public String listReservasPaciente() {
        String username = this.getRequest().getRemoteUser();
        boolean pendienteBool = false;
        
        log.debug("--> PACIENTE: "+username);
        
        reservas = manager.obtenerReservasPaciente(username, pendienteBool);
        return SUCCESS;
    }

    public String listReservasPacientePend() {
        String username = this.getRequest().getRemoteUser();
        boolean pendienteBool = true;
        
        log.debug("--> PACIENTE: "+username);
        
        reservas = manager.obtenerReservasPaciente(username, pendienteBool);
        return SUCCESS;
    }
    
    public String listReservasDoctor() {

        String username = this.getRequest().getParameter("username");
        String pendiente = this.getRequest().getParameter("pendiente");
        boolean pendienteBool = false;
        
        if (pendiente.compareTo("S") == 0) {
            pendienteBool = true;            
        }
        log.debug("--> PACIENTE: "+username);
        
        reservas = manager.obtenerReservasDoctor(username, pendienteBool);
        return SUCCESS;
    }

    public String listReservasFecha(Timestamp fechaInicio, Timestamp fechaFin) {
        try {
            
            reservas = manager.obtenerReservasFecha(fechaInicio, fechaFin);
            return SUCCESS;
        } catch (Exception ex) {
            log.fatal(ex);
            saveMessage(getText("reserva.invalidUserType"));
            return ERROR;
        }
    }
    
    public List getReservas() {
        return reservas;
    }
 
    public String getSoloVista(){
        return this.soloVista;
    }

    public String list() {
        this.soloVista=null;
        reservas = manager.obtenerReservas();
        return SUCCESS;
    }

    /* Delete, Editar, Guardar*/
    public String delete() {
        manager.eliminarReserva(reserva.getId());
        saveMessage(getText("reserva.deleted"));
        return SUCCESS;
    }

    public String edit() {
        String username = this.getRequest().getRemoteUser();
        this.setUserNamePaciente(username);
        if (id != null) {
            log.debug("EDITANDO...");
            reserva = manager.obtenerReserva(id);
            log.debug("Se obtuvo: " + reserva.getPaciente().getEmail());
        } 
        else {
            
            reserva = new Reserva();
        }

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null)
            return CANCEL;

        if (delete != null)
            return delete();

        boolean isNew = (reserva.getId() == null);
        
        log.debug("GUARDANDO...");
        log.debug("1.1-)Se obtuvo: " + reserva.getFechaReservadaString() + "--" +this.getRequest().getParameter("reserva.fechaInicio"));
        
        /*
         * Se recupera la reserva modificada debido a que el form (del edit)
         * no envia los datos del Doctor y del Paciente.
         */
        String username = this.getRequest().getRemoteUser();
        this.setUserNamePaciente(username);
        
        Paciente paciente = this.pacienteManager.getPaciente(reserva.getPaciente().getId());
        Doctor doctor = this.doctorManager.obtenerDoctorPorNombre(username);
        //log.debug("2.1-)Se obtuvo: " + oldReserva.getFecha());
        
        // Se actualizan los datos del Doctor y del Paciente.

        reserva.setPaciente(paciente);

        // Se guarda la reserva modificada.
        manager.guardarReserva(reserva);
        
        String key = (isNew) ? "reserva.added" : "reserva.updated";
        saveMessage(getText(key));
        
        if (!isNew)
            return INPUT;
        else
            return SUCCESS;
    }

    public void setConsultaManager(ConsultaManager consultaManager) {
        this.consultaManager = consultaManager;
    }

    public void setHorarioAtencionManager(HorarioAtencionManager horarioAtencionManager) {
        this.horarioAtencionManager = horarioAtencionManager;
    }

    public void setTurnoManager(TurnoManager turnoManager) {
        this.turnoManager = turnoManager;
    }
    
    public void getReservasDisp(HorarioAtencion ha, Timestamp fecha){
        List<Turno> turnos =this.turnoManager.getTurnos(ha);
        Iterator<Turno> it = turnos.iterator();
        boolean disponible = true;
        Turno turnoActual;
        while(it.hasNext()){
            turnoActual = it.next();
            disponible = this.manager.isTurnoDisponible(turnoActual, fecha);
            if(disponible){
                this.turnosDisp.add(turnoActual);
            }
        }
    }

    public List<Turno> getTurnosDisp() {
        return turnosDisp;
    }

    public void setTurnosDisp(List<Turno> turnosDisp) {
        this.turnosDisp = turnosDisp;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    
    public void getDoctoresFecha(Timestamp fecha){
        DiaDeSemana dia = obtenerDia(fecha);
        this.doctores = this.doctorManager.obtenerPorDia(dia);
    }

    private DiaDeSemana obtenerDia(Timestamp fecha) {
        DiaDeSemana dia = null;
                
        if(fecha.getDay() == 0){
            return dia.DOMINGO;
        }
        if(fecha.getDay() == 1){
            return dia.LUNES;
        }
        if(fecha.getDay() == 2){
            return dia.MARTES;
        }
        if(fecha.getDay() == 3){
            return dia.MIERCOLES;
        }
        if(fecha.getDay() == 4){
            return dia.JUEVES;
        }
        if(fecha.getDay() == 5){
            return dia.VIERNES;
        }
        if(fecha.getDay() == 6){
            return dia.SABADO;
        }
        return dia;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    
    public String actualizarTurnos(){
        
        
        //String doctorIdString = this.getRequest().getParameter("doctoresSelect.value");
        String fechaString = this.getRequest().getParameter("fechaReservada");
        Timestamp fecha = this.convertirFecha(fechaString);
        
        Long doctorIdlong = Long.parseLong(this.doctorId);
        
        this.doctor = this.doctorManager.get(doctorIdlong);
        
        
        // obtener el horario de atencion del doctor que coincide con el 
        // dia dado por fechareservada        
        // implementa esto vos HUGO
        
        DiaDeSemana dia = this.obtenerDia(fecha);
        this.setHorarios(this.obtenerHorarioDia(dia));
        Set<HorarioAtencion> listh = this.getHorarios();
        
        Iterator<HorarioAtencion> it = listh.iterator();
        
        
        // mejorar separando los turnos de un día de los del otro.
        // en realidad, creo que no hace falta obtener los turnos puesto que ya estàn 
        // en los horarios...
        while (it.hasNext()) {
            HorarioAtencion current = it.next();
            List<Turno> turnos = this.turnoManager.getTurnos(current);
            Iterator<Turno> itTurno = turnos.iterator();
            while(itTurno.hasNext()){
                Turno t = itTurno.next();
                if(this.manager.isTurnoDisponible(t,fecha)){
                    this.turnosDisp.add(t);      
                }
            }
            
        }
        return SUCCESS;
        
    }

    public String getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(String especialidadId) {
        this.especialidadId = especialidadId;
    }

    public String getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(String turnoId) {
        this.turnoId = turnoId;
    }

    private Set<HorarioAtencion> obtenerHorarioDia(DiaDeSemana ds) {
        Set<HorarioAtencion> listh = this.doctor.getHorarios();
        Set<HorarioAtencion> listReturn = new HashSet<HorarioAtencion>();
        
        Iterator<HorarioAtencion> it = listh.iterator();
        
        while (it.hasNext()) {
            HorarioAtencion current = it.next();
            
            if (current.getDia().compareTo(ds) == 0) {
                listReturn.add(current);
            }                
        }
        
        return listReturn;
    }

    public Set<HorarioAtencion> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<HorarioAtencion> horarios) {
        this.horarios = horarios;
    }
    
    public Timestamp convertirFecha(String f){
        Timestamp retorno = null;
        String[] fecha = f.split("/");
        retorno.setDate(Integer.parseInt(fecha[0].trim()));
        retorno.setMonth(Integer.parseInt(fecha[1].trim()));
        retorno.setYear(Integer.parseInt(fecha[2].trim())-1900);
        
        return retorno;
    }

}
