// Interfaz para el logueo correcto del usuario a su cuenta
package interfaces;

import excepciones.AutenticacionFallidaException;

public interface Autenticable {
    boolean autenticar(String username, String password) throws AutenticacionFallidaException;
    void logout(); // incluye pausa de 3 segundos
}

