/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.HorarioAtencionDao;
import edu.fpuna.dao.TurnoDao;
import edu.fpuna.model.HorarioAtencion;
import edu.fpuna.model.Turno;
import java.sql.Time;
import java.util.Iterator;
import java.util.Set;
import org.jmock.Mock;

/**
 *
 * @author Cristhian Parra
 */
public class TurnoManagerImplTest extends BaseManagerMockTestCase {
    private TurnoManagerImpl manager = null;
    private HorarioAtencionManagerImpl hmanager = null;
    private Mock dao = null;
    private Mock hdao = null;
    private Turno turno = null;
    
    @Override
    protected void setUp() throws Exception {
        dao = new Mock(TurnoDao.class);
        hdao = new Mock(HorarioAtencionDao.class);
        
        manager = new TurnoManagerImpl((TurnoDao) dao.proxy());
        hmanager = new HorarioAtencionManagerImpl((HorarioAtencionDao) hdao.proxy());
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        manager = null;
    }
    
    public void testGuardarYEliminar() {
        
        log.debug("Creando turno...");
        Turno t1 = new Turno();
        
        HorarioAtencion horario = new HorarioAtencion();
        
        Time hora = new Time(System.currentTimeMillis());
        t1.setHora(hora);
        
        hdao.expects(once()).method("get")
               .with(eq(-1L)).will(returnValue(horario));
        
        horario = hmanager.get(-1L);
        
        log.debug("Horario de Atencion antes de agregar turno: cantturnos = " +
                horario.getTurnos().size());
        
        t1.setHorario(horario);
        
        log.debug("Guardando turno...");
        
        dao.expects(once()).method("guardar").with(same(t1)).will(returnValue(t1));
        
        t1 = manager.guardar(t1);
        
        //assertNotNull(t1.getId());
        log.debug("Turno guardado...");
        
        log.debug("Turno es ..." + t1.getHora().toString());            
        
        HorarioAtencion ht = new HorarioAtencion();
        
        hdao.expects(once()).method("get")
               .with(eq(-1L)).will(returnValue(ht));
        
        ht = hmanager.get(-1L);
        
        log.debug("Horario de Atencion despues de agregar turno: cantturnos = " +
                ht.getTurnos().size());
        
        
        Set<Turno> turnos = ht.getTurnos();
        
        Iterator<Turno> it = turnos.iterator();
        
        int i = 0;
        while(it.hasNext()) {
            i++;
            log.debug("Turno "+i+" es ..." + it.next().getHora().toString());            
        }
        
        /*
        log.debug("Eliminando turno...");
        manager.remove(turno);

        turno = manager.getTurno(turno.getId());
        assertTrue(turno == null);
*/
        
        /*
        
        log.debug("testing getTipoSangre");
        Long id = -1L;
        tipoSangre = new TipoSangre();
        // set expected behavior on dao
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(tipoSangre));
        TipoSangre result = manager.get(id);
        assertSame(tipoSangre, result);
         */
    }
}
