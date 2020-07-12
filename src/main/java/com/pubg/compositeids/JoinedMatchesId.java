package com.pubg.compositeids;

import java.io.Serializable;


public class JoinedMatchesId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	 
    private int matchId;
    
    /**
	 * Default parameter less constructor. (DO NOT REMOVE IT)
	 */
    public JoinedMatchesId() {    }
    
    /**
   	 * Parameterized constructor to set values while using these composite ids as primary key.
   	 */
    public JoinedMatchesId(String userId, int matchId) {
        this.userId = userId;
        this.matchId = matchId;
    }
    
    @Override
    public boolean equals(Object o) {
    	
    	if (o == this) return true;
    	
    	if (!(o instanceof JoinedMatchesId)) {
            return false;
        }
    	
    	JoinedMatchesId joinedMatchesId = (JoinedMatchesId) o;
    	return joinedMatchesId.userId == userId &&	joinedMatchesId.matchId == matchId;
    }

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the matchId
	 */
	public int getMatchId() {
		return matchId;
	}

	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	@Override
	public String toString() {
		return "JoinedMatchesId [userId=" + userId + ", matchId=" + matchId + "]";
	}

}
