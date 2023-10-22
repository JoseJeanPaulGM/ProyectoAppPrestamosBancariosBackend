package com.cibertec.prestamos.service;

import com.cibertec.prestamos.domain.model.Usuario;
import com.cibertec.prestamos.domain.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;


    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return  usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(int idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario);
    }
}