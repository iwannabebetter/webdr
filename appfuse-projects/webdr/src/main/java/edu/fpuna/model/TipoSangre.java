/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import javax.persistence.*;

/**
 * Clase que representa un determinado tipo de sangre
 * @author ghuttemann
 */
@Entity
@Table(name="tiposangre")
public class TipoSangre extends BaseObject {
    
    private Long id;
    private String nombre;      // required; unique

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable=false,unique=true,length=3)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TipoSangre))
            return false;
        
        final TipoSangre tipo = (TipoSangre) o;
        
        if (this.nombre.equalsIgnoreCase(tipo.getNombre()))
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
