/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.persistence.service;

import edu.infnet.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Service
public interface UserService {

    public List<Usuario> findAll();

    public void save(Usuario user);

    public Optional<Usuario> findById(Long id);

    public void deleteById(Long id);

}
