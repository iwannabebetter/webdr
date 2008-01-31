/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Especialidad;

/**
 * Clase de prueba de EspecialidadDao
 * @author ghuttemann
 */
public class EspecialidadDaoTest extends BaseDaoTestCase {
    private EspecialidadDao especialidadDao = null;
    
    public void setEspecialidadDao(EspecialidadDao especialidadDao) {
        this.especialidadDao = especialidadDao;
    }
    
    public void testObtenerEspecialidadPorNombre() throws Exception {
        Especialidad especialidad = especialidadDao.obtenerPorNombre("Psiquiatra");
        assertTrue(especialidad != null);
    }
    
    public void testGuardarYEliminarEspecialidad() throws Exception {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre("Pediatra");
        especialidad.setDescripcion("Trata los problemas de los niños");
        
        log.debug("Guardando especialidad...");
        especialidadDao.guardar(especialidad);

        
        log.debug("Recuperando especialidad...");
        especialidad = especialidadDao.obtenerPorNombre(especialidad.getNombre());
        assertEquals("Pediatra", especialidad.getNombre());
        assertNotNull(especialidad.getId());
        
        log.debug("Eliminando especialidad...");
        especialidadDao.eliminar(especialidad);
        flush();
        especialidad = especialidadDao.obtenerPorNombre(especialidad.getNombre());
        assertTrue(especialidad == null);
    }
}
