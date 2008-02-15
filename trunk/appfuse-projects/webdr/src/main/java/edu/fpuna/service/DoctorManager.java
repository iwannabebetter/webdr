/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service;

import edu.fpuna.dao.DoctorDao;
import edu.fpuna.model.DiaDeSemana;
import edu.fpuna.model.Doctor;
import java.util.List;
import org.acegisecurity.userdetails.UsernameNotFoundException;

/**
 * Interfaz Manager para Doctor
 * @author ghuttemann
 */
public interface DoctorManager extends GenericManager<Doctor, Long> {
    
    /**
     * Setea el DAO por dependency injection.
     * @param doctorDao
     */
    public void setDoctorDao(DoctorDao doctorDao);
    
    /**
     * Obtiene un doctor por su nombre de usuario.
     * @param username El nombre de usuario del doctor a recuperar.
     * @return El objeto Doctor correspondiente al nombre de usuario.
     */
    public Doctor obtenerDoctorPorNombre(String username) throws UsernameNotFoundException;
    
    /**
     * Guarda o actualiza un determinado doctor.
     * @param doctor El doctor a guardar o actualizar
     * @return El doctor guardado o actualizado.
     * @throws edu.fpuna.service.UserExistsException
     */
    public Doctor guardarDoctor(Doctor doctor) throws UserExistsException;
    
    /**
     * Elimina un determinado doctor de la base de datos.
     * @param doctor El doctor a eliminar.
     */
    public void eliminarDoctor(Doctor doctor);
    
    /**
     * Obtiene todos los doctores de la base de datos.
     * @return Una lista con los doctores recuperados.
     */
    public List<Doctor> obtenerDoctores();
    
    /**
     * Obtiene los doctores de una determinada especialidad.
     * @param especialidad La especialidad buscada.
     * @return Una lista con los doctores recuperados.
     */
    public List<Doctor> obtenerDoctoresPorEspecialidad(String especialidad);
    
    public List<Doctor> obtenerDoctoresPorEspecialidadLong(Long especialidad);
    
    /**
     * Setea el manager de especialidades, 
     * @param el manager de especialidades, 
     * @return void
     */
    public void setEspecialidadManager(EspecialidadManager especialidadManager);
    
    public List<Doctor> obtenerPorDia(DiaDeSemana dia);
    
}
