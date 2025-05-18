// Excepcion que se lanza cuando el usuario realiza una transferencia con fondos insuficientes
package excepciones;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException() {
        super("** ERROR: Saldo insuficiente para realizar la transacción.");
    }
}
