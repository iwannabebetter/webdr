/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.webapp.action;

import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import edu.fpuna.service.DoctorManager;
import edu.fpuna.service.HorarioAtencionManager;
import edu.fpuna.service.TurnoManager;
import java.util.Iterator;
import java.util.Set;

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
        assertEquals(action.listPorDoctor(), HorarioAtencionAction.SUCCESS);
        
        log.debug("Probando cantidad de Horarios de ghuttemann..." + action.getHorariosDoctor().size());
        assertTrue(action.getHorariosDoctor().size() >= 3);
        log.debug("Dia 0 es .." + action.getHorariosDoctor().get(0).getDia());
        log.debug("Dia 1 es .." + action.getHorariosDoctor().get(1).getDia());
        log.debug("Dia 2 es .." + action.getHorariosDoctor().get(2).getDia());

        
        log.debug("Hora 0 es .." + action.getHorariosDoctor().get(0).getHoraInicio());
        log.debug("Hora 1 es .." + action.getHorariosDoctor().get(1).getHoraInicio());
        log.debug("Hora 2 es .." + action.getHorariosDoctor().get(2).getHoraInicio());

    }
    
    public void testCrearTurnos() throws Exception {
        log.debug("Probando crear Turno...");
        
        HorarioAtencionManager manager = (HorarioAtencionManager) 
                applicationContext.getBean("horarioAtencionManager");
        TurnoManager tmanager = (TurnoManager) 
                applicationContext.getBean("turnoManager");
        
        action.setHorarioAtencionManager(manager);
        action.setTurnoManager(tmanager);                 
        HorarioAtencion horarioAtencion = manager.getHorarioAtencion(-1L);
        action.setHorarioAtencion(horarioAtencion);
        
        log.debug("Hora de Atención a dividir en turnos de 60 minutos");
        log.debug("Hora Inicio: "+
                action.getHorarioAtencion().getHoraInicio().getTime());
        log.debug("Hora Inicio String: "+
                action.getHorarioAtencion().getHoraInicioString());
        
        log.debug("Hora Fin: "+
                action.getHorarioAtencion().getHoraFin().getTime());
        log.debug("Hora Fin String: "+
                action.getHorarioAtencion().getHoraFinString());
        
        float cantTurnos = (action.getHorarioAtencion().getHoraFin().getTime() - 
                action.getHorarioAtencion().getHoraInicio().getTime())/(60*60*1000);
        
        log.debug("Cantidad de Turnos a crear: "+cantTurnos);
        
        action.crearTurnos(60);
        
        horarioAtencion = manager.getHorarioAtencion(horarioAtencion.getId());
        
        action.setHorarioAtencion(horarioAtencion);
        
        /*log.debug("Probando cantidad de turnos creado... "+
                action.getHorarioAtencion().getTurnos().size());
        */
        
        log.debug("Probando cantidad de turnos creado... "+
                manager.get(-1L).getTurnos().size());
        
                
        assertTrue(manager.get(-1L).getTurnos().size() == 7);
        
        
        Set<Turno> turnos = action.getHorarioAtencion().getTurnos();
        
        Iterator<Turno> it = turnos.iterator();
        
        int i = 0;
        while(it.hasNext()) {
            i++;
            log.debug("Turno "+i+" es ..." + it.next().getHora().toString());            
        }
        
        

    }

}
