package br.com.casadocodigo.usuarios;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuariosRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

}
