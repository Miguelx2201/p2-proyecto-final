package co.edu.uniquindio.poo.p2proyectofinal.model;

import java.io.Serializable;

public class EfectivoPago implements IMetodoPago {
    @Override
    public boolean pagar(Compra compra) throws ProyectoException {
        double monto=compra.getTotal();
        if(monto<=0) {throw new ProyectoException("el monto debe ser mayor a 0");}
        System.out.println("Generando recibo de pago para convenio de recaudo por $" + monto);
        return true;
    }

    @Override
    public String getNombre() {
        return "Efectivo ";
    }
}
