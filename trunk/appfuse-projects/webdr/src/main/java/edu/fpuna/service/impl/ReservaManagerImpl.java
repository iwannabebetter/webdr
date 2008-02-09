/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.ReservaDao;
import edu.fpuna.model.Reserva;
import edu.fpuna.model.Turno;
import edu.fpuna.service.ReservaManager;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public class ReservaManagerImpl  extends GenericManagerImpl<Reserva, Long>
        implements ReservaManager {
 
    // DAO Reserva
    private ReservaDao reservaDao;

    /**
     * Constructor
     * @param reservaDao
     */
    public ReservaManagerImpl(ReservaDao reservaDao) {
        super(reservaDao);
        this.reservaDao = reservaDao;
    }

    /**
     * {@inheritDoc}
     */
    public void setReservaDao(ReservaDao reservaDao) {
        this.reservaDao = reservaDao;
    }

    /**
     * {@inheritDoc}
     */
    public Reserva obtenerReserva(Long id) {
        return reservaDao.getReserva(id);
    }

    /**
     * {@inheritDoc}
     */
    public List<Reserva> obtenerReservas() {
        return reservaDao.getReservas();
    }
    /**
     * {@inheritDoc}
     */
    public List<Reserva> obtenerReservasPaciente(String username,boolean pendiente) {
        if (pendiente) {
            return reservaDao.getReservasPendientesPac(username);
        }
        return reservaDao.getReservasPaciente(username);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Reserva> obtenerReservasDoctor(String username,boolean pendiente) {
        if (pendiente) {
            return reservaDao.getReservasPendientesDr(username);
        }
        return reservaDao.getReservasDoctor(username);
    }    

    /**
     * {@inheritDoc}
     */
    public List<Reserva> obtenerReservasFecha(
                            Timestamp fechaInicio, Timestamp fechaFin) throws Exception {
        
        return reservaDao.obtenerReservasFecha(fechaInicio, fechaFin);
    }    

    /**
     * {@inheritDoc}
     */
    public Reserva guardarReserva(Reserva reserva) {
        log.debug("Guardando Reserva"+reserva.getId());
        return reservaDao.guardar(reserva);
    }

    /**
     * {@inheritDoc}
     */
    public void eliminarReserva(Long id) {
        reservaDao.eliminar(reservaDao.get(id));
    }
    
    /**
     * Metodo que obtiene todas las Reservas
     * @return List<Reserva> lista de todas las Reservas
     */
    public List<Reserva> getAll() {
        return reservaDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTurnoDisponible(Turno turno, Timestamp fecha) {
        return reservaDao.isTurnoDisponible(turno, fecha);
    }

}
