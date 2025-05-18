package modelo;

import excepciones.SaldoInsuficienteException;
import main.Main;
import transacciones.Bonificacion;

import excepciones.BonificacionNoDisponibleException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Salario {
    private double salario;
    private double saldoAgregadoHoy = 0;
    private final double LIMITE_DIARIO = 250000;
    private LocalDate ultimaBonificacion;

    // Constructor que inicializa el salario
    public Salario(double salarioInicial) {
        this.salario = salarioInicial;
        this.ultimaBonificacion = null; // Inicialmente, no se ha aceptado ninguna bonificación
    }

    // Obtener el salario actual
    public double getSalario() {
        return salario;
    }

    // Ajustar o actualizar el salario
    public void setSalario(double nuevoSalario) {
        this.salario = nuevoSalario;
        System.out.println("Su salario ha sido actualizado a: $" + nuevoSalario);
    }

    // Mostrar el salario actual
    public void mostrarSalario() {
        System.out.println("El salario actual es: $" + salario);
    }

    // Agregar saldo al salario respetando el límite diario
    public boolean agregarSaldo(double monto) {
        if (saldoAgregadoHoy + monto > LIMITE_DIARIO) {
            System.out.println("No puede agregar más de $250000 en un solo día.");
            return false;
        } else {
            salario += monto;
            saldoAgregadoHoy += monto;
            return true;
        }
    }

    // Verificar bonificaciones de acuerdo a la edad
    public void verificarBonificacion(Usuario usuario) throws BonificacionNoDisponibleException, SaldoInsuficienteException {
        // Verificar si el usuario ya ha aceptado una bonificación en los últimos 30 días
        if (ultimaBonificacion != null) {
            long diasDesdeUltimaBonificacion = ChronoUnit.DAYS.between(ultimaBonificacion, LocalDate.now());
            if (diasDesdeUltimaBonificacion < 30) {
                LocalDate proxima = ultimaBonificacion.plusDays(30);
                throw new BonificacionNoDisponibleException(proxima.toString());
            }
        }

        Scanner sc = Main.sc;
        System.out.print("Ingrese su DNI: ");
        int dniIngresado;
        try {
            String dniInput = sc.nextLine();
            if (!dniInput.matches("\\d+")) {
                throw new excepciones.EntradaNoNumericaException();
            }
            dniIngresado = Integer.parseInt(dniInput);
        } catch (excepciones.EntradaNoNumericaException e) {
            System.out.println(e.getMessage());
            System.out.println("*** Presione cualquier tecla para volver al menú principal...");
            Main.sc.nextLine();
            return; // Cortar y volver al menú
        }

        System.out.print("Ingrese su fecha de nacimiento (dd/mm/aaaa): ");
        String fechaNacimientoIngresada = sc.nextLine();

        if (!fechaNacimientoIngresada.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("** ERROR: Formato incorrecto. Se esperaba dd/mm/aaaa.");
            System.out.println("*** Presione cualquier tecla para volver al menú principal...");
            Main.sc.nextLine();
            return;
        }

        // Verificar si el DNI y la fecha de nacimiento coinciden con los datos del usuario
        if (usuario.getDni() == dniIngresado && usuario.getFechaNacimiento().equals(fechaNacimientoIngresada)) {
            int edad = calcularEdad(usuario.getFechaNacimiento());
            double bonificacion = 0;
            String tipoBonificacion = "";

            // Bonificaciones por edades
            if (edad >= 16 && edad <= 18) {
                bonificacion = 15000;
                tipoBonificacion = "Bonificación Estudiantil";
            } else if (edad >= 19 && edad <= 25) {
                bonificacion = 25000;
                tipoBonificacion = "Bonificación Juvenil";
            } else if (edad >= 26 && edad <= 50) {
                bonificacion = 40000;
                tipoBonificacion = "Bonificación Adulta";
            } else if (edad >= 51) {
                bonificacion = 42500;
                tipoBonificacion = "Bonificación de Jubilados";
            }

            if (!tipoBonificacion.isEmpty()) {
                System.out.println("Usted " + usuario.getNombre() + " " + usuario.getApellido() +
                        ", DNI: " + usuario.getDni() + ", corresponde a la " + tipoBonificacion + " por $"
                        + bonificacion +
                        " mensuales. ¿Desea aceptarlos? S/N");
                String respuesta = sc.nextLine();
                if (respuesta.equalsIgnoreCase("S")) {
                    Bonificacion bono = new Bonificacion(bonificacion, tipoBonificacion, 0, usuario.getCuentaPrincipal());

                    usuario.getCuentaPrincipal().getHistorial().registrarTransaccion(bono); // Primero registrar y ejecutar

                    // AHORA sí mostrar el saldo actualizado
                    System.out.println("La bonificación por $" + bonificacion + " ha sido agregada a su cuenta.");
                    System.out.println("Saldo actual: $" + usuario.getCuentaPrincipal().getSaldo());

                    // Actualizar fecha de última bonificación
                    ultimaBonificacion = LocalDate.now();

                    System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                    Main.sc.nextLine();
                } else {
                    System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                    Main.sc.nextLine();
                }
            } else {
                System.out.println("No hay bonificación disponible para su edad.");
                System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                Main.sc.nextLine();
            }
        } else {
            System.out.println("DNI o fecha de nacimiento incorrectos.");
            System.out.println("*** Presione cualquier tecla para volver al menú principal...");
            Main.sc.nextLine();
        }
    }

    // Calcular la edad a partir de la fecha de nacimiento
    private int calcularEdad(String fechaNacimiento) {
        String[] partes = fechaNacimiento.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);
        java.time.LocalDate fechaNac = java.time.LocalDate.of(anio, mes, dia);
        java.time.LocalDate ahora = java.time.LocalDate.now();
        return java.time.Period.between(fechaNac, ahora).getYears();
    }
}
