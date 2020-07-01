package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Juerghens castro on 06-30-20 and  06:00 PM to 2020
 */
public class RestTemplateExamples {

    public static final String API_ROOT = "https://api.predic8.de:443/shop";

    @Test
    public void getCategories() throws Exception {
        String apiurl = API_ROOT + "/categories/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiurl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() throws Exception {
        String apiurl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiurl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomers() throws Exception {
        String apiurl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> postmap = new HashMap<>();
        postmap.put("firstname", "joe");
        postmap.put("lastname", "buck");
        JsonNode jsonNode = restTemplate.postForObject(apiurl, postmap, JsonNode.class);


        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateCustomers() throws Exception {

        String apiurl = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> postmap = new HashMap<>();
        postmap.put("firstname", "Michael");
        postmap.put("lastname", "Cars");
        JsonNode jsonNode = restTemplate.postForObject(apiurl, postmap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
        String customerUrl = jsonNode.get("customer_url").textValue();
        String id = customerUrl.split("/")[3];
        System.out.println("created customer id= " + id);
        postmap.put("firstname", "Michael 2");
        postmap.put("lastname", "Cars 2");
        restTemplate.put(apiurl + id, postmap);
        JsonNode updateNode = restTemplate.getForObject(apiurl + id, JsonNode.class);
        System.out.println("Response");
        System.out.println(updateNode.toString());
        System.out.println(updateNode.get(0));

    }

    @Test(expected = ResourceAccessException.class)
    public void updateCustomerUsingPatchSunHttp() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);

        System.out.println(updatedNode.toString());

    }

    @Test
    public void updateCustomerUsingPatch() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        // Use Apache HTTP client factory
        //see: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");

        //example of setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);

        System.out.println(updatedNode.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() throws Exception {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Les");
        postMap.put("lastname", "Claypool");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Created customer id: " + id);

        restTemplate.delete(apiUrl + id); //expects 200 status

        System.out.println("Customer deleted");

        //should go boom on 404
        restTemplate.getForObject(apiUrl + id, JsonNode.class);

    }

}
