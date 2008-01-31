package edu.fpuna.service;

import edu.fpuna.dao.ConsultaDao;
import edu.fpuna.model.Consulta;
import java.util.Date;
import java.util.List;

/**
 * Interfaz Manager para Consulta
 * @author Administrador
 */
public interface ConsultaManager extends GenericManager<Consulta, Long> {

    /**
     * Inicializador del atributo DAO
     * @param consultaDao
     */
    public void setConsultaDao(ConsultaDao dao);

    /**
     * Metodo para la obtener una consulta a travez de su id.
     * @param id
     * @return Consulta
     */
    public Consulta obtenerConsulta(Long id);

    /**
     * Metodo para obtener las Consultas de un Paciente.
     * @param username
     * @return List lista de las Consultas.
     */
    public List<Consulta> obtenerConsultasPaciente(String username);
    
    /**
     * Metodo para obtener las Consultas de un Doctor.
     * @param username
     * @return List lista de las Consultas
     */
    public List<Consulta> obtenerConsultasDoctor(String username);

    /**
     * Metodo para obtener las Consultas en una fecha.
     * @param username Paciente o Doctor propietario de las consultas a recuperar.
     * @param fechaInicio Limite inferior del rango de fecha a consultar.
     * @param fechaFin Limite superior del rango de fecha a consultar.
     * @return List<Consulta> lista de las Consultas.
     */
    public List<Consulta> obtenerConsultasFecha(String username, 
                            Date fechaInicio, Date fechaFin) throws Exception;

    /**
     * Metodo para Guardar una Consulta
     * @param consulta consulta a guardar
     */
    public void guardarConsulta(Consulta consulta);

    /**
     * Metodo para eliminar una Consulta.
     * @param id id de Consulta a eliminar.
     */
    public void eliminarConsulta(Long id);
}
