/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import java.sql.Time;

/**
 *
 * @author Cristhian Parra
 */
public class TurnoDaoTest extends BaseDaoTestCase {
    private TurnoDao turnoDao = null;
    private HorarioAtencionDao horarioAtencionDao = null;
    private DoctorDao doctorDao = null;
    
    public void setTurnoDao(TurnoDao turnoDao) {
        this.turnoDao = turnoDao;
    }

    public void setHorarioAtencionDao(HorarioAtencionDao horarioDao) {
        this.horarioAtencionDao = horarioDao;
    }
    
    public void setDoctorDao(DoctorDao ddao) {
        this.doctorDao = ddao;
    }

    public void testGetTurnoId() throws Exception {
        Turno turno = turnoDao.getTurno(-01L);
        assertTrue(turno != null);
    }
    
    public void testGuardarYEliminarTurno() throws Exception {
        log.debug("Creando turno...");
        Turno turno = new Turno();
        Time hora = new Time(System.currentTimeMillis());
        turno.setHora(hora);
        
        HorarioAtencion horario = obtenerHorario();
        turno.setHorario(horario);
        log.debug("Guardando turno...");
        turno = turnoDao.guardar(turno);
        assertNotNull(turno.getId());
        log.debug("Turno guardado...");
             
        log.debug("Eliminando turno...");
        turnoDao.eliminar(turno);
        flush();
        turno = turnoDao.getTurno(turno.getId());
        assertTrue(turno == null);
    }

    private HorarioAtencion obtenerHorario() {
        log.debug("Creando Horario de Atención...");
        HorarioAtencion horario = horarioAtencionDao.get(-1L);
        log.debug("Horario obtenido: "+horario.getDia());
        /*
        HorarioAtencion horario = new HorarioAtencion();      
        horario.setDia(DiaDeSemana.MIERCOLES);
        
        hora = new Time(System.currentTimeMillis()-3600000000L*4);
        horario.setHoraInicio(hora);
        
        hora = new Time(hora.getTime()+3600000000L*4);
        horario.setHoraFin(hora);
        
        horario.setDoctor(this.doctorDao.obtenerPorNombre("ghuttemann"));
        log.debug("Doctor obtenido: "+horario.getDoctor().toString());
        
        log.debug("Guardando horario de Atención...");
        
        horarioAtencionDao.guardar(horario);
        flush();
        */
        return horario;
    }
}
