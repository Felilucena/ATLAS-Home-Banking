package transacciones;

import excepciones.SaldoInsuficienteException;

import java.util.ArrayList;

public class HistorialDeTransacciones {
    private ArrayList<Transaccion> transacciones;

    public HistorialDeTransacciones() {
        this.transacciones = new ArrayList<>();
    }

    // Registrar una transacción en el historial
    public void registrarTransaccion(Transaccion transaccion) throws SaldoInsuficienteException {
        transacciones.add(transaccion);
        transaccion.ejecutar();
    }

    // Mostrar todas las transacciones con detalles específicos
    public void mostrarHistorial() {
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
        } else {
            for (Transaccion transaccion : transacciones) {
                String tipo = "";

                if (transaccion instanceof Transferencia) {
                    tipo = "Transferencia";
                } else if (transaccion instanceof PagoDeServicio) {
                    PagoDeServicio pago = (PagoDeServicio) transaccion;
                    tipo = "Pago de Servicio (" + pago.getTipoServicio() + ")";
                } else if (transaccion instanceof IngresoSaldo) {
                    tipo = "Ingreso de Saldo";
                } else if (transaccion instanceof Bonificacion) {
                    Bonificacion bonificacion = (Bonificacion) transaccion;
                    tipo = "Bonificación (" + bonificacion.getTipoBonificacion() + ")";
                }

                String signo = (transaccion instanceof Bonificacion || transaccion instanceof IngresoSaldo) ? "++"
                        : "--";
                System.out.println(
                        tipo + ": " + signo + " $" + transaccion.getMonto() + " | Fecha: " + transaccion.getFecha());
            }
        }
    }
}