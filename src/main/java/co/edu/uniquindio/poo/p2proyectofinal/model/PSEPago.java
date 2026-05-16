package co.edu.uniquindio.poo.p2proyectofinal.model;

public class PSEPago implements IMetodoPago {
    private String banco;
    private String correoUsuario;

    public PSEPago(String banco, String correoUsuario) {
        this.banco = banco;
        this.correoUsuario = correoUsuario;
    }

    @Override
    public boolean pagar(Compra compra) throws ProyectoException {
        double monto=compra.getTotal();
        if(monto<=0) {throw new ProyectoException("el monto debe ser mayor a 0");}
        System.out.println("Redirigiendo a la plataforma de " + banco + " para pagar $" + monto);
        return true;
    }

    @Override
    public String getNombre() {
        return "PSE";
    }
}
