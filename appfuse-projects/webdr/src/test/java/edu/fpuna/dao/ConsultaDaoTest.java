package edu.fpuna.dao;

import edu.fpuna.model.Consulta;
import edu.fpuna.model.Doctor;
import edu.fpuna.model.Notas;
import edu.fpuna.model.MedidasPaciente;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Clase que realiza las pruebas sobre el DAO de las 
 * Consulta (ConsultaDao)
 * @author mrodas
 */
public class ConsultaDaoTest extends BaseDaoTestCase {

    // Objeto DAO de prueba
    private ConsultaDao consultaDao = null;
    private DoctorDao doctorDao = null;
    private PacienteDao pacienteDao = null;

    public void setConsultaDao(ConsultaDao consultaDao) {
        this.consultaDao = consultaDao;
    }

    public void setDoctorDao(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    public void setPacienteDao(PacienteDao pacienteDao) {
        this.pacienteDao = pacienteDao;
    }

    /*
     * Test nº1. Prueba de busqueda de una Consulta por su id
     */
    public void testObtenerConsultaId() throws Exception {
        log.debug("Testing(1) ObtenerConsultaId...");

        Consulta consulta = consultaDao.obtenerConsultaId(-1L);

        assertTrue(consulta.getId() == -1);
        log.debug("Testing ObtenerConsultaId ha Finalizado.");
    }

    /*
     * Test nº2. Prueba de busqueda de Consulta por Paciente
     */
    public void testObtenerConsultasPaciente() throws Exception {
        log.debug("Testing(2) ObtenerConsultasPaciente...");

        List<Consulta> consultas = consultaDao.obtenerConsultasPaciente("user");

        assertTrue(consultas.isEmpty() != true);
        log.debug("Cantidad de consultas del Paciente: " + consultas.size());
        log.debug("Testing ObtenerConsultasPaciente ha Finalizado.");
    }

    /*
     * Test nº3. Prueba de busqueda de Consulta por Doctor
     */
    public void testObtenerConsultasDoctor() throws Exception {
        log.debug("Testing(3) ObtenerConsultasDoctor...");

        List<Consulta> consultas = consultaDao.obtenerConsultasDoctor("admin");

        assertTrue(consultas.isEmpty() != true);
        log.debug("Cantidad de consultas del Doctor: " + consultas.size());
        log.debug("Testing ObtenerConsultasDoctor ha Finalizado.");
    }

    /*
     * Test nº4. Prueba de busqueda de Consulta por Fecha
     */
    public void testObtenerConsultasFecha() throws Exception { 
        log.debug("Testing(4) ObtenerConsultasFecha...");
        Date fecha = new Date(85, 5, 5, 0, 0, 0);
        Date fecha2 = new Date(103, 5, 5, 0, 0, 0);
        
        log.debug("Recuperando doctor(id = -2)...");
        Doctor doctor = doctorDao.get(-2L);
        
        log.debug("Recuperando consultas del doctor(id = -2)...");
        List<Consulta> consultas = consultaDao.obtenerConsultasFecha(doctor.getUsername(), fecha, fecha2);
        
        log.debug("Cantidad de consultas del Doctor-fecha: " + consultas.size());
        assertTrue(consultas.isEmpty() != true);
        log.debug("Testing ObtenerConsultasFecha ha Finalizado.");
    }

    /*
     * Test nº5. Prueba de eliminar una consulta
     */
    public void testEliminarConsulta() throws Exception {
        log.debug("Testing(5) EliminarConsulta...");

        consultaDao.remove((long) -1);

        log.debug("Testing EliminarConsulta ha Finalizado.");
    }

    /*
     * Test nº6. Prueba de guardar una consulta
     */
    public void testGuardarConsulta() throws Exception {
        log.debug("Testing(6) GuardarConsulta...");

        Consulta consulta = new Consulta();
        consulta.setId(new Long(3));
        consulta.setFechaInicio(new Timestamp(80,10,5,11,30,0,0));
        consulta.setFechaFin(new Timestamp(80,10,5,12,30,0,0));
        MedidasPaciente mpacientes = new MedidasPaciente(new Double(100), new Double(200), new Integer(30), new Boolean(false));
        consulta.setMedidasPaciente(mpacientes);
        consulta.setNotas(new Notas("Irritaciones", "Alergia", "Alergin 100gr", "200ml cada 12hrs"));
        consulta.setDoctor(doctorDao.obtenerPorNombre("admin"));
        consulta.setPaciente(pacienteDao.getPaciente("user"));

        consulta = consultaDao.save(consulta);

        log.debug("Testing GuardarConsulta ha Finalizado.");
    }
}
