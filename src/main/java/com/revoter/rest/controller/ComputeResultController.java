package com.revoter.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revoter.dto.VoteCount;
import com.revoter.dto.VoteResult;
import com.revoter.model.Vote;
import com.revoter.rest.repository.VoteRepository;

@RestController
public class ComputeResultController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputeResultController.class);
	
	@Autowired
	private VoteRepository voteRepository;
	
	@RequestMapping(value="/api/restaurants/computeresult", method=RequestMethod.GET)
	public ResponseEntity<?> computeResult() {
		LOGGER.info("GET /api/restaurants/computeresult");
		
		VoteResult result = new VoteResult();
		// get votes for all the restaurants
		Iterable<Vote> allVotes = voteRepository.findAll();
		Map<Long, VoteCount> tempMap = new HashMap<Long, VoteCount>();
		int totalVotes = 0;
		
		for (Vote vote : allVotes) {
			totalVotes++;
			long restaurantId = vote.getRestaurant().getId();
			VoteCount voteCount = tempMap.get(restaurantId);
			if (voteCount == null) {
				voteCount = new VoteCount(restaurantId);
				tempMap.put(restaurantId, voteCount);
			}
			voteCount.setCount(voteCount.getCount() + 1);
		}
		
		result.setTotalVotes(totalVotes);
		result.setResults(tempMap.values());
		
		return new ResponseEntity<VoteResult>(result, HttpStatus.OK);
	}
}