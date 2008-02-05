/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.DoctorDao;
import edu.fpuna.dao.EspecialidadDao;
import edu.fpuna.dao.HorarioAtencionDao;
import edu.fpuna.model.DiaDeSemana;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import java.sql.Time;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.acegisecurity.providers.dao.DaoAuthenticationProvider;
import org.jmock.Mock;

/**
 *
 * @author fmancia
 */
public class HorarioAtencionManagerImplTest extends BaseManagerMockTestCase{
    private HorarioAtencionManagerImpl manager = null;
    private DoctorManagerImpl managerDoctor = null;
    
    private Mock dao = null, doctorDao = null, espDao =null;
    private HorarioAtencion horarioAtencion = null;
    
    EspecialidadManagerImpl espManager;
    
    protected void setUp() throws Exception {
        dao = new Mock(HorarioAtencionDao.class);
        doctorDao = new Mock(DoctorDao.class);
        
        espDao = new Mock(EspecialidadDao.class);
        espManager = new EspecialidadManagerImpl((EspecialidadDao) espDao.proxy());
        
        log.debug("Antes de instanciar");
        managerDoctor = new DoctorManagerImpl((DoctorDao) doctorDao.proxy(), null);
        log.debug("Despues de instanciar");
        
        manager = new HorarioAtencionManagerImpl((HorarioAtencionDao) dao.proxy());
        manager.setDoctorManager(managerDoctor);
    }
    
    public void testGetHorarioAtencion() {
        log.debug("testing getHorarioAtencion");
        
        /**
        List<HorarioAtencion> result = manager.getHorarioAtencion("ghuttemann");
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(especialidad));
        assertTrue(result.isEmpty());
         */
        
        log.debug("testing Antes de obtener al doctor ");
        Doctor doctor = doctorDao.obtenerDoctorPorNombre("ghuttemann");
        log.debug("testing Despues Obtenemos el doctor " +doctor.getFirstName());
        
        
        HorarioAtencion h = new HorarioAtencion();
        h.setDia(DiaDeSemana.MARTES);
        h.setDoctor(doctor);
        
        
        h.setHoraFin(Time.valueOf("03:00:00"));
        h.setHoraInicio(Time.valueOf("12:00:00"));
       
        Turno t1 = new Turno();
        t1.setHora(Time.valueOf("03:00:00"));
        Turno t2 = new Turno();
        t2.setHora(Time.valueOf("04:30:00"));        
        Turno t3 = new Turno();
        t3.setHora(Time.valueOf("10:30:00"));
        
        Set<Turno> listTurnos = new HashSet<Turno>();
        listTurnos.add(t1);
        listTurnos.add(t2);
        listTurnos.add(t3);
        
        h.setTurnos(listTurnos);
        
        log.debug("testing Antes de Guardar " +doctor.getFirstName());
        HorarioAtencion hnew = manager.guardar(h);
        assertTrue(hnew.getTurnos().size() == 3);
    }

    
}
