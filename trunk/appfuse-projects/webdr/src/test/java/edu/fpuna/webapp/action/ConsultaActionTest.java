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
import org.apache.struts2.ServletActionContext;
import edu.fpuna.service.ConsultaManager;
import edu.fpuna.model.Consulta;
import edu.fpuna.model.Notas;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Hugo Meyer
 */
public class ConsultaActionTest extends BaseActionTestCase {

    private ConsultaAction action;

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        log.debug("Realizando SetUp...");
        super.onSetUpBeforeTransaction();
        
        log.debug("Recuperando ConsultaManager...");
        ConsultaManager consultaManager = (ConsultaManager) applicationContext.getBean("consultaManager");
        
        log.debug("Estableciendo ConsultaManager...");
        action = new ConsultaAction();
        action.setConsultaManager(consultaManager);
        
        // add a test person to the database
        //log.debug("Agregando una especialidad");
        //Consulta consulta = new Consulta();
        //Date fecha  = new Date();
        //
        //fecha.setTime(10);
        //
        //consulta.setFecha(fecha);
        //consulta.setHoraInicio(fecha);
        //fecha.setTime(54);
        //consulta.setHoraFin(fecha);
        //consulta.getNotas().setSintomas("Muere");
        //consulta.getNotas().setDiagnostico("Moriras...");
        //
        //especialidad.setNombre("Pediatria");
        //especialidad.setDescripcion("Especialidad enfocada al tratamiento de niños");
        //especialidadManager.saveEspecialidad(especialidad);
    }

    public void testSearch() throws Exception {
        log.debug("Listando Consultas...");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        
        assertTrue(action.getConsultas().size() >= 1);
        log.debug("Cantidad de Consultas: " + action.getConsultas().size() + "...");
    }

    /* Pruebas Agregadas para Agregar, Editar y Borrar */

    public void testEdit() throws Exception {
        log.debug("Probando 'edit'...");
        action.setId(-1L);
        assertNull(action.getConsulta());
        assertEquals("success", action.edit());
        assertNotNull(action.getConsulta());
        assertFalse(action.hasActionErrors());
    }

    public void testSave() throws Exception {
        log.debug("Probando 'save'...");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setId(-1L);
        assertEquals("success", action.edit());
        assertNotNull(action.getConsulta());
        // update last name and save
        Notas notas = new Notas("01","02","03","04");
        action.getConsulta().setNotas(notas); // VER
        assertEquals("input", action.save());
        assertEquals(notas, action.getConsulta().getNotas()); //VER
        assertFalse(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());
        assertNotNull(request.getSession().getAttribute("messages"));
    }

    public void testRemove() throws Exception {
        log.debug("Probando 'remove'...");
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletActionContext.setRequest(request);
        action.setDelete("");
        Consulta consulta = new Consulta();
        consulta.setId(-2L);
        action.setConsulta(consulta);
        assertEquals("success", action.delete());
        assertNotNull(request.getSession().getAttribute("messages"));
    }
}
