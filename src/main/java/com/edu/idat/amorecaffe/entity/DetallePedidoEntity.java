/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author 51934
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "det_pedidos")
@Table(name = "det_pedidos")
@Data
public class DetallePedidoEntity implements Serializable {
    @EmbeddedId
    DetallePedidoId id;

    private static final long serialVersionUID = 1L;
    @OneToOne
    @MapsId("id_cabventa")
    private CabeceraPedidoEntity cabventa;
    @OneToOne
    @MapsId("id_producto")
    private ProductoEntity producto;

    @Column(name = "precio", nullable = false, columnDefinition = "Decimal(10,2)")
    @NotNull(message = "precio must not be null")
    @Positive()
    private Double precio_producto;
    @Column(name = "cantidad", nullable = false)
    @NotNull(message = "cantidad must not be null")
    @Pattern(regexp = "[0-9]", message = "cantidad must be a number")
    @Positive
    @Min(1)
    private int cantidad;
    @Column(name = "subtotal", nullable = false,columnDefinition = "Decimal(10,2)")
    @Positive
    @Min(1)
    private double subtotal;
    
    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.precio_producto = this.producto.getPrecio();
        this.subtotal = this.precio_producto * Double.valueOf(this.cantidad);
    }
}
