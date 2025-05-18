package sistema;

import modelo.Usuario;

import java.util.ArrayList;

// Clase que centraliza la gestión de usuarios y cuentas
public class Banco {
    private ArrayList<Usuario> usuarios;
    private static int totalUsuarios = 0;  // Atributo estático para contar usuarios

    // Constructor que inicializa la lista de usuarios
    public Banco() {
        this.usuarios = new ArrayList<>();
    }

    // Metodo para registrar un nuevo usuario
    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        totalUsuarios++;
        System.out.println("Usuario registrado exitosamente. Su nombre de usuario es: " + usuario.getNombreUsuario());
    }

    // Metodo para buscar un usuario por nombre de usuario
    public Usuario buscarUsuarioPorIdentificador(String identificador) {
        for (Usuario usuario : usuarios) {
            if (
                    usuario.getNombreUsuario().equals(identificador) ||
                            usuario.getEmail().equals(identificador) ||
                            String.valueOf(usuario.getTelefono()).equals(identificador)) {
                return usuario;
            }
        }
        return null;
    }


    // Metodo para obtener el número total de usuarios
    public static int getTotalUsuarios() {
        return totalUsuarios;
    }

    // Metodo para listar todos los usuarios registrados (para fines de depuración)
    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("Usuarios registrados:");
            for (Usuario usuario : usuarios) {
                System.out.println("Usuario: " + usuario.getNombreUsuario());
            }
        }
    }
}
