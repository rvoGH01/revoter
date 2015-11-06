package com.revoter.rest.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revoter.model.Restaurant;
import com.revoter.model.Vote;
import com.revoter.rest.exception.ResourceNotFoundException;
import com.revoter.rest.repository.RestaurantRepository;
import com.revoter.rest.repository.VoteRepository;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController extends AbstractRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private VoteRepository voteRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Iterable<Restaurant>> getAllRestaurants() {
		LOGGER.info("GET /api/restaurants");
		Iterable<Restaurant> allRestaurants = restaurantRepository.findAll();
		return new ResponseEntity<Iterable<Restaurant>>(allRestaurants, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{restaurantId}", method=RequestMethod.GET)
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
		LOGGER.info("GET /api/restaurants/{}", restaurantId);
		
		Restaurant restaurant = restaurantRepository.findOne(restaurantId);
		
		if (null == restaurant) {
			LOGGER.error("Restaurant with id={} not found!", restaurantId);
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
		
		LOGGER.info("Found restaurant: {}", restaurant);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Void> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
		LOGGER.info("POST /api/restaurants; {}", restaurant);
				
		Restaurant newRestaurant = restaurantRepository.save(restaurant);
		LOGGER.info("Saved restaurant: {}", newRestaurant);
				
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newRestaurantUri = getNewResourceUri(restaurant.getId());
		LOGGER.debug("New restaurant URI: {}", newRestaurantUri);
		responseHeaders.setLocation(newRestaurantUri);
		
		return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{restaurantId}", method=RequestMethod.PUT)
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody Restaurant restaurant) {
		LOGGER.info("PUT /api/restaurants/{}; {}", restaurantId, restaurant);
				
		Restaurant currentRestaurant = restaurantRepository.findOne(restaurantId);
        
		if (null == currentRestaurant) {
			LOGGER.error("Restaurant with id={} not found!", restaurantId);
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
        
        currentRestaurant.setName(restaurant.getName());
        // TODO: how to set dishes: replace or just add missing by checking id (e.g. if dish with id=12 is missing then add it)
        currentRestaurant.setDishes(restaurant.getDishes());
        
        // Save the entity
		restaurantRepository.save(currentRestaurant);
		return new ResponseEntity<Restaurant>(currentRestaurant, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{restaurantId}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
		LOGGER.info("DELETE /api/restaurants/{}", restaurantId);
		
		Restaurant restaurant = restaurantRepository.findOne(restaurantId);
		if (null == restaurant) {
			LOGGER.error("Restaurant with id={} not found!", restaurantId);
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
		
		// in case some votes available for a restaurant then delete them first
		Iterable<Vote> iterable = voteRepository.findByRestaurant(restaurantId);
		if (iterable != null) {
			LOGGER.info("Delete vote for restaurant with id={}", restaurantId);
			voteRepository.delete(iterable);
		}
		
		restaurantRepository.delete(restaurantId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}