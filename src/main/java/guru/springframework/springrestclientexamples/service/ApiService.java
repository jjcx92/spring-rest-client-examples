package guru.springframework.springrestclientexamples.service;

import guru.springframework.api.domain.User;

import java.util.List;

/**
 * Juerghens castro on 06-30-20 and  09:01 AM to 2020
 */
public interface ApiService {

    List<User> getUsers(Integer limit);
}
