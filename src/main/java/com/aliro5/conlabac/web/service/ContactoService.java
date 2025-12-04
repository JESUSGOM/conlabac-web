package com.aliro5.conlabac.web.service;

import com.aliro5.conlabac.web.dto.ContactoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ContactoService {

    @Value("${api.url.base}")
    private String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getApiUrl() {
        return baseUrl.replace("/centros", "/contactos");
    }

    public List<ContactoDTO> listar(Integer idCentro) {
        String url = getApiUrl() + "?centroId=" + idCentro;
        try {
            ContactoDTO[] res = restTemplate.getForObject(url, ContactoDTO[].class);
            return res != null ? Arrays.asList(res) : Arrays.asList();
        } catch (Exception e) {
            return Arrays.asList();
        }
    }

    public ContactoDTO obtener(Integer id) {
        return restTemplate.getForObject(getApiUrl() + "/" + id, ContactoDTO.class);
    }

    public void guardar(ContactoDTO contacto) {
        restTemplate.postForObject(getApiUrl(), contacto, ContactoDTO.class);
    }

    public void eliminar(Integer id) {
        restTemplate.delete(getApiUrl() + "/" + id);
    }
}