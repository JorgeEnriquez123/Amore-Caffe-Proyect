/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoId;
import com.edu.idat.amorecaffe.entity.ProductoEntity;

/**
 *
 * @author 51934
 */
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, DetallePedidoId> {
    List<DetallePedidoEntity> findByProducto(ProductoEntity productoEntity);
    List<DetallePedidoEntity> findByCabventa( CabeceraPedidoEntity cabeceraPedidoEntity);
}
