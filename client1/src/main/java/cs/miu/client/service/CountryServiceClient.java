package cs.miu.client.service;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cs.miu.client.model.CountryRegion;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class CountryServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${geography-service.service-name}")
    private String serverName;

    @Value("${geography-service.username}")
    private String userName;

    @Value("${geography-service.password}")
    private String pass;

    @CircuitBreaker(name = "backendA", fallbackMethod = "findByIdFallback")
    @RateLimiter(name = "backendA")
    @Bulkhead(name = "backendA")
    @Retry(name = "backendA", fallbackMethod = "findByIdFallback")
    public CountryRegion findById(String countryCode) {
        String url = getBaseServiceUrl(serverName) + "/countries/" + countryCode;
        HttpEntity<CountryRegion> request = new HttpEntity<CountryRegion>(createHeaders(userName,pass));
        ResponseEntity<CountryRegion> countryCache = restTemplate.exchange(url, HttpMethod.GET, request, CountryRegion.class);
        return countryCache.getBody();
    }

    public CountryRegion findByIdFallback(String countryCode, Exception ex) {
        System.out.println(ex.toString());
        return new CountryRegion();
    }

    public List<CountryRegion> findAll() {
        String url = getBaseServiceUrl(serverName) + "/countries";
        HttpEntity<CountryRegion> request = new HttpEntity<CountryRegion>(createHeaders(userName,pass));
        ResponseEntity<CountryRegion[]> countries = restTemplate.exchange(url, HttpMethod.GET, request, CountryRegion[].class);
        return Arrays.asList(countries.getBody());
    }

    public List<CountryRegion> findAllFallback() {
        System.out.println("Inside findAllFallback()");
        return new ArrayList<>();
    }

    private String getBaseServiceUrl(String serverName) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serverName);
        return serviceInstances.get(0).getUri().toString();
    }

    public HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        String encodedAuth =
                Base64.getEncoder().encodeToString(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + encodedAuth;
        headers.set("Authorization", authHeader);
        return headers;
    }

}
