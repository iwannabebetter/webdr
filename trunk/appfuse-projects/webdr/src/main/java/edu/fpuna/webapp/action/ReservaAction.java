/*
 * ReservaAction.java
 *
 * Created on 17 de enero de 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import edu.fpuna.model.Reserva;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.Turno;
import edu.fpuna.service.ConsultaManager;
import edu.fpuna.service.ReservaManager;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.HorarioAtencionManager;
import edu.fpuna.service.PacienteManager;
import edu.fpuna.service.TurnoManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Action de la clase Reserva.
 * @author Hugo Meyer, Marcelo Rodas
 */
public class ReservaAction extends BaseAction {

    /*
     * Managers utilizados en las funcionalidades de Reserva
     */
    private ReservaManager manager;
    private DoctorManager doctorManager;
    private PacienteManager pacienteManager;
    private ConsultaManager consultaManager;
    private HorarioAtencionManager horarioAtencionManager;
    private TurnoManager turnoManager;
    
    /*
     * POJO de Reserva y Listado
     */
    private Reserva reserva;
    private List<Reserva> reservas;
    private List<Turno> turnosDisp;
    private Turno turno;
    
    /* Otras variables importantes */
    private Long id;
    private String userNamePaciente;
    private Timestamp fechaInicio;
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
        this.userNamePaciente = username;
    }
    public String getUserNamePaciente() {
        return this.userNamePaciente;
    }
    
    public Reserva getReserva() {
        return reserva;
    }
    
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
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
    
}
