package transacciones;

import modelo.Cuenta;

public class Bonificacion extends Transaccion {
    private String tipoBonificacion;
    private double porcentajeBonificacion;
    private Cuenta cuenta;

    public Bonificacion(double monto, String tipoBonificacion, double porcentajeBonificacion, Cuenta cuenta) {
        super(monto);
        this.tipoBonificacion = tipoBonificacion;
        this.porcentajeBonificacion = porcentajeBonificacion;
        this.cuenta = cuenta;
    }

    @Override
    // Metodo para ejecutar la bonificacion
    public void ejecutar() {
        cuenta.agregarSaldo(monto);
    }

    // Metodo para obtener el tipo de Bonificacion dada la edad del usuario
    public String getTipoBonificacion() {
        return tipoBonificacion;
    }

    public double getPorcentajeBonificacion() {
        return porcentajeBonificacion;
    }
}
