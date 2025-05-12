package Usuarios;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class SistemaAutenticacion {

    private Map<String, Usuario> usuarios;

    public SistemaAutenticacion() {
        usuarios = new HashMap<>();
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getLogin(), usuario);
    }

    public Usuario autenticar(String login, String password) {
        Usuario usuario = usuarios.get(login);

        if (usuario != null && usuario.getPassword().equals(password)) {
            System.out.println("Autenticación exitosa para " + usuario.getNombre());
            return usuario;
        }

        System.out.println("Autenticación fallida");
        return null;
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios.values();
    }
}
