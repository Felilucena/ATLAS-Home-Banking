package servicio;

import main.Main;
import modelo.Cuenta;
import modelo.Usuario;
import transacciones.IngresoSaldo;
import transacciones.PagoDeServicio;
import transacciones.Transferencia;

import excepciones.BonificacionNoDisponibleException;
import excepciones.OpcionInvalidaException;
import excepciones.SaldoInsuficienteException;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Usuario usuario;
    private final Scanner scanner;

    // Constructor que recibe el usuario
    public Menu(Usuario usuario) {
        this.usuario = usuario;
        this.scanner = new Scanner(System.in);
    }

    // Metodo para mostrar el menú principal
    public void mostrarMenu() {
        Cuenta cuentaActual = usuario.getCuentaPrincipal(); // Cuenta principal por defecto
        int opcion = 0;

        do {
            System.out.println("\nTe damos la bienvenida " + usuario.getNombre() + " a nuestro Home Banking");
            System.out.println("----- Menú -----");
            System.out.println("Cuenta actual: " + cuentaActual.getAlias() + " | Saldo: $" + cuentaActual.getSaldo());
            System.out.println("1. Ver datos del usuario");
            System.out.println("2. Realizar transferencia");
            System.out.println("3. Pagar servicio");
            System.out.println("4. Modificar datos personales");
            System.out.println("5. Agregar saldo");
            System.out.println("6. Ver bonificaciones");
            System.out.println("7. Ver historial de transacciones de la cuenta actual");
            System.out.println("8. Seleccionar otra cuenta");
            System.out.println("9. Crear nueva cuenta");
            System.out.println("10. Cerrar sesión");

            boolean opcionValida = false;
            while (!opcionValida) {
                System.out.print("Seleccione una opción: ");
                String entrada = scanner.nextLine();

                try {
                    if (!entrada.matches("\\d+")) {
                        throw new excepciones.EntradaNoNumericaException();
                    }

                    opcion = Integer.parseInt(entrada);

                    if (opcion < 1 || opcion > 10) {
                        throw new excepciones.OpcionInvalidaException();
                    }

                    opcionValida = true;

                } catch (excepciones.EntradaNoNumericaException | excepciones.OpcionInvalidaException e) {
                    System.out.println(e.getMessage());
                    System.out.println(); // Espacio para claridad
                }
            }

            try{
                switch (opcion) {
                    case 1: // Mostrar los datos del usuario
                        System.out.println("\n** Datos del Usuario **");
                        usuario.mostrarDatos();
                        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                        Main.sc.nextLine();
                        break;

                    case 2: // Realizar una transferencia
                        System.out.println("\n** Transferencia de Fondos **");
                        realizarTransferencia(cuentaActual);
                        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                        Main.sc.nextLine();
                        break;

                    case 3: // Pagar un servicio
                        System.out.println("\n** Pago de Servicios **");
                        pagarServicio(cuentaActual);
                        break;

                    case 4: // Modificar datos personales del usuario
                        System.out.println("\n** Modificación de Datos del Usuario **");
                        usuario.modificarDatos();
                        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                        Main.sc.nextLine();
                        break;

                    case 5: // Agregar saldo al salario
                        System.out.println("\n** Agregar Saldo **");
                        System.out.print("Ingrese el monto a agregar: $");
                        double montoAgregar = scanner.nextDouble();
                        scanner.nextLine(); // Limpiar el buffer

                        IngresoSaldo ingreso = new IngresoSaldo(montoAgregar, cuentaActual);
                        cuentaActual.getHistorial().registrarTransaccion(ingreso);

                        System.out.println("Se ha agregado $" + montoAgregar + " a su cuenta. Saldo actual: $" + cuentaActual.getSaldo());
                        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                        Main.sc.nextLine();
                        break;

                    case 6: // Ver bonificaciones
                        System.out.println("\n** Bonificaciones **");
                        try {
                            usuario.getSalario().verificarBonificacion(usuario);
                        } catch (BonificacionNoDisponibleException e) {
                            System.out.println(e.getMessage());
                            Main.sc.nextLine();
                        }
                        break;

                    case 7:
                        System.out.println("\n** Historial de Transacciones **");
                        cuentaActual.getHistorial().mostrarHistorial();
                        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                        Main.sc.nextLine();
                        break;

                    case 8:
                        System.out.println("\n** Selección de Cuenta **");
                        List<Cuenta> cuentas = usuario.getCuentas();
                        for (int i = 0; i < cuentas.size(); i++) {
                            System.out.println((i + 1) + ". Alias: " + cuentas.get(i).getAlias() + " | Saldo: $" + cuentas.get(i).getSaldo());
                        }
                        System.out.print("Seleccione el número de la cuenta: ");
                        String entradaSeleccion = scanner.nextLine(); // Pedimos como string

                        try {
                            if (!entradaSeleccion.matches("\\d+")) {
                                throw new excepciones.EntradaNoNumericaException();
                            }

                            int seleccion = Integer.parseInt(entradaSeleccion);

                            if (seleccion < 1 || seleccion > cuentas.size()) {
                                throw new excepciones.OpcionInvalidaException();
                            }

                            cuentaActual = cuentas.get(seleccion - 1);
                            System.out.println("Cuenta cambiada a: " + cuentaActual.getAlias());

                        } catch (excepciones.EntradaNoNumericaException | excepciones.OpcionInvalidaException e) {
                            System.out.println(e.getMessage());
                            System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                            Main.sc.nextLine();
                        }
                        break;


                    case 9:
                        System.out.println("\n** Crear Nueva Cuenta **");
                        usuario.agregarNuevaCuenta();
                        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
                        Main.sc.nextLine();
                        break;

                    case 10: // Cerrar sesión
                        usuario.logout(); // Llama al metodo con Thread.sleep
                        break;

                    default:
                        throw new OpcionInvalidaException();
                }
            } catch (OpcionInvalidaException e) {
                System.out.println(e.getMessage());
            } catch (SaldoInsuficienteException e) {
                throw new RuntimeException(e);
            }

        } while (opcion != 10);
    }

    // Metodo para realizar una transferencia
    private void realizarTransferencia(Cuenta cuentaActual) {
        System.out.print("Ingrese el CBU/alias de la cuenta destino: ");
        String cbuDestino = scanner.nextLine();
        System.out.print("Ingrese el monto a transferir: $");
        double monto = scanner.nextDouble();
        scanner.nextLine();

        try {
            Transferencia trans = new Transferencia(monto, cbuDestino, cuentaActual);
            cuentaActual.getHistorial().registrarTransaccion(trans);
        } catch (SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }
    }


    // Metodo para pagar un servicio
    private void pagarServicio(Cuenta cuentaActual) {
        System.out.println("1. Pagar hipoteca ($10000)");
        System.out.println("2. Pagar luz ($5000)");
        System.out.println("3. Pagar agua ($3500)");
        System.out.println("4. Pagar wifi ($2750)");
        System.out.print("Seleccione una opción: ");

        String entrada = scanner.nextLine();
        int opcion = 0;
        try {
            if (!entrada.matches("\\d+")) {
                throw new excepciones.EntradaNoNumericaException();
            }

            opcion = Integer.parseInt(entrada);

            if (opcion < 1 || opcion > 4) {
                throw new excepciones.OpcionInvalidaException();
            }

        } catch (excepciones.EntradaNoNumericaException | excepciones.OpcionInvalidaException e) {
            System.out.println(e.getMessage());
            System.out.println("*** Presione cualquier tecla para volver al menú principal...");
            Main.sc.nextLine();
            return;
        }

        double monto = 0;
        String tipoServicio = "";
        String nombreDelProveedor = "";
        String numeroDeFactura = "";

        switch (opcion) {
            case 1:
                monto = 10000;
                tipoServicio = "Hipoteca";
                nombreDelProveedor = "Banco Hipotecario";
                numeroDeFactura = "0001-12345678";
                break;
            case 2:
                monto = 5000;
                tipoServicio = "Luz";
                nombreDelProveedor = "Edenor";
                numeroDeFactura = "0002-87654321";
                break;
            case 3:
                monto = 3500;
                tipoServicio = "Agua";
                nombreDelProveedor = "AySA";
                numeroDeFactura = "0003-13579024";
                break;
            case 4:
                monto = 2750;
                tipoServicio = "Wifi";
                nombreDelProveedor = "Claro Argentina";
                numeroDeFactura = "0004-24680135";
                break;
        }

        try {
            if (cuentaActual.descontarSaldo(monto)) {
                System.out.println("Servicio pagado con éxito. Disfrútelo!");
                PagoDeServicio pago = new PagoDeServicio(monto, tipoServicio, nombreDelProveedor, numeroDeFactura);
                cuentaActual.getHistorial().registrarTransaccion(pago);
            } else {
                throw new excepciones.SaldoInsuficienteException();
            }
        } catch (excepciones.SaldoInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("*** Presione cualquier tecla para volver al menú principal...");
        Main.sc.nextLine();
    }
}