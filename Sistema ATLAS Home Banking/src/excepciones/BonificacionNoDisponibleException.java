// Excepcion si el usuario decide volver a tomar su bonificacion no pasados los 30 dias
package excepciones;

public class BonificacionNoDisponibleException extends Exception {
    public BonificacionNoDisponibleException(String fechaProximaBonificacion) {
        super("** ERROR: Usted ya ha sido contribuido con su bonificación mensual. El día " + fechaProximaBonificacion + " podrá volver a solicitarla. Presione cualquier tecla para volver al menú principal");
    }
}

