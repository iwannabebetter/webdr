/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Especialidad;
import edu.fpuna.service.EspecialidadManager;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public class EspecialidadAction extends BaseAction {

    private EspecialidadManager manager;
    List<Especialidad> especialidades;
    
    public Especialidad getEspecialidad(String nombre) {
        return manager.getEspecialidad(nombre);
    }

    public void setEspecialidadManager(EspecialidadManager especialidadManager) {
        this.manager = especialidadManager;
    }

    public String list() {
        especialidades = manager.getAll();
        return SUCCESS;
    }
}
