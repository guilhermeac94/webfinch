package com.finch.webfinch.service;

import com.finch.webfinch.converter.JsonConverter;
import com.finch.webfinch.model.Cliente;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * Implementação da interface de Serviço do cliente.
 * Responsável pelas requisições para a API.
 * 
 * @author guilherme.carvalho
 */
@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

    @Override
    public List<Cliente> findAll() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(JsonConverter.getMessageConverters());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        Cliente[] response = restTemplate.exchange(
                "http://localhost:8080/clientes",
                HttpMethod.GET,
                entity,
                Cliente[].class).getBody();

        return Arrays.asList(response);
    }

    public Cliente findById(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(JsonConverter.getMessageConverters());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        Cliente response = restTemplate.exchange(
                "http://localhost:8080/clientes/" + id,
                HttpMethod.GET,
                entity,
                Cliente.class).getBody();

        return response;
    }

    public Cliente create(Cliente cliente) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(JsonConverter.getMessageConverters());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity(cliente, headers);

        Cliente response = restTemplate.exchange(
                "http://localhost:8080/clientes",
                HttpMethod.POST,
                entity,
                Cliente.class).getBody();
        return response;
    }

    public Cliente update(Cliente cliente) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(JsonConverter.getMessageConverters());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity(cliente, headers);

        Cliente response = restTemplate.exchange(
                "http://localhost:8080/clientes/" + cliente.getId(),
                HttpMethod.PUT,
                entity,
                Cliente.class).getBody();
        return response;
    }

    public void remove(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setMessageConverters(JsonConverter.getMessageConverters());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        restTemplate.exchange(
                "http://localhost:8080/clientes/" + id,
                HttpMethod.DELETE,
                entity,
                Cliente.class);
    }
}
