/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.HorarioAtencionManager;

/**
 *
 * @author Fernando
 */
public class HorarioAtencionActionTest extends BaseActionTestCase {
    
    private HorarioAtencionAction action;


    protected void onSetUpInTransaction() throws Exception {
        log.debug("Realizando SetUp...");
        super.onSetUpInTransaction();
        
        log.debug("Recuperando HorarioAtencionManager...");
        HorarioAtencionManager manager = (HorarioAtencionManager) applicationContext.getBean("horarioAtencionManager");
        DoctorManager doctorManager = (DoctorManager) applicationContext.getBean("doctorManager");

        
        action = new HorarioAtencionAction();
        action.setHorarioAtencionManager(manager);
        action.setDoctorManager(doctorManager);

    }
    
    public void testList() throws Exception {
        log.debug("Probando lista ...");
        action.setDoctorUsername("ghuttemann");
        assertEquals(action.listarHorarios(), HorarioAtencionAction.SUCCESS);
        
        log.debug("Probando cantidad de Horarios de ghuttemann...");
        assertTrue(action.getHorariosDoctor().size() == 3);
        log.debug("Dia 0 es .." + action.getHorariosDoctor().get(0).getDia());
        log.debug("Dia 1 es .." + action.getHorariosDoctor().get(1).getDia());
        log.debug("Dia 2 es .." + action.getHorariosDoctor().get(2).getDia());
        
        log.debug("Hora 0 es .." + action.getHorariosDoctor().get(0).getHoraInicio());
        log.debug("Hora 1 es .." + action.getHorariosDoctor().get(1).getHoraInicio());
        log.debug("Hora 2 es .." + action.getHorariosDoctor().get(2).getHoraInicio());
    }

}
