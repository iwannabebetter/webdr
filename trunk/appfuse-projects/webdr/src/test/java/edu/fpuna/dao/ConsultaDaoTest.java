package edu.fpuna.dao;

import edu.fpuna.model.Consulta;
import java.util.List;
/**
 * Clase que realiza las pruebas sobre el DAO de las 
 * Consulta (ConsultaDao)
 * @author mrodas
 */
public class ConsultaDaoTest extends BaseDaoTestCase{
    // Objeto DAO de prueba
    private ConsultaDao consultaDao = null;
    
    public void setConsultaDao(ConsultaDao consultaDao) {
        this.consultaDao = consultaDao;
    }
    
    /*
     * Test nº1. Prueba de busqueda de Consulta por Paciente
     */
    public void testObtenerConsultasPaciente() throws Exception {
        
        log.debug("Consultando Paciente");
        List<Consulta> consultas = consultaDao.obtenerConsultasPaciente("user");
        log.debug(" Esta es la cantidad de Consultas " + consultas.size());
        
        assertTrue(consultas.isEmpty() != true);
    }

    /*
     * Test nº2. Prueba de busqueda de Consulta por Doctor
     */
    public void testObtenerConsultasDoctor() throws Exception {
        
        log.debug("Consultando Doctor");
        List<Consulta> consultas = consultaDao.obtenerConsultasDoctor("admin");
        log.debug(" Esta es la cantidad de Consultas " + consultas.size());
        
        assertTrue(consultas.isEmpty() != true);
    }
}
