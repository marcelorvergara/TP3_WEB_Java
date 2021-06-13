/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.cep.service;

import edu.infnet.cep.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@FeignClient(url = "https://viacep.com.br/ws/", name = "BuscaCepSvc")
public interface BuscaCepSvc {

    @GetMapping("{cep}/json")
    Endereco buscaEnderecoPor(@PathVariable("cep") String cep);

}
