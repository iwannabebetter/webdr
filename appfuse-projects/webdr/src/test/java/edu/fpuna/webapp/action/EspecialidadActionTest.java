/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fpuna.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import edu.fpuna.service.EspecialidadManager;
import edu.fpuna.model.Especialidad;
import edu.fpuna.webapp.action.BaseActionTestCase;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Cristhian Parra
 */
public class EspecialidadActionTest extends BaseActionTestCase {

    private EspecialidadAction action;

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        log.debug("Seteo antes de la transaccion");
        super.onSetUpBeforeTransaction();
        
        log.debug("Recuperando el Manager");
        EspecialidadManager especialidadManager = (EspecialidadManager) applicationContext.getBean("especialidadManager");
        
        log.debug("Estableciendo el Manager");
        action = new EspecialidadAction();
        action.setEspecialidadManager(especialidadManager);
        
        // add a test person to the database
        log.debug("Agregando una especialidad");
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre("Pediatria");
        especialidad.setDescripcion("Especialidad enfocada al tratamiento de niños");
        especialidadManager.saveEspecialidad(especialidad);
    }

    public void testSearch() throws Exception {
        log.debug("Probando el listado de especialidades");
        assertEquals(action.list(), ActionSupport.SUCCESS);
        
        log.debug("Recuperando especialidad");
        assertFalse(action.getEspecialidad("Pediatria") == null);
    }
}
