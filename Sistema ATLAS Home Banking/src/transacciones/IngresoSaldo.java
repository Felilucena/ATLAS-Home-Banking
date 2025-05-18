package transacciones;

import modelo.Cuenta;

public class IngresoSaldo extends Transaccion {
    private Cuenta cuenta;

    public IngresoSaldo(double monto, Cuenta cuenta) {
        super(monto);
        this.cuenta = cuenta;
    }

    @Override
    public void ejecutar() {
        cuenta.agregarSaldo(monto);
    }
}
