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
import edu.fpuna.service.ConsultaManager;
import java.util.Date;
import java.util.List;

/**
 * Action de la clase Consulta.
 * @author Hugo Meyer, Marcelo Rodas
 */
public class ConsultaAction extends BaseAction {

    private ConsultaManager manager;
    private Consulta consulta;
    private List<Consulta> consultas;
    private Long id;
    private Date fechaInicio;
    private Date fechaFin;

    public void setConsultaManager(ConsultaManager consultaManager) {
        this.manager = consultaManager;
    }

    public void setId(Long id) {
        this.id = id;
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
        String username = this.getRequest().getRemoteUser();
        consultas = manager.obtenerConsultasPaciente(username);
        return SUCCESS;
    }

    public String listConsultasDoctor() {
        String username = this.getRequest().getRemoteUser();
        consultas = manager.obtenerConsultasDoctor(username);
        return SUCCESS;
    }

    public String listConsultasFecha(Date fechaInicio, Date fechaFin) {
        try {
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

    public String list() {
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
        
        log.debug("GUARDANDO...");
        log.debug("1.1-)Se obtuvo: " + consulta.getFecha() + "--" +this.getRequest().getParameter("consulta.fechaInicio"));
        
        /*
         * Se recupera la consulta modificada debido a que el form (del edit)
         * no envia los datos del Doctor y del Paciente.
         */
        Consulta oldConsulta = manager.obtenerConsulta(consulta.getId());
        log.debug("2.1-)Se obtuvo: " + oldConsulta.getFecha());
        
        // Se actualizan los datos del Doctor y del Paciente.

        consulta.setDoctor(oldConsulta.getDoctor());
        consulta.setPaciente(oldConsulta.getPaciente());

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
