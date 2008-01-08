/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.service.impl;

import edu.fpuna.dao.EspecialidadDao;
import edu.fpuna.model.Especialidad;
import edu.fpuna.service.EspecialidadManager;
import edu.fpuna.service.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Liz
 */
public class EspecialidadManagerImpl extends GenericManagerImpl<Especialidad, Long> implements 
        EspecialidadManager {
 
    private EspecialidadDao dao;

    public EspecialidadManagerImpl(EspecialidadDao especialidadDao) {
        super(especialidadDao);
        this.dao = especialidadDao;
    }

    public Especialidad obtenerPorNombre(String lastName) {
        return dao.obtenerPorNombre(lastName);
    }

    public void setEspecialidadDao(EspecialidadDao dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Especialidad> getAll() {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Especialidad getEspecialidad(String name) {
        return dao.obtenerPorNombre(name);
    }

    /**
     * {@inheritDoc}
     */
    public void saveEspecialidad(Especialidad esp) {
        dao.guardar(esp);
    }

    /**
     * {@inheritDoc}
     */
    public void removeEspecialidad(String name) {
        dao.eliminar(dao.obtenerPorNombre(name));
    }

    public List getEspecialidades() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
