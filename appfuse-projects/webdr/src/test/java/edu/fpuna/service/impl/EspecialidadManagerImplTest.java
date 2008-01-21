/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.EspecialidadDao;
import edu.fpuna.model.Especialidad;
import org.jmock.Mock;

/**
 *
 * @author Liz
 */
public class EspecialidadManagerImplTest extends BaseManagerMockTestCase{
    private EspecialidadManagerImpl manager = null;
    private Mock dao = null;
    private Especialidad especialidad = null;
    
    protected void setUp() throws Exception {
        dao = new Mock(EspecialidadDao.class);
        manager = new EspecialidadManagerImpl((EspecialidadDao) dao.proxy());
    }
    
    protected void tearDown() throws Exception {
        manager = null;
    }
    
    public void testGetEspecialidad() {
        log.debug("testing getEspecialidad");
        Long id = -1L;
        especialidad = new Especialidad();
        // set expected behavior on dao
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(especialidad));
        Especialidad result = manager.get(id);
        assertSame(especialidad, result);
    }
}
