package com.dto;

public class BookingRequest {
    private Long trainId;
    private int  requiredSeats  ; 
    public int getRequiredSeats() {
    	return this.requiredSeats;
    }
    public void setRequiredSeats( int requiredSeats) { 
    	this.requiredSeats = requiredSeats ; 
    }
    // Getter and Setter
    public Long getTrainId() {
        return trainId;
    }
    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }
}
