package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entity.CustomerMaster;
import com.example.demo.Service.CustomerService;
import com.example.demo.dto.ApiResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/addCustomer")
    public ResponseEntity<ApiResponse> addCustomer(@RequestBody CustomerMaster customer) {
        CustomerMaster customerMaster = customerService.AddCustomer(customer);

         // Call Email microservice
        String emailServiceUrl = "http://localhost:8081/sendEmail";
        Map<String, Object> emailMaster = new HashMap<>();
        emailMaster.put("name", customerMaster.getFirstName() + " " + customerMaster.getLastName());
        emailMaster.put("email", customerMaster.getEmail());
       
        
        try {
            restTemplate.postForObject(emailServiceUrl, emailMaster, String.class);
           
        } catch (Exception e) {
            System.err.println("An error occurred while sending the email: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Customer added successfully", null));
        

    }

}
