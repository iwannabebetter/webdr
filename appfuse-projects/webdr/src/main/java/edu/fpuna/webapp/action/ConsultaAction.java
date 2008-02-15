/*
 * ConsultaAction.java
 *
 * Created on 17 de enero de 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import edu.fpuna.model.Consulta;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Paciente;
import edu.fpuna.service.ConsultaManager;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.PacienteManager;
import java.util.Date;
import java.util.List;

/**
 * Action de la clase Consulta.
 * @author Hugo Meyer, Marcelo Rodas
 */
public class ConsultaAction extends BaseAction {

    private ConsultaManager manager;
    private DoctorManager doctorManager;
    private PacienteManager pacienteManager;
    private Consulta consulta;
    private List<Consulta> consultas;
    private Long id;
    private String userNameDoctor;
    private Date fechaInicio;
    private Date fechaFin;
    private String soloVista;
    private String pacienteId;
    

    public void setConsultaManager(ConsultaManager consultaManager) {
        this.manager = consultaManager;
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
    
    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }
    
    public String getPacienteId() {
        return this.pacienteId ;
    }
    
    public void setUserNameDoctor(String username) {
        this.userNameDoctor = username;
    }
    
    public String getUserNameDoctor() {
        return this.userNameDoctor;
    }

    public String getFullNameDoctor() {
        String username = getRequest().getRemoteUser();
        Doctor doctor = doctorManager.obtenerDoctorPorNombre(username);
        return doctor.getFullName();
    }
    
    public Consulta getConsulta() {
        return consulta;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setConsulta(Consulta consulta) {
        log.debug("EJECUTANDO 1... "+consulta);
        log.debug("EJECUTANDO 2... "+this.consulta);
        this.consulta = consulta;
    }

    public String listConsultasPaciente() {
        this.soloVista=null;
        String username = this.getRequest().getParameter("username");
		log.debug("--> PACIENTE: "+username);
        consultas = manager.obtenerConsultasPaciente(username);
        return SUCCESS;
    }

    public String listConsultasDoctor() {
        this.soloVista="ok";
        String username = this.getRequest().getRemoteUser();
        consultas = manager.obtenerConsultasDoctor(username);
        return SUCCESS;
    }

    public String listConsultasDoctorEdit() {
        this.soloVista=null;
        String username = this.getRequest().getRemoteUser();
        consultas = manager.obtenerConsultasDoctor(username);
        return SUCCESS;
    }
    
    public String listConsultasFecha(Date fechaInicio, Date fechaFin) {
        try {
            this.soloVista=null;
            String username = this.getRequest().getRemoteUser();
            consultas = manager.obtenerConsultasFecha(username, fechaInicio, fechaFin);
            return SUCCESS;
        } catch (Exception ex) {
            log.fatal(ex);
            saveMessage(getText("consulta.invalidUserType"));
            return ERROR;
        }
    }
    
    public List getConsultas() {
        return consultas;
    }
 
    public String getSoloVista(){
        return this.soloVista;
    }

    public String list() {
        this.soloVista=null;
        consultas = manager.getAll();
        return SUCCESS;
    }

    /* Delete, Editar, Guardar*/
    public String delete() {
        manager.remove(consulta.getId());
        saveMessage(getText("consulta.deleted"));
        return SUCCESS;
    }

    public String edit() {
        String username = this.getRequest().getRemoteUser();
        this.setUserNameDoctor(username);
        if (id != null) {
            log.debug("EDITANDO...");
            consulta = manager.obtenerConsulta(id);
            log.debug("Se obtuvo: " + consulta.getDoctor().getEmail());
        } 
        else {
            
            consulta = new Consulta();
        }

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null)
            return CANCEL;

        if (delete != null)
            return delete();

        boolean isNew = (consulta.getId() == null);
        
        log.debug("ConsultaAction: GUARDANDO...");
        /*
         * Se recupera la consulta modificada debido a que el form (del edit)
         * no envia los datos del Doctor y del Paciente.
         */
        String username = this.getRequest().getRemoteUser();
        this.setUserNameDoctor(username);
        
        Paciente paciente = null;
        if(isNew){
            paciente = this.pacienteManager.getPaciente(pacienteId);
        }else{
            paciente = this.pacienteManager.getPaciente(consulta.getPaciente().getId());
        }
        
        Doctor doctor = this.doctorManager.obtenerDoctorPorNombre(username);

        consulta.setDoctor(doctor);
        consulta.setPaciente(paciente);

        // Se guarda la consulta modificada.
        manager.guardarConsulta(consulta);
        
        String key = (isNew) ? "consulta.added" : "consulta.updated";
        saveMessage(getText(key));
        
        if (!isNew)
            return INPUT;
        else
            return SUCCESS;
    }
}
