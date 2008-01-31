/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Consulta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Liz
 */
public interface ConsultaDao extends GenericDao<Consulta, Long>  {

    /**
     * Metodo para obtener las Consultas por idConsulta.
     * @param id Identificador de la consulta.
     * @return La consulta con el identificador pasado.
     */
    public Consulta obtenerConsultaId(Long id) ;
    
    /**
     * Metodo para obtener las Consultas de un Pacinete.
     * @param username Nombre del Paciente.
     * @return List<Consulta> Lista de Consultas del Paciente.
     */
    public List<Consulta> obtenerConsultasPaciente(String username);
    
    /**
     * Metodo para obtener las Consultas de un Doctor.
     * @param username Nombre del Doctor.
     * @return List<Consulta> Lista de Consultas del Doctor.
     */
    public List<Consulta> obtenerConsultasDoctor(String username);
    
    /**
     * Metodo para obtener las Consultas en una fecha dada.
     * @param usuario Paciente o Doctor propietario de las consultas a recuperar.
     * @param fechaInicio Limite inferior del rango de fecha a consultar.
     * @param fechaFin Limite superior del rango de fecha a consultar.
     * @return List<Consulta> lista de consulta encontradas.
     */
    public List<Consulta> obtenerConsultasFecha(String username, 
                            Date fechaInicio, Date fechaFin) throws Exception;

    /**
     * Guarda una consulta.
     * @param consulta La consulta a guardar.
     */
    public Consulta guardar(Consulta consulta);
	  
    /**
     * Elimina una consulta.
     * @param consulta La consulta a eliminar.
     */
    public void eliminar(Consulta consulta);
}
