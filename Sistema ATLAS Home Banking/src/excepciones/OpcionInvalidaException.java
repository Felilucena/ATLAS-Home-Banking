// Excepcion en caso de que el usuario ingrese una opcion fuera del campo
package excepciones;

public class OpcionInvalidaException extends Exception {
    public OpcionInvalidaException() {
        super("** ERROR: Opción fuera de rango. Intente nuevamente.");
    }
}