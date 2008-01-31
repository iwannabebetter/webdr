/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.Constants;
import edu.fpuna.model.Paciente;
import edu.fpuna.model.TipoSangre;
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
    
    public void eliminar(Paciente p){
        manager.eliminarPaciente(p);
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
        manager.eliminarPaciente(paciente);
        saveMessage(getText("paciente.deleted"));
        return SUCCESS;
    }
    
    public String edit() {
        tipoSangres = tipoSangreManager.getAll();
        
        if (id != null){
            paciente = manager.getPaciente(id);
            paciente.setConfirmPassword(paciente.getPassword());
        }
        else{
            paciente = new Paciente();
        }
        return SUCCESS;
    }
    
    public String save() throws Exception {
        tipoSangres = tipoSangreManager.getAll();
        if (cancel != null)
            return CANCEL;

        if (delete != null)
            return delete();

        boolean isNew = (paciente.getId() == null);
        paciente.getRoles().clear();
        
        paciente.addRole(roleManager.getRole(Constants.ID_USER_ROLE));
        paciente.addRole(roleManager.getRole(Constants.ID_PACIENTE_ROLE));
        paciente = manager.guardarPaciente(paciente);
        String key = (isNew) ? "paciente.added" : "paciente.updated";
        saveMessage(getText(key));

        if (!isNew){
            paciente.setConfirmPassword(paciente.getPassword());
            return INPUT;
        }else{
            return SUCCESS;
        }
    }
}
