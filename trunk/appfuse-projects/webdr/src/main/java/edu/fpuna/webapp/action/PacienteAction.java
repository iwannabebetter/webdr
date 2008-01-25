/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.Paciente;
import edu.fpuna.service.PacienteManager;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public class PacienteAction extends BaseAction {

    private PacienteManager manager;
    List<Paciente> pacientes;

    public void setPacienteManager(PacienteManager pacienteManager) {
        this.manager = pacienteManager;
    }
    
    public Paciente getPaciente(String username) {
        return manager.getPaciente(username);
    }

    public List<Paciente> getPacientes(){
        return pacientes;
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
}
