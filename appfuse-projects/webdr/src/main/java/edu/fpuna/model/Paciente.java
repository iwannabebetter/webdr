/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import java.sql.Date;
import javax.persistence.*;

/**
 * Clase que representa a un paciente
 * @author ghuttemann
 */
@Entity
@Table(name="paciente")
public class Paciente extends User {
    
    private TipoSangre tipoSangre;
    private Date fechaIngreso;
    private Date fechaNacimiento;
    private Integer cedula;

    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn(name="tiposangre_id",nullable=false)
    public TipoSangre getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(TipoSangre tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    @Column(name="fecingreso",nullable=false)
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    @Column(name="fechanac",nullable=false)
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    @Column(name="cedula",nullable=false)
    @org.hibernate.annotations.Index(name="cedula_idx")
    public Integer getCedula() {
        return cedula;
    }
    
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }
}
