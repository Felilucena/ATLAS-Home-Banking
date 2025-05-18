package transacciones;

import excepciones.SaldoInsuficienteException;

import java.time.LocalDateTime;

// Clase abstracta para representar una transacción
public abstract class Transaccion {
    protected double monto;
    protected LocalDateTime fecha;

    // Constructor de la clase Transaccion
    public Transaccion(double monto) {
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    // Metodo abstracto para ejecutar la transacción, debe ser implementado por las subclases
    public abstract void ejecutar() throws SaldoInsuficienteException;

    // Métodos getter
    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
