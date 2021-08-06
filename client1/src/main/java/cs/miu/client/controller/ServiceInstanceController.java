package cs.miu.client.controller;

import cs.miu.client.model.CountryRegion;
import cs.miu.client.service.CountryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ServiceInstanceController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private CountryServiceClient countryServiceClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@GetMapping("/countries/{countryCode}")
	public ResponseEntity<?> findById(@PathVariable String countryCode) {
		CountryRegion countryRegion = countryServiceClient.findById(countryCode);
		return new ResponseEntity<CountryRegion>(countryRegion, HttpStatus.OK);
	}

	@GetMapping("/countries")
	public List<CountryRegion> findAll(){
		return countryServiceClient.findAll();
	}
}
