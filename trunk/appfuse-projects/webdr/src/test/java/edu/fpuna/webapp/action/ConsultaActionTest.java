/*
 * ConsultaActionTest.java
 *
 * Created on 17 de enero de 2008, 8:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import org.apache.struts2.ServletActionContext;
import edu.fpuna.service.ConsultaManager;
import edu.fpuna.model.Consulta;
import edu.fpuna.model.Doctor;
import edu.fpuna.webapp.action.BaseActionTestCase;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Hugo Meyer
 */
public class ConsultaActionTest extends BaseActionTestCase {

    private ConsultaAction action;

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        log.debug("Seteo antes de la transaccion");
        super.onSetUpBeforeTransaction();
        
        log.debug("Recuperando el Manager");
        ConsultaManager consultaManager = (ConsultaManager) applicationContext.getBean("consultaManager");
        
        log.debug("Estableciendo el Manager");
        action = new ConsultaAction();
        action.setConsultaManager(consultaManager);
        
        // add a test person to the database
//        log.debug("Agregando una especialidad");
//        Consulta consulta = new Consulta();
//        Date fecha  = new Date();
//        
//        fecha.setTime(10);
//        
//        consulta.setFecha(fecha);
//        consulta.setHoraInicio(fecha);
//        fecha.setTime(54);
//        consulta.setHoraFin(fecha);
//        consulta.getNotas().setSintomas("Muere");
//        consulta.getNotas().setDiagnostico("Moriras...");
//        
//        
//        especialidad.setNombre("Pediatria");
//        especialidad.setDescripcion("Especialidad enfocada al tratamiento de niños");
//        especialidadManager.saveEspecialidad(especialidad);
        
    }

    public void testSearch() throws Exception {
        log.debug("Probando el listado de consultas");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        
        log.debug("Recuperando consultas");
        assertTrue(action.getConsultas().size() >= 1);
        log.debug("Tamaño de consultas" + action.getConsultas().size());
    }
}