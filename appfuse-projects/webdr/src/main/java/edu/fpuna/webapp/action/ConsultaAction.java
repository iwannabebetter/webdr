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
 * @author Hugo Meyer, Marcelo Rodas
 */
public class ConsultaAction extends BaseAction{

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
			consulta = manager.getConsulta(id);
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
		consulta = manager.save(consulta);
		String key = (isNew) ? "consulta.added" : "consulta.updated";
		saveMessage(getText(key));
		if (!isNew) {
			return INPUT;
		} else {
			return SUCCESS;
		}
	}

}
