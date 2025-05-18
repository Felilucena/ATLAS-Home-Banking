package transacciones;

import modelo.Cuenta;

import excepciones.SaldoInsuficienteException;

// Clase que representa una transferencia, hereda de Transaccion
public class Transferencia extends Transaccion {
    private static double tarifaTransferencia;
    private String cbuDestino;
    private Cuenta cuentaOrigen;

    public Transferencia(double monto, String cbuDestino, Cuenta cuentaOrigen) {
        super(monto);
        this.cbuDestino = cbuDestino;
        this.cuentaOrigen = cuentaOrigen;
    }

    @Override
    public void ejecutar() throws SaldoInsuficienteException {
        tarifaTransferencia = monto * 0.05;
        double montoFinal = monto + tarifaTransferencia;
        if (!cuentaOrigen.descontarSaldo(montoFinal)) {
            throw new SaldoInsuficienteException();
        }
        System.out.println("Transferencia de $" + monto + " más tarifa de impuestos por $" + tarifaTransferencia + " realizada a " + cbuDestino);
    }


    public String getCbuDestino() {
        return cbuDestino;
    }

    public static double getTarifaTransferencia() {
        return tarifaTransferencia;
    }
}
