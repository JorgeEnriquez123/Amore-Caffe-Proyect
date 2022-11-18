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
import com.edu.idat.amorecaffe.entity.DetallePedidoId;
import com.edu.idat.amorecaffe.entity.ProductoEntity;
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
    public DetallePedidoEntity findOne(String cabventa,String pro) throws ClassNotFoundException {
        DetallePedidoEntity detallePedido = null;
        detallePedido = DetallePedidoRepository.findById(new DetallePedidoId(cabventa, pro)).orElse(null);

        if (detallePedido == null) {
            throw new ClassNotFoundException(
                    String.format("DetallePedido with id or slug ['%s','%s'] not found", cabventa,pro));
        }
        return detallePedido;
    }

    @Override
    public DetallePedidoEntity create(DetallePedidoEntity detallePedido) throws ClassNotFoundException {
        String codProducto = detallePedido.getProducto().getId(), codVenta = detallePedido.getCabventa().getId();
        ProductoEntity pro = productoRepository.findById(codProducto).orElseThrow(() -> new ClassNotFoundException(
                String.format("Producto with id '%s' not found", codProducto)));
        CabeceraPedidoEntity cabVenta = cabPedidoRepository.findById(codVenta)
                .orElseThrow(() -> new ClassNotFoundException(
                        String.format("CabeceraPedido with id '%s' not found", codVenta)));

        DetallePedidoId detId = new DetallePedidoId(cabVenta.getId(), pro.getId());

        detallePedido.setId(detId);
        detallePedido.setPrecio_producto(pro.getPrecio());
        detallePedido.setSubtotal(detallePedido.getPrecio_producto() * Double.valueOf(detallePedido.getCantidad()));
        return DetallePedidoRepository.save(detallePedido);
    }

    @Override
    public void delete(String id) throws ClassNotFoundException, IllegalArgumentException, Exception {
        // if (!uuidValidate.matcher(id).matches()) {
        // throw new IllegalArgumentException(String.format("id '%s' must be a uuid",
        // id));
        // }
        // DetallePedidoEntity DetallePedido = this.findOne(id);
        // CabeceraPedidoEntity cabVenta =
        // cabPedidoRepository.findById(DetallePedido.getCabventa().getId()).orElse(null);

        // if (cabVenta != null)
        // throw new Exception(String.format("DetallePedido with id '%s' cannot be
        // deleted because it is in use.", id));

        // DetallePedidoRepository.delete(DetallePedido);
        return;
    }

    @Override
    public DetallePedidoEntity update(DetallePedidoEntity DetallePedidoEntityDto,String cabventa,String pro)
            throws ClassNotFoundException {
        if (!uuidValidate.matcher(cabventa).matches() || !uuidValidate.matcher(pro).matches() ) {
            throw new IllegalArgumentException(String.format("id ['%s','%s'] must be a uuid", cabventa,pro));
        }
        DetallePedidoEntity det = this.findOne(cabventa, pro);
        BeanUtils.copyProperties(DetallePedidoEntityDto, det);

        return DetallePedidoRepository.save(DetallePedidoEntityDto);
    }

    // private void verificar(String dni){
    // DetallePedidoEntity DetallePedidoNotValidate =
    // DetallePedidoRepository.findByDni(dni);
    // if (DetallePedidoNotValidate != null) {
    // throw new IllegalArgumentException(
    // String.format("DetallePedido with dni '%s' is exists in db", dni));
    // }
    // }
}
