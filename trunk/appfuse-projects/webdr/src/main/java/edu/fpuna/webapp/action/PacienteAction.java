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
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Cristhian Parra
 */
public class PacienteAction extends EditAccessAction {

    private GenericManager<TipoSangre,Long> tipoSangreManager;
    private List<TipoSangre> tipoSangres;
    private PacienteManager manager;
    private List<Paciente> pacientes;
    private Paciente paciente;
    private Long id;
    private Boolean viewHistorial;
    
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
        pacientes = manager.obtenerTodos();
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
    
    public Boolean getViewHistorial() {
        return viewHistorial;
    }
    
    private void setViewHistorial() {
        if (getRequest().isUserInRole(Constants.DOCTOR_ROLE))
            viewHistorial = true;
        else
            viewHistorial = false;
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

        if (!isNew) {
            paciente.setConfirmPassword(paciente.getPassword());
            return INPUT;
        }
        else {
            return SUCCESS;
        }
    }
    
    @Override
    protected void setEditAccess() {
        HttpServletRequest req = getRequest();
        boolean hasAccess = req.isUserInRole(Constants.ADMIN_ROLE) ||
                            req.isUserInRole(Constants.DOCTOR_ROLE);
        
        if (hasAccess)
            editAccess = true;
        else
            editAccess = false;
    }
    
    @Override
    public void prepare() {
        setEditAccess();
        setDeleteAccess();
        setViewHistorial();
    }
}
