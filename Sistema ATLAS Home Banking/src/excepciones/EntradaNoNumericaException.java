// Excepcion cuando el usuario ingresa un input no numerico en campos numericos
package excepciones;

public class EntradaNoNumericaException extends Exception {
    public EntradaNoNumericaException() {
        super("** ERROR: Formato incorrecto. Se espera el uso de datos numéricos. Intente nuevamente");
    }
}
