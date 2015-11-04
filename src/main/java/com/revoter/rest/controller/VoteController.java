package com.revoter.rest.controller;

import java.net.URI;
import java.util.Calendar;

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
import com.revoter.rest.exception.BadRequestException;
import com.revoter.rest.exception.ResourceNotFoundException;
import com.revoter.rest.repository.RestaurantRepository;
import com.revoter.rest.repository.VoteRepository;

@RestController
public class VoteController extends AbstractRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);
	
	private static final int LAST_CHANCE_TO_VOTE = 11; // 11:00 AM
	
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@RequestMapping(value="/api/restaurants/{restaurantId}/votes", method=RequestMethod.POST)
	public ResponseEntity<?> createVote(@PathVariable Long restaurantId, @RequestBody Vote vote) {
		LOGGER.info("POST /api/restaurants/{}/votes; {}", restaurantId, vote);
		
		if (restaurantId != vote.getRestaurant().getId()) {
			LOGGER.error("Restaurant ids in URI ({}) and JSON body ({}) are not matched!", restaurantId, vote.getRestaurant().getId());
			throw new BadRequestException("Restaurant ids in URI and JSON body are not matched!");
		}
		Restaurant r = restaurantRepository.findOne(restaurantId);
		if (r == null) {
			LOGGER.error("Restaurant with id={} not found!", restaurantId);
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
		
		long userId = vote.getUser().getId();
		Vote dbVote = voteRepository.findByUser(userId);
		
		if (null == dbVote) {
			LOGGER.info("User with id={} doesn't have any votes yet!", userId);
						
			vote = voteRepository.save(vote);
			LOGGER.info("Saved vote: {}", vote);
			
			// Set the location header for the newly created resource
			HttpHeaders responseHeaders = new HttpHeaders();
			URI newVoteUri = getNewResourceUri(vote.getId());
			LOGGER.debug("New vote URI: {}", newVoteUri);
			responseHeaders.setLocation(newVoteUri);
			
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);
		} else {
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
			
			// update vote if it is before 11:00
			if (hour < LAST_CHANCE_TO_VOTE) {
				LOGGER.info("Update vote as it is before {}AM", LAST_CHANCE_TO_VOTE);
				dbVote.setRestaurant(vote.getRestaurant());
				// dbVote.setUser(vote.getUser()); // is it required??
				voteRepository.save(dbVote);
			} else {
				LOGGER.warn("It is too late to change your mind. Voting is allowed up to {}AM", LAST_CHANCE_TO_VOTE);
			}
			
			return new ResponseEntity<Vote>(dbVote, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/api/restaurants/{restaurantId}/votes", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Vote>> getAllVotes(@PathVariable Long restaurantId) {
		LOGGER.info("GET /api/restaurants/{}/votes", restaurantId);
		Iterable<Vote> allVotes = voteRepository.findByRestaurant(restaurantId);
		return new ResponseEntity<Iterable<Vote>>(allVotes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/restaurants/{restaurantId}/votes/{voteId}", method=RequestMethod.GET)
	public ResponseEntity<Vote> getVote(@PathVariable Long restaurantId, @PathVariable Long voteId) {
		LOGGER.info("GET /api/restaurants/{}/votes/{}", restaurantId, voteId);
		Vote vote = voteRepository.findOne(voteId);
		LOGGER.info("Found vote: {}", vote);
				
		if (vote.getRestaurant().getId() != restaurantId) {
			LOGGER.error("Restaurant ids in URI ({}) and JSON body ({}) are not matched!", restaurantId, vote.getRestaurant().getId());
			throw new BadRequestException("Restaurant ids in URI and JSON body are not matched!");
		}
		
		return new ResponseEntity<Vote>(vote, HttpStatus.OK);
	}
}