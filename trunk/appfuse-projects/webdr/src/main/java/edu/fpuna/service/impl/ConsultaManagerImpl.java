package edu.fpuna.service.impl;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import edu.fpuna.service.ConsultaManager;
import edu.fpuna.service.*;
import java.util.Date;
import java.util.List;

/**
 * Implementación del Manager para las Consultas.
 * @author mrodas
 */
public class ConsultaManagerImpl extends GenericManagerImpl<Consulta, Long>
        implements ConsultaManager {
 
    // DAO Consulta
    private ConsultaDao consultaDao;

    /**
     * Constructor
     * @param consultaDao
     */
    public ConsultaManagerImpl(ConsultaDao consultaDao) {
        super(consultaDao);
        this.consultaDao = consultaDao;
    }

    /**
     * {@inheritDoc}
     */
    public void setConsultaDao(ConsultaDao consultaDao) {
        this.consultaDao = consultaDao;
    }

    /**
     * {@inheritDoc}
     */
    public Consulta obtenerConsulta(Long id) {
        return consultaDao.obtenerConsultaId(id);
    }

    /**
     * {@inheritDoc}
     */
    public List obtenerConsultasPaciente(String username) {
        return consultaDao.obtenerConsultasPaciente(username);
    }
    
    /**
     * {@inheritDoc}
     */
    public List obtenerConsultasDoctor(String username) {
        return consultaDao.obtenerConsultasDoctor(username);
    }    

    /**
     * {@inheritDoc}
     */
    public List obtenerConsultasFecha(String username, 
                            Date fechaInicio, Date fechaFin) throws Exception {
        
        return consultaDao.obtenerConsultasFecha(username, fechaInicio, fechaFin);
    }    

    /**
     * {@inheritDoc}
     */
    public void guardarConsulta(Consulta consulta) {
        log.debug("Guardando Consulta"+consulta.getId());
        consultaDao.guardar(consulta);
        log.debug("Guardando Consulta ha Terminado.");
    }

    /**
     * {@inheritDoc}
     */
    public void eliminarConsulta(Long id) {
        consultaDao.eliminar(consultaDao.obtenerConsultaId(id));
    }
    
    /**
     * Metodo que obtiene todas las Consultas
     * @return List<Consulta> lista de todas las Consultas
     */
    public List<Consulta> getAll() {
        return consultaDao.getAll();
    }
}
