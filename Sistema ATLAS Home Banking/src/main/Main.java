package main;

import modelo.Usuario;
import servicio.Menu;
import sistema.Banco;

import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    static Banco banco = new Banco(); // Instancia de la clase Banco
    static boolean sesionIniciada = false;
    static Usuario usuarioActual = null;// Variable para almacenar el usuario actual

    public static void main(String[] args) {
        while (true) {
            sesionIniciada = false;
            usuarioActual = null;

            // Mostrar menú de inicio hasta que se inicie sesión o se elija salir
            while (!sesionIniciada) {
                System.out.println("\n---- Bienvenido a ATLAS Home Banking ----");
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Salir");

                int opcion = 0;
                boolean opcionValida = false;

                while (!opcionValida) {
                    System.out.print("Seleccione una opción: ");
                    String entrada = sc.nextLine();

                    try {
                        if (!entrada.matches("\\d+")) {
                            throw new excepciones.EntradaNoNumericaException();
                        }

                        opcion = Integer.parseInt(entrada);

                        if (opcion < 1 || opcion > 3) {
                            throw new excepciones.OpcionInvalidaException();
                        }

                        opcionValida = true;

                    } catch (excepciones.EntradaNoNumericaException | excepciones.OpcionInvalidaException e) {
                        System.out.println(e.getMessage());
                        System.out.println(); // Salto de línea extra para legibilidad
                    }
                }

                switch (opcion) {
                    case 1:
                        // Registro
                        System.out.println("\n*** Registro de Nueva Cuenta ***");

                        System.out.print("Ingrese su nombre: ");
                        String nombre = sc.nextLine();

                        System.out.print("Ingrese su apellido: ");
                        String apellido = sc.nextLine();

                        int dni;
                        while (true) {
                            System.out.print("Ingrese su DNI: ");
                            try {
                                dni = Integer.parseInt(sc.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println(new excepciones.EntradaNoNumericaException().getMessage());
                            }
                        }

                        System.out.print("Ingrese su fecha de nacimiento (dd/mm/aaaa): ");
                        String fechaNacimiento = sc.nextLine();

                        String email;
                        while (true) {
                            System.out.print("Ingrese su correo electrónico: ");
                            email = sc.nextLine();
                            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) break;
                            else System.out.println(new excepciones.FormatoEmailInvalidoException().getMessage());
                        }

                        long telefono;
                        while (true) {
                            System.out.print("Ingrese su número de celular: ");
                            try {
                                telefono = Long.parseLong(sc.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println(new excepciones.EntradaNoNumericaException().getMessage());
                            }
                        }

                        System.out.print("Ingrese una contraseña: ");
                        String contrasena = sc.nextLine();

                        Usuario nuevoUsuario = new Usuario(nombre, apellido, dni, fechaNacimiento, contrasena, email, telefono);
                        banco.registrarUsuario(nuevoUsuario);
                        usuarioActual = nuevoUsuario;
                        System.out.println("*** Presione una tecla para volver al inicio");
                        sc.nextLine();
                        break;

                    case 2:
                        // Inicio de sesión
                        System.out.println("\n*** Inicio de Sesión ***");

                        System.out.print("Ingrese su nombre de usuario, correo electrónico o número de celular: ");
                        String identificador = sc.nextLine();

                        System.out.print("Ingrese su contraseña: ");
                        String contrasenaIngreso = sc.nextLine();

                        try {
                            Usuario usuario = banco.buscarUsuarioPorIdentificador(identificador);
                            if (usuario != null && usuario.getContrasena().equals(contrasenaIngreso)) {
                                sesionIniciada = true;
                                usuarioActual = usuario;
                                System.out.println("** Inicio de sesión exitoso.");
                            } else {
                                throw new excepciones.AutenticacionFallidaException();
                            }
                        } catch (excepciones.AutenticacionFallidaException e) {
                            System.out.println(e.getMessage());
                            sc.nextLine(); // Pausa
                        }
                        break;

                    case 3:// Salir completamente
                        System.out.println("Saliendo del sistema...");
                        System.out.println("\n*** Gracias por usar nuestro sistema ***");
                        return;
                }
            }

            // Menú principal (solo si la sesión fue iniciada correctamente)
            if (usuarioActual != null) {
                Menu menu = new Menu(usuarioActual);
                menu.mostrarMenu();
                // Al finalizar menu.mostrarMenu(), volvemos al ciclo inicial automáticamente
            }
        }
    }
}