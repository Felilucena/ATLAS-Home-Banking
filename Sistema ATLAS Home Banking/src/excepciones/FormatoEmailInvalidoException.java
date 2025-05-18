// Excepcion si el usuario escribe un mail con el formato incorrecto
package excepciones;

public class FormatoEmailInvalidoException extends Exception {
    public FormatoEmailInvalidoException() {
        super("** ERROR: Formato incorrecto. El formato esperado es: direccion@gmail.com. Intente nuevamente");
    }
}

