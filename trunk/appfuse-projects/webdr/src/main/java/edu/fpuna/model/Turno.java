/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import java.util.Date;
import javax.persistence.*;

/**
 * Clase que representa un turno dentro de un
 * horario de atención del doctor.
 * @author ghuttemann
 */
@Entity
@Table(name="turno")
public class Turno extends BaseObject {
    
    private Long id;
    private Date hora;

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="hora",nullable=false)
    @Temporal(TemporalType.TIME)
    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(id=" + this.id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Turno))
            return false;
        
        final Turno turno = (Turno) o;
        
        if (this.id.equals(turno.getId()))
            return true;
        
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }   
}
