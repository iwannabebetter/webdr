/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Especialidad;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.TipoSangre;
import edu.fpuna.service.EspecialidadManager;
import edu.fpuna.service.GenericManager;
import edu.fpuna.service.PacienteManager;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public class PacienteAction extends BaseAction {

    private GenericManager<TipoSangre,Long> tipoSangreManager;
    List<TipoSangre> tipoSangres;
    
    private PacienteManager manager;
    List<Paciente> pacientes;

    private Paciente paciente;
    private Long id;
    
    public void setPacienteManager(PacienteManager pacienteManager) {
        this.manager = pacienteManager;
    }
    
    public void setTipoSangreManager(GenericManager<TipoSangre,Long> tipoSangreManager) {
        this.tipoSangreManager = tipoSangreManager;
    }
    
    public Paciente getPaciente(String username) {
        return manager.getPaciente(username);
    }

    public List<Paciente> getPacientes(){
        return pacientes;
    }
    
    public List<TipoSangre> getTipoSangres(){
        return tipoSangres;
    }
    
    public void guardar(Paciente p){
        manager.guardarPaciente(p);
    }
    
    public void borrar(Paciente p){
        manager.borrarPaciente(p);
    }

    public String list() {
        pacientes = manager.getAll();
        return SUCCESS;
    }
    
    public void  setId(Long id){
        this.id = id;
    }
    
    public Paciente getPaciente(){
        return this.paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public String delete() {
        manager.borrarPaciente(paciente);
        saveMessage(getText("paciente.deleted"));
        return SUCCESS;
    }
    
    public String edit() {
        tipoSangres = tipoSangreManager.getAll();
        if (id != null) {
            paciente = manager.getPaciente(id);
        } else {
            paciente = new Paciente();
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
        boolean isNew = (paciente.getId() == null);
        paciente = manager.guardarPaciente(paciente);
        String key = (isNew) ? "paciente.added" : "paciente.updated";
        saveMessage(getText(key));
        if (!isNew) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }
    
    
}

