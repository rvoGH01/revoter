package com.revoter.rest.controller;

import java.net.URI;
import java.util.Calendar;

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
	private static final int LAST_CHANCE_TO_VOTE = 11; // 11:00 AM
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@RequestMapping(value="/api/restaurants/{restaurantId}/votes", method=RequestMethod.POST)
	public ResponseEntity<?> createVote(@PathVariable Long restaurantId, @RequestBody Vote vote) {
		System.out.println("/restaurants/" + restaurantId + "/votes; " + vote);
		
		if (restaurantId != vote.getRestaurant().getId()) {
			throw new BadRequestException("Restaurant ids in URI and JSON body are not matched!");
		}
		Restaurant r = restaurantRepository.findOne(restaurantId);
		if (r == null) {
			throw new ResourceNotFoundException("Restaurant with id " + restaurantId + " not found!");
		}
		
		long userId = vote.getUser().getId();
		Vote dbVote = voteRepository.findByUser(userId);
		
		if (null == dbVote) {
			System.out.println("User with id=" + userId + " doesn't have any votes!");
			
			vote = voteRepository.save(vote);
			System.out.println("Saved vote: " + vote);
			
			// Set the location header for the newly created resource
			HttpHeaders responseHeaders = new HttpHeaders();
			URI newVoteUri = getNewResourceUri(vote.getId());
			System.out.println("NEW VOTE URI: " + newVoteUri);
			responseHeaders.setLocation(newVoteUri);
			
			return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);
		} else {
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
			System.out.println("CURR HOUR: " + hour);
			
			// update vote if it is before 11:00
			if (hour < LAST_CHANCE_TO_VOTE) {
				dbVote.setRestaurant(vote.getRestaurant());
				// dbVote.setUser(vote.getUser()); // is it required??
				voteRepository.save(dbVote);
			} else {
				System.out.println("It is too late to change your mind. Voting is allowed up to " + LAST_CHANCE_TO_VOTE + "AM");
			}
			
			return new ResponseEntity<Vote>(dbVote, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/api/restaurants/{restaurantId}/votes", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Vote>> getAllVotes(@PathVariable Long restaurantId) {
		System.out.println("GET /restaurants/" + restaurantId + "/votes");
		Iterable<Vote> allVotes = voteRepository.findByRestaurant(restaurantId);
		return new ResponseEntity<Iterable<Vote>>(allVotes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/restaurants/{restaurantId}/votes/{voteId}", method=RequestMethod.GET)
	public ResponseEntity<Vote> getVote(@PathVariable Long restaurantId, @PathVariable Long voteId) {
		System.out.println("GET /restaurants/" + restaurantId + "/votes/" + voteId);
		Vote vote = voteRepository.findOne(voteId);
		System.out.println("RESULT: " + vote);
		
		if (vote.getRestaurant().getId() != restaurantId) {
			throw new BadRequestException("Restaurant ids in URI and JSON body are not matched!");
		}
		
		return new ResponseEntity<Vote>(vote, HttpStatus.OK);
	}
}