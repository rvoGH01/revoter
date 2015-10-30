package com.revoter.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revoter.model.Dish;
import com.revoter.rest.dao.RestourantDao;
import com.revoter.rest.dao.RestourantDaoImpl;

@RestController
@RequestMapping("/restourant")
public class RestourantService {
	
	@Autowired
	private RestourantDao restourantDao;
	
	public void addRestourant() {
		
	}
	
	public void addDishToRestourant() {
		
	}
	
	public void voteRestourant() {
		
	}
	
	@RequestMapping(value = "/dish/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> getUser(@PathVariable("id") long id) {
        System.out.println("Fetching Dish with id " + id);
        final Dish d = ((RestourantDaoImpl) restourantDao).getDish(id);
        return new ResponseEntity<Dish>(d, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)  
	public String sayHello(@PathVariable String name) {  
		final Dish d = ((RestourantDaoImpl) restourantDao).getDish(1);
		String result = "Greeting: " + name + "!!! Dish is: " + d;    
		return result;  
	} 
}