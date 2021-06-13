/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.persistence.repository;

import edu.infnet.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {

}
