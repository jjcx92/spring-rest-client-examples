package guru.springframework.springrestclientexamples.service;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Juerghens castro on 06-30-20 and  09:03 AM to 2020
 */

@Service
public class ApiServiceImpl implements ApiService {

    //declarando variables para utilizar resttemplante y declarando variable que contiene el webservices
    private RestTemplate restTemplate;
    private final String api_url;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    //listando el webservice
    @Override
    public List<User> getUsers(Integer limit) {


        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString(api_url)
                .queryParam("limit", limit);
        //utilizando quemado el webservices
        //UserData userData=restTemplate.getForObject("http://private-anon-e87a3a470c-apifaketory.apiary-mock.com/api/user?limit=" + limit,UserData.class);
        UserData userData=restTemplate.getForObject(uriComponentsBuilder.toUriString(),UserData.class);

        return userData.getData();
    }
}
