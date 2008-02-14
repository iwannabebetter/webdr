/*
 * ReservaAction.java
 *
 * Created on 17 de enero de 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import edu.fpuna.Constants;
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
import edu.fpuna.service.RoleManager;
import edu.fpuna.service.TurnoManager;
import edu.fpuna.service.UserManager;
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
    private Paciente paciente;
    
    /* Otras variables importantes */
    private Long id;
    
    private String reservaId;
    private String doctorId;
    private String pacienteId;
    private String especialidadId;
    private String turnoId;
    private String fechaReservada;
    private String fechaRealizacion;
    private String fechaRealizacionSoloFecha;
    
    private Timestamp fechaReservadaTimestamp;
    private Timestamp fechaRealizacionTimestamp;
    
    private String soloVista = null;
    

    /* ---------------------------------------------
     SETTERS DE LOS MANAGERS 
     ----------------------------------------------*/
    public void setReservaManager(ReservaManager reservaManager) {
        this.manager = reservaManager;
    }

    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }

    public void setPacienteManager(PacienteManager pacienteManager) {
        this.pacienteManager = pacienteManager;
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
  
    public void setEspecialidadManager(EspecialidadManager especialidadManager) {
        this.especialidadManager = especialidadManager;
    }
    
    /* ---------------------------------------------
     GETTERS Y SETTERS DE POJOS
     ----------------------------------------------*/        
    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
 
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        log.debug("EJECUTANDO 1... "+reserva);
        log.debug("EJECUTANDO 2... "+this.reserva);
        this.reserva = reserva;
    }
  
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }  
      
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doc) {
        this.doctor = doc;
    }  

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente pac) {
        this.paciente = pac;
    }  
    
    /* ---------------------------------------------
     GETTERS Y SETTERS DE ATRIBUTOS SIMPLES QUE SE TRANSMITEN EN EL REQUEST
     ----------------------------------------------*/    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return this.id;
    }

    public String getReservaId() {
        return this.reservaId;
    }
    
    public void setReservaId(String reservaId) {
        this.reservaId = reservaId;
    }
    
    public void setDoctorId(String Id) {
        this.doctorId = Id;
    }
   
    public String getDoctorId() {
        return this.doctorId;
    }
      
    public void setPacienteId(String Id) {
        this.pacienteId = Id;
    }
   
    public String getPacienteId() {
        return this.pacienteId;
    }
  
    public void setEspecialidadId(String Id) {
        this.especialidadId = Id;
    }
   
    public String getEspecialidadId() {
        return this.especialidadId;
    }
        
    public void setFechaReservada(String fr) {
        this.fechaReservada = fr;
    }
   
    public String getFechaReservada() {
        return this.fechaReservada;
    }

    public void setFechaRealizacion(String fr) {
        this.fechaRealizacion = fr;
    }
   
    public String getFechaRealizacion() {
        return this.fechaRealizacion;
    }
        
    public void setFechaRealizacionSoloFecha(String fr) {
        this.fechaRealizacionSoloFecha = fr;
    }
   
    public String getFechaRealizacionSoloFecha() {
        return this.fechaRealizacionSoloFecha;
    }
    
    public String getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(String turnoId) {
        this.turnoId = turnoId;
    }

    
    /* ---------------------------------------------
     GETTERS Y SETTERS DE OTROS ATRIBUTOS
     ----------------------------------------------*/    
    public List<Turno> getTurnosDisp() {
        return turnosDisp;
    }

    public void setTurnosDisp(List<Turno> turnosDisp) {
        this.turnosDisp = turnosDisp;
    }
    
    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public List<Doctor> getDoctores() {
        return this.doctores;
    }

    public void setDoctores(List<Doctor> docs) {
        this.doctores = docs;
    }
    
    public void setHorarios(Set<HorarioAtencion> horarios) {
        this.horarios = horarios;
    }
    
    public Set<HorarioAtencion> getHorarios() {
        return horarios;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setFechaReservadaTimestamp(Timestamp fr) {
        this.fechaReservadaTimestamp = fr;
    }
    
    public void setFechaRealizacion(Timestamp fr) {
        this.fechaRealizacionTimestamp = fr;
    }
    
    public Timestamp setFechaReservadaTimestamp() {
        return this.fechaReservadaTimestamp;
    }
    
    public Timestamp setFechaRealizacion() {
        return this.fechaRealizacionTimestamp;
    }
 
    public String getSoloVista(){
        return this.soloVista;
    }

    public void setSoloVista(String vista){
        this.soloVista = vista;
    }
    
    public void getDoctoresFecha(Timestamp fecha){
        DiaDeSemana dia = obtenerDia(fecha);
        this.doctores = this.doctorManager.obtenerPorDia(dia);
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

     /* ---------------------------------------------
     OTROS MÉTODOS
     ----------------------------------------------*/    
   
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
        
        if (id != null) {
            log.debug("EDITANDO...");
            reserva = manager.obtenerReserva(id);
            log.debug("Se obtuvo: " + reserva.getFechaReservadaString());
        } 
        else {
            log.debug("CREANDO NUEVA RESERVA...");
            this.reserva = new Reserva();    
        }
               
        
        // seteamos los atributos que se utilizarán en el formulario de la reserva
        this.doctores = this.doctorManager.obtenerDoctores();        
        log.debug(".::NUEVA RESERVA:doctores:-> "+this.doctores.toString());
        
        this.especialidades = this.especialidadManager.getEspecialidades();
        log.debug(".::NUEVA RESERVA:especialidades:-> "+this.especialidades.toString());

        String user = this.getRequest().getRemoteUser();
        log.debug(".::NUEVA RESERVA:user:-> "+user);
        
        if (this.getRequest().isUserInRole(Constants.PACIENTE_ROLE)) {
            log.debug(".::NUEVA RESERVA:user:-> "+user+" es un PACIENTE");
            this.paciente = this.pacienteManager.getPaciente(user);
            
            log.debug(".::NUEVA RESERVA:paciente.id:-> "+paciente.getId());
            this.reserva.setPaciente(this.paciente);
            
        }

        this.fechaRealizacionTimestamp = new Timestamp(System.currentTimeMillis());
        this.fechaRealizacion = this.fechaRealizacionTimestamp.toString();          
        this.reserva.setFechaRealizacion(this.fechaRealizacionTimestamp);
        
        
        //falta implementar un método para extraer solo la parte de la fecha
        // de la fecha de realizacion para colocar en fechaRealizacionSoloFecha
        
        this.fechaRealizacionSoloFecha = this.fechaRealizacion;
        
        log.debug(".::NUEVA RESERVA:reserva.fechaRealizacion:-> "+reserva.getFechaRealizacionString());
        
        return SUCCESS;
    }

    public String saveReserva() throws Exception {
        if (cancel != null)
            return CANCEL;

        if (delete != null)
            return delete();
        
        log.debug("TRATANDO DE OBTENER EL ID DE RESERVA");
        if(reserva == null){
            log.debug("LA RESERVA ES NULA.....");
        }
        boolean isNew = (reserva.getId() == null);
        
        log.debug("GUARDANDO...");
        log.debug("1.1-)Se obtuvo: " + reserva.getFechaReservadaString() + "--" +this.getRequest().getParameter("fechaReservadaTimestamp"));
        
        /*
         * Se recupera la reserva modificada debido a que el form (del edit)
         * no envia los datos del Doctor y del Paciente.
         * 
         * Se setean los datos a guardar en la reserva
         */
        
        // 1. obtenemos el paciente de la reserva
        Long pacienteIdLong = Long.parseLong(this.getPacienteId());
        this.paciente = this.pacienteManager.getPaciente(pacienteIdLong);       
        reserva.setPaciente(this.paciente);
        
        // 2. obtenemos el turno de la reserva
        Long turnoIdLong = Long.parseLong(this.getTurnoId());
        this.turno = this.turnoManager.getTurno(turnoIdLong);
        reserva.setTurno(this.turno);
        
        // 3. seteamos las fechas (de realización y de reserva)        
        this.fechaRealizacionTimestamp = new Timestamp(System.currentTimeMillis());
        reserva.setFechaRealizacion(this.fechaRealizacionTimestamp);
                
        //this.fechaReservadaTimestamp = new Timestamp()
        reserva.setFechaReservada(this.fechaReservadaTimestamp);  
       
        // Se guarda la reserva modificada.
        manager.guardarReserva(reserva);
        
        String key = (isNew) ? "reserva.added" : "reserva.updated";
        saveMessage(getText(key));
        
        if (!isNew)
            return INPUT;
        else
            return SUCCESS;
    }

    public String actualizarTurnos(){
        
        
        //String doctorIdString = this.getRequest().getParameter("doctoresSelect.value");
        String fechaString = this.getRequest().getParameter("fechaReservadaTimestamp");
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
    
    public Timestamp convertirFecha(String f){
        Timestamp retorno = null;
        String[] fecha = f.split("/");
        retorno.setDate(Integer.parseInt(fecha[0].trim()));
        retorno.setMonth(Integer.parseInt(fecha[1].trim()));
        retorno.setYear(Integer.parseInt(fecha[2].trim())-1900);
        
        return retorno;
    }

        private DiaDeSemana obtenerDia(Timestamp fecha) {
    /*    DiaDeSemana dia = null;
                
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
     */

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        int dia = calendario.get(Calendar.DAY_OF_WEEK);

        switch (dia) {
            case Calendar.SUNDAY:
                return DiaDeSemana.DOMINGO;
            case Calendar.MONDAY:
                return DiaDeSemana.LUNES;
            case Calendar.TUESDAY:
                return DiaDeSemana.MARTES;
            case Calendar.WEDNESDAY:
                return DiaDeSemana.MIERCOLES;
            case Calendar.THURSDAY:
                return DiaDeSemana.JUEVES;
            case Calendar.FRIDAY:
                return DiaDeSemana.VIERNES;
            case Calendar.SATURDAY:
                return DiaDeSemana.SABADO;
        }
        return null;
    }
    
}
