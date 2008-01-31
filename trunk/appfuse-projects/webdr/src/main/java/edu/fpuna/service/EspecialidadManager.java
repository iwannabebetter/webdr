/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service;

import edu.fpuna.model.Especialidad;
import java.util.List;


/**
 * Interfaz Manager para Especialidad
 * @author Liz
 */
public interface EspecialidadManager extends GenericManager<Especialidad, Long> {

    /**
     * {@inheritDoc}
     */
    List<Especialidad> getEspecialidades();

    /**
     * {@inheritDoc}
     */
    Especialidad getEspecialidad(String nombre);

    /**
     * {@inheritDoc}
     */
    Especialidad getEspecialidad(Long id);
    
    /**
     * {@inheritDoc}
     */
    Especialidad saveEspecialidad(Especialidad especialidad);

    /**
     * {@inheritDoc}
     */
    void removeEspecialidad(String nombre);
}
