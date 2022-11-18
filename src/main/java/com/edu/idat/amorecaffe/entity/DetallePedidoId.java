package com.edu.idat.amorecaffe.entity;


import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class DetallePedidoId implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id_cabventa")
	private String id_cabventa;
	@Column(name = "id_producto")
	private String id_producto;

	public DetallePedidoId(String id_cabventa, String id_producto) {
		this.id_cabventa = id_cabventa;
		this.id_producto = id_producto;
	}

	public DetallePedidoId() {
		
	}

	
	

}
