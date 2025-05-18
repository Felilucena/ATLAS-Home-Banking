// Excepcion que maneja el loggin incorrecto del usuario
package excepciones;

public class AutenticacionFallidaException extends Exception {
    public AutenticacionFallidaException() {
        super("** ERROR: Autenticación fallida. Nombre de usuario o contraseña incorrectos. Presione una tecla para intentar de nuevo");
    }
}

