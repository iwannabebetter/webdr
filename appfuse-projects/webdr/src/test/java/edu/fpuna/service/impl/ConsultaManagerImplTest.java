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
    
    /**
     * Metodo para inicializar el dao y el manager.
     * @throws java.lang.Exception
     */
    protected void setUp() throws Exception {
        dao = new Mock(ConsultaDao.class);
        manager = new ConsultaManagerImpl((ConsultaDao) dao.proxy());
    }
    
    /**
     * Destructor
     * @throws java.lang.Exception
     */
    protected void tearDown() throws Exception {
        manager = null;
    }
    
    /**
     * Test de la función get()
     */
    public void testGet() {
        log.debug("Testing Get...");
        Long id = Long.valueOf(""+ (-1) + "");
        consulta = new Consulta();
        
        // Se establece el comportamiento del DAO
        dao.expects(once()).method("get").with(eq(id)).will(returnValue(consulta));
        
        // Se establece el procesamiento del Manager
        Consulta result = manager.get(id);
        
        // Se compara el funcionamiento del DAO con el Manager
        assertSame(consulta, result);
        log.debug("Testing Get ha Finalizado.");
        
    }
    
    /**
     * Test de la función getConsulta()
     */
    public void testGetConsulta() {
        log.debug("Testing GetConsulta...");
        long id = -1;
        consulta = new Consulta();
        // Se establece el comportamiento del DAO
        dao.expects(once()).method("obtenerConsultaId").with(eq(id)).will(returnValue(consulta));
        
        // Se establece el procesamiento del Manager
        Consulta result = manager.getConsulta(id);
        assertSame(consulta, result);
        log.debug("Testing GetConsulta ha Finalizado.");
    }
    
}
