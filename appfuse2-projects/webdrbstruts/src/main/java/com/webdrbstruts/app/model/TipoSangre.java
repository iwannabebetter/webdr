package com.webdrbstruts.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tipo_sangre")
public class TipoSangre extends BaseObject{
	private Long id;
	private String tiposangre;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	@Column(name="tipo_sangre", length=50)
	public String getTipoSangre(){
		return tiposangre;
	}

	public void setTipoSangre(String sangre){
		tiposangre = sangre;
	}

	@Override
	public boolean equals(Object o) {

		if(o != null && o.getClass().equals(this.getClass())){
			TipoSangre cmp = (TipoSangre) o;
			if(cmp.getTipoSangre().trim().compareToIgnoreCase(this.getTipoSangre().trim()) == 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		return tiposangre.hashCode();
	}

	@Override
	public String toString() {
		return tiposangre;
	}

}

