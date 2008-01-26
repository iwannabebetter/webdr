/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.service.DoctorManager;
import java.util.List;

/**
 * Action de la clase Doctor.
 * @author ghuttemann
 */
public class DoctorAction extends BaseAction {

    private DoctorManager doctorManager;
    private List doctores;
    
    public void setDoctorManager(DoctorManager doctorManager) {
        this.doctorManager = doctorManager;
    }
    
    public List getDoctores() {
        return doctores;
    }
    
    public String list() {
        doctores = doctorManager.getAll();
        return SUCCESS;
    }
}
