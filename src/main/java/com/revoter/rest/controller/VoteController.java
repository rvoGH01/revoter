package com.revoter.rest.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revoter.model.Vote;
import com.revoter.rest.repository.VoteRepository;

@RestController
public class VoteController extends AbstractRestController {

	@Autowired
	private VoteRepository voteRepository;
	
	@RequestMapping(value="/restaurants/{restaurantId}/votes", method=RequestMethod.POST)
	public ResponseEntity<Void> createVote(@PathVariable Long restaurantId, @RequestBody Vote vote) {
		System.out.println("/restaurants/" + restaurantId + "/votes; " + vote);
		vote = voteRepository.save(vote);
		System.out.println("Saved vote: " + vote);
		
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newVoteUri = getNewResourceUri(vote.getId());
		System.out.println("NEW VOTE URI: " + newVoteUri);
		responseHeaders.setLocation(newVoteUri);
		
		return new ResponseEntity<Void>(responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/restaurants/{restaurantId}/votes", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Vote>> getAllVotes(@PathVariable Long restaurantId) {
		System.out.println("GET /restaurants/" + restaurantId + "/votes");
		Iterable<Vote> allVotes = voteRepository.findByRestaurant(restaurantId);
		return new ResponseEntity<Iterable<Vote>>(allVotes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/restaurants/{restaurantId}/votes/{voteId}", method=RequestMethod.GET)
	public ResponseEntity<Vote> getVote(@PathVariable Long restaurantId, @PathVariable Long voteId) {
		System.out.println("GET /restaurants/" + restaurantId + "/votes/" + voteId);
		Vote vote = voteRepository.findOne(voteId);
		System.out.println("RESULT: " + vote);
		//vote.getDish().get
		return new ResponseEntity<Vote>(vote, HttpStatus.OK);
	}
}