/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.idat.amorecaffe.services.pedidodetalle;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.repository.CabeceraPedidoRepository;
import com.edu.idat.amorecaffe.repository.DetallePedidoRepository;
import com.edu.idat.amorecaffe.repository.ProductoRepository;

/**
 *
 * @author 51934
 */
@Service
public class DetallePedidoServiceImp implements DetallePedidoService {

    Pattern uuidValidate = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    @Autowired
    private DetallePedidoRepository DetallePedidoRepository;
    @Autowired
    private CabeceraPedidoRepository cabPedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;



    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoEntity> findAll() {
        return DetallePedidoRepository.findAll();
    }

    @Override
    public DetallePedidoEntity findOne(String search) throws ClassNotFoundException {
        DetallePedidoEntity detallePedido = null;

        if (uuidValidate.matcher(search).matches()) {
            detallePedido = DetallePedidoRepository.findById(search).orElse(null);
        }
        if (detallePedido == null) {
            throw new ClassNotFoundException(
                    String.format("DetallePedido with id or slug '%s' not found", search));
        }
        return detallePedido;
    }

    @Override
    public DetallePedidoEntity create(DetallePedidoEntity DetallePedido) throws ClassNotFoundException{
        String codProducto = DetallePedido.getProducto().getId();
        productoRepository.findById(codProducto).orElseThrow(()->new ClassNotFoundException(
            String.format("Producto with id '%s' not found", codProducto))
        );
        return DetallePedidoRepository.save(DetallePedido);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        // if (!uuidValidate.matcher(id).matches()) {
        //     throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        // }
        // DetallePedidoEntity DetallePedido = this.findOne(id);
        // CabeceraPedidoEntity cabVenta = cabPedidoRepository.findById(DetallePedido.getCabventa().getId()).orElse(null);

        // if (cabVenta != null)
        //     throw new Exception(String.format("DetallePedido with id '%s' cannot be deleted because it is in use.", id));

        // DetallePedidoRepository.delete(DetallePedido);
        return;
    }

    @Override
    public DetallePedidoEntity update(DetallePedidoEntity DetallePedidoEntityDto, String id) throws ClassNotFoundException {
        if (!uuidValidate.matcher(id).matches()) {
            throw new IllegalArgumentException(String.format("id '%s' must be a uuid", id));
        }
        DetallePedidoEntity det = this.findOne(id);
        BeanUtils.copyProperties(DetallePedidoEntityDto, det);
        return DetallePedidoRepository.save(det);
    }

    // private void verificar(String dni){
    //     DetallePedidoEntity DetallePedidoNotValidate = DetallePedidoRepository.findByDni(dni);
    //     if (DetallePedidoNotValidate != null) {
    //         throw new IllegalArgumentException(
    //                 String.format("DetallePedido with dni '%s' is exists in db", dni));
    //     }
    // }
}
 