package guru.springframework.springrestclientexamples.Controllers;

import guru.springframework.springrestclientexamples.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

/**
 * Juerghens castro on 06-30-20 and  12:46 PM to 2020
 */

@Slf4j
@Controller
public class UserControllers {


    private final ApiService apiService;

    public UserControllers(ApiService apiService) {
        this.apiService = apiService;
    }

    //cargando el indice de la pagina
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    //obteniendo los usuarios con un limite definido desde un webservice
    @PostMapping("/users")
    public String formpost(Model model, ServerWebExchange serverWebExchange) {

        MultiValueMap<String, String> map = serverWebExchange.getFormData().block();
        Integer limit = new Integer(map.get("limit").get(0));
        log.debug("Received limit value" + limit);
        //default if null or zero

        if (limit == null || limit == 0) {
            log.debug("Setting limit to default of 10");
            limit=10;
        }

        model.addAttribute("users", apiService.getUsers(limit));

        return "userlist";
    }
}
