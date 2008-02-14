/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import edu.fpuna.Constants.FormatoFecha;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

/**
 * Clase que representa una reserva
 * @author ghuttemann
 */
@Entity
@Table(name="reserva")
public class Reserva extends BaseObject {
    
    private Long id;
    private Timestamp fechaRealizacion;
    private Timestamp fechaReservada;
    private Boolean cancelado;
    private String observacionCancelacion;
    private Paciente paciente;
    private Consulta consulta;
    private Turno turno;

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="fecha_realizacion",nullable=false)
    @org.hibernate.annotations.Index(name="fec_realiz_idx")
    //@Temporal(value = TemporalType.DATE)
    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    /*
     * Retorna la fecha en formato texto
     */
    @Transient
    public String getFechaRealizacionString() {
        return super.formatearFecha(fechaRealizacion, FormatoFecha.FECHAHORA);
    }
    
    /*
     * Retorna la fecha en formato texto
     */
    @Transient
    public String getFechaReservadaString() {
        return super.formatearFecha(fechaReservada, FormatoFecha.FECHAHORA);
    }
    
    public void setFechaRealizacion(Timestamp fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    @Column(name="fecha_reservada",nullable=false)
    @org.hibernate.annotations.Index(name="fec_reserv_idx")
    //@Temporal(value = TemporalType.DATE)
    public Date getFechaReservada() {
        return fechaReservada;
    }

    public void setFechaReservada(Timestamp fechaReservada) {
        this.fechaReservada = fechaReservada;
    }

    @Column(name="cancelado")
    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    @Column(name="observ_cancel")
    @Lob
    public String getObservacionCancelacion() {
        return observacionCancelacion;
    }

    public void setObservacionCancelacion(String observacionCancelacion) {
        this.observacionCancelacion = observacionCancelacion;
    }

    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn(name="paciente_id",nullable=false)
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="consulta_id",unique=true)
    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="turno_id",unique=true)
    public Turno getTurno() {
        return turno;
    }
    
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
