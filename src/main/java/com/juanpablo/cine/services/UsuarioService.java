package com.juanpablo.cine.services;

import com.juanpablo.cine.models.Usuario;
import com.juanpablo.cine.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(Usuario usuario) {
        System.out.println("Registrando: " + usuario.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole("USER");
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
