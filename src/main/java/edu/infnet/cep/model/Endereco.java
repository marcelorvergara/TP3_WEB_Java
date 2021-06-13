/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.cep.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
public class Endereco {

    @JsonIgnoreProperties(ignoreUnknown = true)

    @JsonProperty("cep")
    private String cep;
    @JsonProperty("logradouro")
    private String logradouro;
    @JsonProperty("complemento")
    private String complemento;
    @JsonProperty("bairro")
    private String bairro;
    @JsonProperty("localidade")
    private String localidade;
    @JsonProperty("uf")
    private String uf;
    @JsonProperty("unidade")
    private String unidade;
    @JsonProperty("ibge")
    private String ibge;
    @JsonProperty("gia")
    private String gia;

    public Endereco() {
    }

    @JsonProperty("")
    public String getCep() {
        return cep;
    }

    @JsonProperty("")
    public void setCep(String cep) {
        this.cep = cep;
    }

    @JsonProperty("")
    public String getLogradouro() {
        return logradouro;
    }

    @JsonProperty("")
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    @JsonProperty("")
    public String getComplemento() {
        return complemento;
    }

    @JsonProperty("")
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @JsonProperty("")
    public String getBairro() {
        return bairro;
    }

    @JsonProperty("")
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @JsonProperty("")
    public String getLocalidade() {
        return localidade;
    }

    @JsonProperty("")
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    @JsonProperty("")
    public String getUf() {
        return uf;
    }

    @JsonProperty("uf")
    public void setUf(String uf) {
        this.uf = uf;
    }

    @JsonProperty("unidade")
    public String getUnidade() {
        return unidade;
    }

    @JsonProperty("unidade")
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    @JsonProperty("ibge")
    public String getIbge() {
        return ibge;
    }

    @JsonProperty("ibge")
    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    @JsonProperty("gia")
    public String getGia() {
        return gia;
    }

    @JsonProperty("gia")
    public void setGia(String gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "BuscaCepDomain [cep=" + cep + ", logradouro=" + logradouro + ", complemento=" + complemento
                + ", bairro=" + bairro + ", localidade=" + localidade + ", uf=" + uf + ", unidade=" + unidade
                + ", ibge=" + ibge + ", gia=" + gia + "]";
    }

}
