package guru.springframework.springrestclientexamples.service;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Juerghens castro on 06-30-20 and  09:03 AM to 2020
 */

@Service
public class ApiServiceImpl implements ApiService{

    private RestTemplate restTemplate;

    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers(Integer limit) {

        UserData userData=restTemplate.getForObject("http://private-anon-e87a3a470c-apifaketory.apiary-mock.com/api/user?limit=" + limit,UserData.class);


        return userData.getData();
    }
}
