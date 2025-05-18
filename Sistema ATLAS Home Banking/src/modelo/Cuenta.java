package modelo;

import transacciones.HistorialDeTransacciones;

import java.util.Random;

public class Cuenta {
    private double saldo;
    private String cbu;
    private String alias;
    private HistorialDeTransacciones historial;

    // Constructor que genera una cuenta con saldo inicial, CBU y alias
    public Cuenta() {
        this.saldo = 10000.0; // Saldo inicial de $10000
        this.cbu = generarCBU();
        this.alias = "sin_alias";
        this.historial = new HistorialDeTransacciones();
    }

    // Genera un CBU aleatorio de 22 dígitos
    private String generarCBU() {
        Random random = new Random();
        StringBuilder cbuGenerado = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            cbuGenerado.append(random.nextInt(10));
        }
        return cbuGenerado.toString();
    }

    // Metodo para consultar el saldo de la cuenta
    public void consultarSaldo() {
        System.out.println("Saldo actual: $" + saldo);
    }

    // Metodo para descontar saldo de la cuenta
    public boolean descontarSaldo(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    // Metodo para agregar saldo a la cuenta
    public void agregarSaldo(double monto) {
        saldo += monto;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCbu() {
        return cbu;
    }

    public HistorialDeTransacciones getHistorial() {
        return historial;
    }
}
