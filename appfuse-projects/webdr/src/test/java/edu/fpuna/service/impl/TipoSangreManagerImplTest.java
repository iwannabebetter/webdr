/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.GenericDao;
import edu.fpuna.model.TipoSangre;
import org.jmock.Mock;

/**
 *
 * @author Liz
 */
public class TipoSangreManagerImplTest extends BaseManagerMockTestCase {
    private GenericManagerImpl<TipoSangre, Long> manager = null;
    private Mock dao = null;
    private TipoSangre tipoSangre = null;
    
    @Override
    protected void setUp() throws Exception {
        dao = new Mock(GenericDao.class);
        manager = new GenericManagerImpl<TipoSangre, Long>((GenericDao<TipoSangre, Long>) dao.proxy());
    }
    
    @Override
    protected void tearDown() throws Exception {
        manager = null;
    }
    
    public void testGetTipoSangre() {
        log.debug("testing getTipoSangre");
        Long id = -1L;
        tipoSangre = new TipoSangre();
        // set expected behavior on dao
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(tipoSangre));
        TipoSangre result = manager.get(id);
        assertSame(tipoSangre, result);
    }
}
