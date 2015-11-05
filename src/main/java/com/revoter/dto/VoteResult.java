package com.revoter.dto;

import java.util.Collection;

import com.revoter.model.BaseType;

public class VoteResult extends BaseType {
	private static final long serialVersionUID = 5268035605862755513L;
	
	private int totalVotes;
	private Collection<VoteCount> results;
	
	public int getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}
	public Collection<VoteCount> getResults() {
		return results;
	}
	public void setResults(Collection<VoteCount> results) {
		this.results = results;
	}
}