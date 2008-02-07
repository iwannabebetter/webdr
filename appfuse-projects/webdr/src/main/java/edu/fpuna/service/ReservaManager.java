/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service;

import edu.fpuna.dao.ReservaDao;
import edu.fpuna.model.Reserva;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public interface ReservaManager {
    /**
     * Inicializador del atributo DAO
     * @param reservaDao
     */
    public void setReservaDao(ReservaDao dao);

    /**
     * Metodo para la obtener una reserva a travez de su id.
     * @param id
     * @return Reserva
     */
    public Reserva obtenerReserva(Long id);

    
    /**
     * Metodo para la obtener una reserva a travez de su id.
     * @param id
     * @return Reserva
     */
    public List<Reserva> obtenerReservas();
    
    /**
     * Metodo para obtener las Reservas de un Paciente.
     * @param username
     * @return List lista de las Reservas.
     */
    public List<Reserva> obtenerReservasPaciente(String username,boolean pendiente);
    
    /**
     * Metodo para obtener las Reservas de un Doctor.
     * @param username
     * @return List lista de las Reservas
     */
    public List<Reserva> obtenerReservasDoctor(String username,boolean pendiente);

    /**
     * Metodo para obtener las Reservas en una fecha.
     * @param username Paciente o Doctor propietario de las reservas a recuperar.
     * @param fechaInicio Limite inferior del rango de fecha a reservar.
     * @param fechaFin Limite superior del rango de fecha a reservar.
     * @return List<Reserva> lista de las Reservas.
     */
    public List<Reserva> obtenerReservasFecha(Timestamp fechaInicio, Timestamp fechaFin) throws Exception;

    /**
     * Metodo para Guardar una Reserva
     * @param reserva reserva a guardar
     */
    public Reserva guardarReserva(Reserva reserva);

    /**
     * Metodo para eliminar una Reserva.
     * @param id id de Reserva a eliminar.
     */
    public void eliminarReserva(Long id);
}
