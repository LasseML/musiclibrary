package com.experis.musiclibrary.controllers;

import com.experis.musiclibrary.data_access.CustomerApiRepository;
import com.experis.musiclibrary.models.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class CustomerApiController {
    private CustomerApiRepository customerApiRepository = new CustomerApiRepository();


    @RequestMapping(value ="/api/customers", method = RequestMethod.GET)
    public ArrayList<Customer> getAllCustomers(){
        return customerApiRepository.getAllCustomers();
    }

    @RequestMapping(value = "api/customer/create", method = RequestMethod.POST)
    public Boolean createNewCustomer(@RequestBody Customer customer){
        return customerApiRepository.createCustomer(customer);
    }

    @RequestMapping(value = "api/customer/update/{id}", method = RequestMethod.PUT)
    public Boolean updateCustomer(@PathVariable int id, @RequestBody Customer customer){
        return customerApiRepository.updateCustomer(customer);
    }

    @RequestMapping(value ="/api/customers/popular/country", method = RequestMethod.GET)
    public HashMap<String, Integer> getPopularCountry() {
        return customerApiRepository.getPopularCountry();
    }

    @RequestMapping(value ="/api/customers/total/spend", method = RequestMethod.GET)
    public HashMap<Integer, String> getHighestSpender() {
        return customerApiRepository.getHighestSpender();
    }

    @RequestMapping(value ="/api/customer/{id}/popular/genre", method = RequestMethod.GET)
    public HashMap<String, Integer> getPopularGenre(@PathVariable int id) {
        return customerApiRepository.getPopularGenre(id);
    }
}
