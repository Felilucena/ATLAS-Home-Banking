// Interfaz para manejar las transacciones de dinero
package interfaces;

import excepciones.SaldoInsuficienteException;

public interface Transaccionable {
    boolean validarTransaccion(); // validar antes de ejecutar
    void realizarTransaccion() throws SaldoInsuficienteException;
    double calcularMonto();
}

