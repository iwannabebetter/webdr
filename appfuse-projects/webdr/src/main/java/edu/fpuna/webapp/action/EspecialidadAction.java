/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Especialidad;
import edu.fpuna.service.EspecialidadManager;
import java.util.List;

/**
 * Action para la clase Especialidad
 * @author Cristhian Parra
 */
public class EspecialidadAction extends EditAccessAction {

    private EspecialidadManager manager;
    List<Especialidad> especialidades;
    Especialidad especialidad;
    Long id;
    
    public Especialidad getEspecialidad() {
        return especialidad;
    }
    
    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
    
    public void setEspecialidadManager(EspecialidadManager especialidadManager) {
        this.manager = especialidadManager;
    }

    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String list() {
        especialidades = manager.getAll();
        return SUCCESS;
    }
    
    public String delete() {
        manager.removeEspecialidad(especialidad.getNombre());
        saveMessage(getText("especialidad.deleted"));
        return SUCCESS;
    }
    
    public String edit() {
        if (id != null)
            especialidad = manager.getEspecialidad(id);
        else
            especialidad = new Especialidad();

        return SUCCESS;
    }

    public String save() throws Exception {
        if (cancel != null)
            return CANCEL;

        if (delete != null)
            return delete();

        boolean isNew = (especialidad.getId() == null);
        especialidad = manager.saveEspecialidad(especialidad);
        String key = (isNew) ? "especialidad.added" : "especialidad.updated";
        saveMessage(getText(key));

        if (!isNew)
            return INPUT;
        else
            return SUCCESS;
    }
}
