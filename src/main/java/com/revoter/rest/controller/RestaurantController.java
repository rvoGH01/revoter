package com.revoter.rest.controller;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.revoter.model.Dish;
import com.revoter.model.Restaurant;
import com.revoter.rest.exception.ResourceNotFoundException;
import com.revoter.rest.repository.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@RequestMapping
	public ResponseEntity<Iterable<Restaurant>> getAllRestaurants() {
		System.out.println("Get all available restaurants.");
		Iterable<Restaurant> allRestaurants = restaurantRepository.findAll();
		return new ResponseEntity<Iterable<Restaurant>>(allRestaurants, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{restaurantId}", method=RequestMethod.GET)
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
		System.out.println("GET /restaurants/" + restaurantId);
		Restaurant restaurant = restaurantRepository.findOne(restaurantId);
		System.out.println("RESULT: " + restaurant);
		
		if (null == restaurant) {
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
		
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Void> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
		System.out.println("POST /restaurants; " + restaurant);
				
		Restaurant newRestaurant = restaurantRepository.save(restaurant);
		System.out.println("SAVED RESTAURANT: " + newRestaurant);
		
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newRestaurantUri = getNewResourceUri(restaurant.getId());
		System.out.println("NEW RESTAURANT URI: " + newRestaurantUri);
		responseHeaders.setLocation(newRestaurantUri);
		
		return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{restaurantId}", method=RequestMethod.PUT)
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody Restaurant restaurant) {
		System.out.println("PUT /restaurants/" + restaurantId);
		
		Restaurant currentRestaurant = restaurantRepository.findOne(restaurantId);
        
		if (null == currentRestaurant) {
			System.out.println("Restaurant with id " + restaurantId + " not found!");
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
        
        currentRestaurant.setName(restaurant.getName());
        currentRestaurant.setDishes(restaurant.getDishes());
        
        // Save the entity
		restaurantRepository.save(currentRestaurant);
		return new ResponseEntity<Restaurant>(currentRestaurant, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{restaurantId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
		System.out.println("DELETE /restaurants/" + restaurantId);
		
		Restaurant restaurant = restaurantRepository.findOne(restaurantId);
		if (null == restaurant) {
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
		
		restaurantRepository.delete(restaurantId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	public void voteRestaurant() {
		
	}
	
	private URI getNewResourceUri(Long resourceId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(resourceId).toUri();
	}
	
	
	////
	
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getRestaurant() {
        Restaurant r = new Restaurant();
        r.setName("Maestro");
        
        Set<Dish> dishes = new HashSet<Dish>();
        Dish d1 = new Dish();
        d1.setName("Soup");
        d1.setPrice(12.35f);
        dishes.add(d1);
        
        Dish d2 = new Dish();
        d2.setName("Salad");
        d2.setPrice(43.15f);
        dishes.add(d2);
        
        r.setDishes(dishes);
        
		System.out.println("TEST RESTAURANT: " + r);
		
        //final Dish d = ((RestourantDaoImpl) restourantDao).getDish(id);
        return new ResponseEntity<Restaurant>(r, HttpStatus.OK);
    }
//	
//	@RequestMapping(value = "/{name}", method = RequestMethod.GET)  
//	public String sayHello(@PathVariable String name) {  
//		//final Dish d = ((RestourantDaoImpl) restourantDao).getDish(1);
//		String result = "Greeting: " + name;    
//		return result;  
//	}
	
	@RequestMapping(value = "/{msg}")
    public String greeting(@PathVariable("msg") String msg) {
        return "Message is: " + msg;
    }
}