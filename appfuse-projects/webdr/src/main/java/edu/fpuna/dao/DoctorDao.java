/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.dao;

import edu.fpuna.model.Doctor;
import edu.fpuna.model.Especialidad;
import java.util.List;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interfaz DAO para Doctor.
 * @author ghuttemann
 */
public interface DoctorDao extends GenericDao<Doctor, Long> {
    
    /**
     * Obtiene un doctor por su nombre de usuario.
     * @param username El nombre de usuario del doctor a recuperar.
     * @return El objeto Doctor correspondiente al nombre de usuario.
     */
    public Doctor obtenerPorNombre(String username) throws UsernameNotFoundException;
    
    /**
     * Recupera los doctores con una determinada especialidad
     * @param especialidad La especialidad buscada en los doctores
     * @return Una lista de los doctores que cumplen el criterio
     */
    public List<Doctor> obtenerPorEspecialidad(Especialidad especialidad);
    
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    String getUserPassword(String username);
}
