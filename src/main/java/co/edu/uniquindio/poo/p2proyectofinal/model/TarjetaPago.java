package co.edu.uniquindio.poo.p2proyectofinal.model;

public class TarjetaPago implements IMetodoPago {
    private String numeroTarjeta;
    private String cvv;

    public TarjetaPago(String numeroTarjeta, String cvv) {
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
    }

    @Override
    public boolean pagar(Compra compra) throws ProyectoException {
        double monto=compra.getTotal();
        if(monto<=0) {throw new ProyectoException("el monto debe ser mayo a 0");}
        System.out.println("Procesando pago de $" + monto + " con Tarjeta...");
        return true;
    }

    @Override
    public String getNombre() {
        return "Tarjeta";
    }
}
