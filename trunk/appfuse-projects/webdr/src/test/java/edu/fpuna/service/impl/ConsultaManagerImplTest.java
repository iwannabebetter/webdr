package edu.fpuna.service.impl;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import org.jmock.Mock;

/**
 * Implementacíón de la Prueba para el Manager de Consulta
 * @author mrodas
 */
public class ConsultaManagerImplTest extends BaseManagerMockTestCase {

    private ConsultaManagerImpl manager = null;
    private Mock dao = null;
    private Consulta consulta = null;
    
    protected void setUp() throws Exception {
        dao = new Mock(ConsultaDao.class);
        manager = new ConsultaManagerImpl((ConsultaDao) dao.proxy());
    }
    
    protected void tearDown() throws Exception {
        manager = null;
    }
    
    public void testGetPerson() {
        log.debug("testing getPerson");
        Long id = Long.valueOf(""+ (-1) + "");
        consulta = new Consulta();
        // set expected behavior on dao
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(consulta));
        Consulta result = manager.get(id);
        assertSame(consulta, result);
    }
    /*
    public void testGetConsulta() {
        log.debug("testing GetConsulta");
        long id = -1;
        consulta = new Consulta();
        // set expected behavior on dao
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(consulta));
        Consulta result = manager.getConsulta(id);
        assertSame(consulta, result);
    } */ 
}
