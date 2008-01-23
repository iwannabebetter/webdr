/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import edu.fpuna.service.PacienteManager;
import edu.fpuna.model.Especialidad;
import edu.fpuna.model.Paciente;
import edu.fpuna.webapp.action.BaseActionTestCase;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Cristhian Parra
 */
public class PacienteActionTest extends BaseActionTestCase {

    private PacienteAction action;

    
    protected void onSetUpBeforeTransaction() throws Exception {
        log.debug("Seteo antes de la transaccion");
        super.onSetUpBeforeTransaction();
        
        log.debug("Recuperando el Manager");
        PacienteManager pacienteManager = (PacienteManager) applicationContext.getBean("pacienteManager");
        
        log.debug("Estableciendo el Manager");
        action = new PacienteAction();
        action.setPacienteManager(pacienteManager);
        
        /** add a test person to the database
        log.debug("Agregando un Paciente");
        Paciente paciente = pacienteManager.getPaciente(username)
        log.debug("Paciente seteado");
        pacienteManager.guardarPaciente(paciente);**/
    }

    public void testSearch() throws Exception {
        log.debug("Probando el listado de pacientes");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        
        log.debug("Recuperando pacientes ");
        assertTrue(action.getPacientes().size() != 0);
        log.debug("Fin Recuperando pacientes ");
        
        
        action.guardar(action.getPacientes().get(0));
        log.debug("Guardar paciente ");
    }
}
