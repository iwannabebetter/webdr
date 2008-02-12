/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fpuna.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ghuttemann
 */
@Embeddable
public class MedidasPaciente {
    
    private Double pesoActual;      // opcional; en kilogramos
    private Integer alturaActual;    // opcional; en centímetros
    private Integer edadActual;     // opcional; en años o meses
    private Boolean edadEnMeses;    // indica si la edad está en meses

    public MedidasPaciente() {
        
    }
    
    public MedidasPaciente(Double pesoActual, Integer alturaActual, 
            Integer edadActual, Boolean edadEnMeses) {
        this.pesoActual   = pesoActual;
        this.alturaActual = alturaActual;
        this.edadActual   = edadActual;
        this.edadEnMeses  = edadEnMeses;
    }
    
    @Column(name="peso_actual")
    public Double getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(Double pesoActual) {
        this.pesoActual = pesoActual;
    }

    @Column(name="altura_actual")
    public Integer getAlturaActual() {
        return alturaActual;
    }

    public void setAlturaActual(Integer alturaActual) {
        this.alturaActual = alturaActual;
    }

    @Column(name="edad_actual",nullable=false)
    public Integer getEdadActual() {
        return edadActual;
    }

    public void setEdadActual(Integer edadActual) {
        this.edadActual = edadActual;
    }

    @Column(name="edad_meses",nullable=false)
    public Boolean getEdadEnMeses() {
        return edadEnMeses;
    }

    public void setEdadEnMeses(Boolean edadEnMeses) {
        this.edadEnMeses = edadEnMeses;
    }
}
