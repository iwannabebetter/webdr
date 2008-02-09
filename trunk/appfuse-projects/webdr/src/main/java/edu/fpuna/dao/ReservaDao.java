/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Reserva;
import edu.fpuna.model.Turno;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public interface ReservaDao extends GenericDao<Reserva, Long> {

    /**
     * M&eacute;todo para cancelar una reserva segun una determinada
     * observaci&oacute;n
     * @param reserva Objeto Reserva a cancelar
     * @param observacionCancelacion Observación relacionada a la cancelación
     */
    public Reserva cancelar(Reserva reserva );

    /**
     * Obtener reservas a partir del ID
     * @param id
     * @return
     */
    public Reserva getReserva(Long id);
    
    /**
     * Obtener todas las reservas del sistema
     * @return
     */
    public List<Reserva> getReservas();
    
    /**
     * Obtener todas las reservas relacionadas a un Doctor
     * @param usernameDoctor
     * @return
     */
    public List<Reserva> getReservasDoctor(String usernameDoctor);
    
    /**
     * Obtener las reservas pendientes del un doctor determinado. 
     * @param usernameDoctor
     * @return
     */
    public List<Reserva> getReservasPendientesDr(String usernameDoctor);
    
    /**
     * Obtener reservas de un paciente determinado
     * @param usernamePaciente
     * @return
     */
    public List<Reserva> getReservasPaciente(String usernamePaciente);
    
    /**
     * Obtner reservas pendientes de un paciente determinado
     * @param usernamePaciente
     * @return
     */
    public List<Reserva> getReservasPendientesPac(String usernamePaciente);
    
    /**
     * Metodo para obtener las Consultas en una fecha dada.
     * @param username Paciente o Doctor propietario de las consultas a recuperar.
     * @param fechaInicio Limite inferior del rango de fecha a consultar.
     * @param fechaFin Limite superior del rango de fecha a consultar.
     * @return List<Consulta> lista de consulta encontradas.
     */
    public List<Reserva> obtenerReservasFecha(Timestamp fechaInicio, Timestamp fechaFin) throws Exception;
    
    public List<Reserva> obtenerReservasFechaDr(String username,Timestamp fechaInicio, Timestamp fechaFin);
   
    public List<Reserva> obtenerReservasFechaPac(String username,Timestamp fechaInicio, Timestamp fechaFin);
    
    public boolean isTurnoDisponible(Turno turno, Timestamp fecha);
    
    public Reserva guardar(Reserva p);
    
    public void eliminar(Reserva p);
    
}
