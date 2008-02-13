/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import com.opensymphony.xwork2.Preparable;
import edu.fpuna.Constants;

/**
 * Action que permite establecer permito de edición
 * sobre objetos.
 * @author ghuttemann
 */
public class EditAccessAction extends BaseAction implements Preparable {
    /**
     * Constante que indica si se permite la edición
     * de algún objeto por el usuario logueado que
     * ejecuta el Action.
     */
    protected Boolean editAccess;
    
    /**
     * Constante que indica si se permite la eliminación
     * de algún objeto por el usuario logueado que
     * ejecuta el Action.
     */
    protected Boolean deleteAccess;
        
    /**
     * Retorna si se tiene permiso o no de edición
     * @return true o false
     */
    public Boolean getEditAccess() {
        return editAccess;
    }
    
    /**
     * Retorna si se tiene permiso o no de eliminación
     * @return true o false
     */
    public Boolean getDeleteAccess() {
        return deleteAccess;
    }
    
    /*
     * Por defecto, solo se da permiso de edición
     * si el usuario está en el rol ADMIN_ROLE.
     * Si no es este el caso, debemos sobrescribir
     * este método para sustituir el rol o agregar
     * otros roles.
     * Se pueden construir distintas reglas lógicas
     * sobre los roles en el que debería estar el
     * usuario logueado. Por ejemplo:
     *    1. isUserInRole(admin) && isUserInRole(doctor)
     *    2. isUserInRole(doctor) || isUserInRole(paciente)
     */
    protected void setEditAccess() {
        if (getRequest().isUserInRole(Constants.ADMIN_ROLE))
            editAccess = true;
        else
            editAccess = false;
    }
    
    /*
     * Por defecto, solo se da permiso de eliminación
     * si el usuario está en el rol ADMIN_ROLE.
     * Si no es este el caso, debemos sobrescribir
     * este método para sustituir el rol o agregar
     * otros roles.
     * Se pueden construir distintas reglas lógicas
     * sobre los roles en el que debería estar el
     * usuario logueado. Por ejemplo:
     *    1. isUserInRole(admin) && isUserInRole(doctor)
     *    2. isUserInRole(doctor) || isUserInRole(paciente)
     */
    protected void setDeleteAccess() {
        if (getRequest().isUserInRole(Constants.ADMIN_ROLE))
            deleteAccess = true;
        else
            deleteAccess = false;
    }
    
    /*
     * Por defecto, este método establece el permiso
     * de edición y eliminación que tiene el usuario 
     * logueado.
     * Si se debe sobrescribir este método y se desea
     * tener el mismo comportamiento, se debe agregar,
     * en la sobrescritura, el código actual del método.
     */
    public void prepare() throws Exception {
        setEditAccess();
        setDeleteAccess();
    }
}
