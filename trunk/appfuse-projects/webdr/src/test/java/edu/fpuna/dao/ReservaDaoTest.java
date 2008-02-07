package edu.fpuna.dao;

import edu.fpuna.model.Reserva;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.dao.DataAccessException;

public class ReservaDaoTest extends BaseDaoTestCase {
    
    private ReservaDao   dao   = null;        
    private DoctorDao    ddao  = null;
    private PacienteDao  pdao  = null;
    private ConsultaDao  cdao  = null;
    
    public void setReservaDao(ReservaDao dao) {
        this.dao = dao;
    }
    
    public void setDoctorDao(DoctorDao ddao) {
        this.ddao = ddao;
    }
    
    public void setPacienteDao(PacienteDao pdao) {
        this.pdao = pdao;
    }

    public ConsultaDao getConsultaDao() {
        return cdao;
    }

    public void setConsultaDao(ConsultaDao cdao) {
        this.cdao = cdao;
    }
    
    public void testGetReservaInvalid() throws Exception {
        try {
            dao.get(1000L);
            fail("'badusername' found in database, failing test...");
        } catch (DataAccessException d) {
            assertTrue(d != null);
        }
    }

    
    /*
     * De la reserva necesitamos: 
     *  - Realizar una reserva (o sea guardar)
     *  - Obtener las reservas por id, paciente, doctor y fecha
     *  - Eliminar una reserva
     *  - Cancelar una reserva
     */
    public void testGetReserva() throws Exception {
        Reserva reserva = dao.get(-1L);
        assertNotNull(reserva);
        assertTrue(reserva.getPaciente().isEnabled());
    }
    
    public void testGuardar() throws Exception {
        log.debug("Iniciando Prueba de Guardar...");
        
        log.debug("Creando nueva Reserva...");
        Reserva reserva = new Reserva();
        reserva.setCancelado(false);
        
        Timestamp fechaRealizacion = new Timestamp(System.currentTimeMillis());
        reserva.setFechaRealizacion(fechaRealizacion);
        reserva.setFechaReservada(fechaRealizacion);       
        reserva.setPaciente(pdao.getPaciente("fmancia"));
        
        log.debug("Guardando nueva Reserva...");
        
        Reserva guardado = dao.guardar(reserva);
        log.debug("Reserva guardada...");
        
        assertNotNull(guardado.getId());        
        assertEquals(reserva.getFechaRealizacion(), guardado.getFechaRealizacion());
        flush();
    }

    public void testCancelar() throws Exception {
        log.debug("Iniciando Prueba de Cancelar...");
        
        Reserva reserva = dao.get(-1L);
        
        log.debug("Cancelando nueva Reserva...");        
        
        String observacionCancelacion = "El doctor XXX realizará un viaje";
        
        reserva.setObservacionCancelacion(observacionCancelacion);
        dao.cancelar(reserva);
        
        log.debug("Reserva Cancelado...");
        
        // se debe volver a obtener reserva?        
        reserva = dao.get(-1L);
        
        assertTrue(reserva.getCancelado());
        
    }
    
    public void testGetReservas() {
        
        log.debug("Iniciando Test Get Reservas...");
        
        List<Reserva> result = dao.getReservas();
        
        assertTrue(result.size() > 0);
        
    }
    
    public void testGetReservasDoctor() {
        
        log.debug("Iniciando Test Get Reservas de Doctor...");
        
    }
        
    public void getReservasPendientesDr() {
        
        log.debug("Iniciando Test Get Reservas Pendientes Dr...");
        
    }
    
    public void getReservasPaciente() {
        
        log.debug("Iniciando Test Get Reservas Paciente...");
        
    }
    
    public void getReservasPendientesPac() {
        
        log.debug("Iniciando Test Get Reservas Pendientes de Paciente...");
        
    }
    
    public void obtenerReservasFecha() throws Exception {
        
        log.debug("Iniciando Test Get Reservas por Fecha...");
        
        Timestamp inicio = new Timestamp(2008, 2, 10, 0, 0, 0,0);
        Timestamp fin = new Timestamp(2008, 2, 12, 0, 0, 0,0);
        
        List<Reserva> result = dao.obtenerReservasFecha( inicio, fin);
        
        assertTrue(result.size() == 2);
        
        
        
    }
    
    
    public void eliminar() {
        
        log.debug("Iniciando Test eliminar Reservas...");
        
    }
    
    
    
    
    
}
