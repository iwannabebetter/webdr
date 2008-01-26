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
    List<Consulta> consultas;
    private Long id;

    public void setConsultaManager(ConsultaManager consultaManager) {
        this.manager = consultaManager;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consulta getConsulta() {
        return this.consulta;
    }

    public void setConsulta(Consulta consulta) {
        log.debug("EJECUTANDO... "+consulta.getId());
        this.consulta = consulta;
    }

    public Consulta getConsulta(Long id) {
        return manager.getConsulta(id);
    }

    public String getConsultasPaciente(String username) {
        consultas = this.manager.getConsultasPaciente(username);
        return SUCCESS;
    }

    public String getConsultasDoctor(String username) {
        consultas = this.manager.getConsultasDoctor(username);
        return SUCCESS;
    }

    public String getConsultasFecha(Date fechaInicio, Date fechaFin) {
        consultas = this.manager.getConsultasFecha(fechaInicio, fechaFin);
        return SUCCESS;
    }
    public List getConsultas() {
        String resultado = this.list();
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
            consulta = manager.getConsulta(id);
            log.debug("Se obtuvo: " + consulta.getDoctor().getEmail());
        } else {
            consulta = new Consulta();
        }
        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null) {
            return "cancel";
        }
        if (delete != null) {
            return delete();
        }
        boolean isNew = (consulta.getId() == null);
        
        this.setId(consulta.getId());
        
        log.debug("GUARDANDO...");
        log.debug("1-)Se obtuvo: " + consulta.getDoctor().getEmail());
        /*
         * Se recupera la consulta modificada debido a que el form (del edit)
         * no envia los datos del Doctor y del Paciente.
         */
        Consulta oldConsulta = manager.getConsulta(consulta.getId());
        log.debug("2-)Se obtuvo: " + oldConsulta.getDoctor().getEmail());
        
        // Se actualizan los datos del Doctor y del Paciente.
        consulta.setDoctor(oldConsulta.getDoctor());
        consulta.setPaciente(oldConsulta.getPaciente());

        // Se guarda la consulta modificada.
        manager.saveConsulta(consulta);
        
        String key = (isNew) ? "consulta.added" : "consulta.updated";
        saveMessage(getText(key));
        if (!isNew) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }
}
