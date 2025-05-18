package modelo;

import main.Main;

import interfaces.Autenticable;
import interfaces.Modificable;
import excepciones.AutenticacionFallidaException;
import excepciones.FormatoEmailInvalidoException;
import excepciones.EntradaNoNumericaException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario implements Autenticable, Modificable {
    private String nombre;
    private String apellido;
    private int dni;
    private String fechaNacimiento;
    private String email;
    private long telefono;
    private String contrasena;
    private String nombreUsuario;
    private final List<Cuenta> cuentas;
    private final Salario salario;

    // Constructor
    public Usuario(String nombre, String apellido, int dni, String fechaNacimiento, String contrasena, String email, long telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.nombreUsuario = generarNombreUsuario(); // Generar el nombre de usuario por defecto
        this.cuentas = new ArrayList<>();
        this.salario = new Salario(10000); // Inicializa con un salario base de $10000

        // Crear una cuenta inicial
        crearCuentaInicial();
    }

    // Metodo para generar un nombre de usuario
    private String generarNombreUsuario() {
        String dniStr = String.valueOf(dni);
        return nombre.substring(0, 2).toLowerCase() + apellido.substring(0, 2).toLowerCase()
                + dniStr.substring(dniStr.length() - 2);
    }

    // Metodo para generar el alias del usuario
    public String generarAlias() {
        String dniStr = String.valueOf(dni);
        return nombre.toLowerCase() + apellido.toLowerCase() + dniStr.substring(dniStr.length() - 3) + ".atlas";
    }


    // Metodo para crear automáticamente la primera cuenta
    public void crearCuentaInicial() {
        Cuenta cuentaInicial = new Cuenta();
        cuentaInicial.setAlias(generarAlias());
        cuentas.add(cuentaInicial);
    }


    // Crear una nueva cuenta y agregarla a la lista (con posibilidad de personalizar alias)
    public void agregarNuevaCuenta() {
        Scanner sc = Main.sc;
        System.out.print("¿Desea personalizar el alias de la nueva cuenta? (S/N): ");
        String respuesta = sc.nextLine();
        String aliasPersonalizado = null;

        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Ingrese el alias deseado: ");
            aliasPersonalizado = sc.nextLine();
        }

        Cuenta nuevaCuenta = new Cuenta();

        if (aliasPersonalizado != null && !aliasPersonalizado.isEmpty()) {
            nuevaCuenta.setAlias(aliasPersonalizado);
        } else {
            nuevaCuenta.setAlias(generarAlias());
        }

        cuentas.add(nuevaCuenta);

        System.out.println("Nueva cuenta creada exitosamente.");
        System.out.println("Alias: " + nuevaCuenta.getAlias());
        System.out.println("CBU: " + nuevaCuenta.getCbu());
    }


    // Obtener la primera cuenta (principal)
    public Cuenta getCuentaPrincipal() {
        return cuentas.get(0);
    }

    // Obtener todas las cuentas
    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    // Buscar cuenta por alias
    public Cuenta buscarCuentaPorAlias(String alias) {
        for (Cuenta c : cuentas) {
            if (c.getAlias().equalsIgnoreCase(alias)) {
                return c;
            }
        }
        return null;
    }

    public Salario getSalario() {
        return salario;
    }

    // Métodos de información y edición de datos
    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("DNI: " + dni);
        System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
        System.out.println("Email: " + email);
        System.out.println("Telefono: " + telefono);
        System.out.println("Nombre de usuario: " + nombreUsuario);
        System.out.println("Cuentas del usuario:");
        for (Cuenta cuenta : cuentas) {
            System.out.println(
                    "- CBU: " + cuenta.getCbu() + " | Alias: " + cuenta.getAlias() + " | Saldo: $" + cuenta.getSaldo());
        }
    }

    public void agregarSaldo(double monto) {
        getCuentaPrincipal().agregarSaldo(monto);
        System.out.println("Se ha agregado $" + monto + " a su cuenta principal. Saldo actual: $"
                + getCuentaPrincipal().getSaldo());
    }

    public boolean descontarSaldo(double monto) {
        if (getCuentaPrincipal().descontarSaldo(monto)) {
            System.out.println("Se ha descontado $" + monto + " de su cuenta principal. Saldo actual: $"
                    + getCuentaPrincipal().getSaldo());
            return true;
        } else {
            System.out.println("Saldo insuficiente.");
            return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean autenticar(String username, String password) throws AutenticacionFallidaException {
        if ((username.equals(nombreUsuario) || username.equals(email) || username.equals(String.valueOf(telefono)))
                && password.equals(contrasena)) {
            return true;
        }
        throw new AutenticacionFallidaException();
    }

    @Override
    public void logout() {
        System.out.println("Cerrando sesión...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("\nSesión finalizada. Vuelva pronto!");
    }

    // Metodo para modificar los datos del usaurio
    public void modificarDatos() {
        Scanner sc = Main.sc;

        // Nombre
        System.out.print("Nombre actual: " + nombre + ". Ingrese nuevo nombre o '01' para omitir: ");
        String input = sc.nextLine();
        if (!input.equals("01")) {
            this.setNombre(input);
        }

        // Apellido
        System.out.print("Apellido actual: " + apellido + ". Ingrese nuevo apellido o '01' para omitir: ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            this.setApellido(input);
        }

        // Fecha de Nacimiento
        System.out.print("Fecha de Nacimiento actual: " + fechaNacimiento + ". Ingrese nueva fecha o '01': ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            this.setFechaNacimiento(input);
        }

        // DNI
        System.out.print("DNI actual: " + dni + ". Ingrese nuevo DNI o '01': ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            try {
                setDni(Integer.parseInt(input));
            } catch (NumberFormatException e) {
                System.out.println(new EntradaNoNumericaException().getMessage());
            }
        }

        // Correo Electronico
        System.out.print("Email actual: " + email + ". Ingrese nuevo email o '01': ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            if (input.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                this.setEmail(input);
            } else {
                System.out.println(new FormatoEmailInvalidoException().getMessage());
            }
        }

        // Teléfono
        System.out.print("Teléfono actual: " + telefono + ". Ingrese nuevo teléfono o '01': ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            try {
                setTelefono(Long.parseLong(input));
            } catch (NumberFormatException e) {
                System.out.println(new EntradaNoNumericaException().getMessage());
            }
        }

        // Nombre de Usuario
        System.out.print("Nombre de usuario actual: " + nombreUsuario + ". Ingrese nuevo nombre o '01': ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            this.setNombreUsuario(input);
        }

        System.out.print("Contraseña actual: " + contrasena + ". Ingrese nueva o '01': ");
        input = sc.nextLine();
        if (!input.equals("01")) {
            this.setContrasena(input);
        }

        // Mostrar los datos actualizados
        System.out.println("\n-- Datos actualizados exitosamente --");
        mostrarDatos();
    }
}
