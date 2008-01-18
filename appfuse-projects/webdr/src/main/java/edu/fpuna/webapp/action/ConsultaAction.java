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
 *
 * @author Hugo Meyer
 */
public class ConsultaAction extends BaseAction{

    private ConsultaManager manager;
    List<Consulta> consultas;
    
    public Consulta getConsulta(long id) {
        return manager.getConsulta(id);
    }

    public void setConsultaManager(ConsultaManager consultaManager) {
        this.manager = consultaManager;
    }
    
    public String getConsultasPaciente(String username) {
        consultas = this.manager.getConsultasPaciente(username);
        return SUCCESS;
    }
    
    public String getConsultasDoctor(String username) {
        consultas = this.manager.getConsultasDoctor(username);
        return SUCCESS;
    }  
    
    public String getConsultasFecha(Date fecha) {
        consultas = this.manager.getConsultasFecha(fecha);
        return SUCCESS;
    } 
    

    public List getConsultas() {
        return consultas;
    } 
    
    public String list() {
        consultas = manager.getAll();
        return SUCCESS;
    }
}
