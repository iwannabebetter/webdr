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
    private ConsultaDao dao;

    /**
     * Constructor
     * @param consultaDao
     */
    public ConsultaManagerImpl(ConsultaDao consultaDao) {
        super(consultaDao);
        this.dao = consultaDao;
    }

    /**
     * Inicializador del atributo DAO
     * @param dao
     */
    public void setConsultaDao(ConsultaDao dao) {
        this.dao = dao;
    }

    /**
     * Metodo para la obtener una consulta a travez de su id.
     * @param id
     * @return Consulta
     */
    public Consulta getConsulta(long id) {
        return dao.obtenerConsultaId(id);
    }

    /**
     * Metodo para obtener las Consultas de un Paciente.
     * @param username
     * @return List lista de las Consultas.
     */
    public List getConsultasPaciente(String username) {
        return dao.obtenerConsultasPaciente(username);
    }
    
    /**
     * Metodo para obtener las Consultas de un Doctor.
     * @param username
     * @return List lista de las Consultas
     */
    public List getConsultasDoctor(String username) {
        return dao.obtenerConsultasDoctor(username);
    }    

    /**
     * Metodo para obtener las Consultas en una fecha.
     * @param fecha
     * @return List lista de las Consultas.
     */
    public List getConsultasFecha(Date fecha) {
        return dao.obtenerConsultasFecha(fecha);
    }    

    /**
     * Metodo para Guardar una Consulta
     * @param consulta consulta a guardar
     */
    public void saveConsulta(Consulta consulta) {
        dao.guardar(consulta);
    }

    /**
     * Metodo para eliminar una Consulta.
     * @param id id de Consulta a eliminar.
     */
    public void removeConsulta(long id) {
        dao.eliminar(dao.obtenerConsultaId(id));
    }
    
    /**
     * Metodo que obtiene todas las Consultas
     * @return List<Consulta> lista de todas las Consultas
     */
    public List<Consulta> getAll() {
        return dao.getAll();
    }
    
}
