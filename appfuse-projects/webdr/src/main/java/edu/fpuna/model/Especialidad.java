/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import javax.persistence.*;

/**
 *
 * @author ghuttemann
 */
@Entity
@Table(name="especialidad")
public class Especialidad extends BaseObject {
    
    private Long id;
    private String nombre;          // required; unique
    private String descripcion;     // required
    
    public Especialidad() {}
    
    public Especialidad(String nombre, String descripcion) {
        this.nombre      = nombre;
        this.descripcion = descripcion;
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable=false,unique=true,length=50)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(nullable=false,unique=true,length=250)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Especialidad))
            return false;

        final Especialidad especialidad = (Especialidad) o;

        if (this.nombre.equals(especialidad.getNombre()))
            return true;
        
        return false;
    }

    @Override
    public int hashCode() {
        if (this.nombre == null)
            return 0;
        
        return this.nombre.hashCode();
    }
}
