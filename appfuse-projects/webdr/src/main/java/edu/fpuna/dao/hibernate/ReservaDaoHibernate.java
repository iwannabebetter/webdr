/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao.hibernate;

import edu.fpuna.Constants;
import edu.fpuna.dao.ReservaDao;
import edu.fpuna.model.Reserva;
import edu.fpuna.model.Role;
import edu.fpuna.model.Turno;
import edu.fpuna.model.User;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cristhian Parra
 */
public class ReservaDaoHibernate 
             extends GenericDaoHibernate<Reserva, Long> implements ReservaDao {


    public ReservaDaoHibernate() {
        super(Reserva.class);
    }
    
    public Reserva cancelar(Reserva reserva) {
        reserva.setCancelado(true);        
        return super.save(reserva);
    }

    public Reserva getReserva(Long id) {
        
        String query = "from Reserva where id=?";
        List result = super.getHibernateTemplate().find(query, id);
        
        Reserva retorno = null;
        if (result.size() > 1)
            log.debug("##ERROR## Reserva duplicada.");
        else
            retorno = (Reserva) result.get(0);
    
        log.debug("--> Busqueda Finalizada." + retorno.getFechaReservadaString());
        return retorno;
    }

    public List<Reserva> getReservas() {
        log.debug("--> Buscando Todas las Reservas...");
        
        
        String query = "from Reserva";
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query);

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    public List<Reserva> getReservasDoctor(String username) {
        log.debug("--> Buscando Reservas por Doctor: " + username + "...");
        
        User doctor = this.obtenerUsuario(username);
        String query = "from Reserva where doctor.id=?";
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, doctor.getId());

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    public List<Reserva> getReservasPendientesDr(String username) {
        log.debug("--> Buscando Reservas (Pendientes) por Doctor: " + username + "...");
        
        User doctor = this.obtenerUsuario(username);
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        // ver como incluir el tema de la fecha reservada
        String query = "from Reserva where doctor.id = ? and cancelado=false " +
                       "     and fechareservada >= ?";
        
        Object [] args = { doctor.getId(), now };
        
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, args );

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    public List<Reserva> getReservasPaciente(String username) {
        
        log.debug("--> Buscando Reservas por Paciente: " + username + "...");
        
        User paciente = this.obtenerUsuario(username);
        String query = "from Reserva where paciente.id=?";
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, paciente.getId());

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    public List<Reserva> getReservasPendientesPac(String username) {
        
        log.debug("--> Buscando Reservas (Pendientes) por Doctor: " + username + "...");
        
        User paciente = this.obtenerUsuario(username);
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        // ver como incluir el tema de la fecha reservada
        String query = "from Reserva where paciente.id = ? and cancelado=false " +
                       "     and fechareservada >= ?";
        
        Object [] args = { paciente.getId(), now };
        
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, args );

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    public List<Reserva> obtenerReservasFecha(Timestamp fechaInicio, Timestamp fechaFin) {
        
        String query = "from Reserva where cancelado=false " +
                       "     and fechareservada between ? and ?";
        
        Object [] args = {  fechaInicio, fechaFin };
        
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, args );

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }
    
    public List<Reserva> obtenerReservasFechaDr(String username,Timestamp fechaInicio, Timestamp fechaFin) {
                
        log.debug("--> Buscando Reservas de Doctor en un rango de Fecha: " + username + "...");
        
        User doctor = this.obtenerUsuario(username);
        
        String query = "from Reserva where doctor.id = ? and cancelado=false " +
                       "     and fechareservada between ? and ?";
        
        Object [] args = { doctor.getId(), fechaInicio, fechaFin };
        
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, args );

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }

    public List<Reserva> obtenerReservasFechaPac(String username,Timestamp fechaInicio, Timestamp fechaFin) {
                
        log.debug("--> Buscando Reservas de Doctor en un rango de Fecha: " + username + "...");
        
        User paciente = this.obtenerUsuario(username);
        
        String query = "from Reserva where doctor.id = ? and cancelado=false " +
                       "     and fechareservada between ? and ?";
        
        Object [] args = { paciente.getId(), fechaInicio, fechaFin };
        
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, args );

        log.debug("--> Busqueda Finalizada.");
        
        return result;
    }
    
    public boolean isTurnoDisponible(Turno turno, Timestamp fecha) {
                
        log.debug("--> Verificando disponibilidad del Turno: " + turno.toString() + "...");
        
        String query = "from Reserva where turno.id    = ?        " +
                       "     and day(fechareservada)   = day(?)   " +
                       "     and month(fechareservada) = month(?) " +
                       "     and year(fechareservada)  = year(?)  ";
        
        Object [] args = { turno.getId(), fecha, fecha, fecha };
        
        List<Reserva> result = super.getHibernateTemplate()
                                     .find(query, args );

        log.debug("--> Busqueda Finalizada.");
        
        return result.isEmpty();
    }
    
    public Reserva guardar(Reserva p) {
        
        getHibernateTemplate().saveOrUpdate(p);
        getHibernateTemplate().flush();
        getHibernateTemplate().clear();
        
        return p;
    }

    public void eliminar(Reserva p) {
        super.remove(p.getId());
    }
    
    /*
     * Dado un usuario, revisa los roles que tiene
     * y retorna el rol si es Doctor o Paciente.
     * En caso de no encontrar uno de estos roles,
     * se retorna null.
     * Se asume que no existen simultáneamente los 
     * dos roles mencionados más arriba.
     */
    private String obtenerRol(User usuario) {
        String dRole = Constants.DOCTOR_ROLE;
        String pRole = Constants.PACIENTE_ROLE;
        
        for (Role role : usuario.getRoles()) {
            String rName = role.getName();
            if (rName.equals(dRole) || rName.equals(pRole))
                return rName;
        }
        
        return null;
    }
    
    /*
     * Obtiene un usuario por el nombre de usuario
     */
    private User obtenerUsuario(String username) {
        log.debug("EMPEZAMOS LA CONSULTA SOBRE APPUSER.....");
        
        String query = "from User where username=?";
        List<User> list = getHibernateTemplate().find(query, username);
        
        log.debug("PASAMOS LA CONSULTA SOBRE APPUSER.....");
        
        if (list.isEmpty()) {
            log.fatal("# " + username + " NO EXISTE EN APPUSER.");
            return null;
        }
        else {
            log.debug("# " + username + " recuperado...");
            return list.get(0);
        }
    }

}
  
