/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.persistence.service;

import edu.infnet.model.Usuario;
import edu.infnet.persistence.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<Usuario> findAll() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    public void save(Usuario user) {
        repository.save(user);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }
}
