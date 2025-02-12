package com.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class TrainRequest {
	 @NotBlank(message = "Train number is required")
	    private long trainNumber;

	    @NotBlank(message = "Train name is required")
	    private String trainName;

	    @NotBlank(message = "Source station is required")
	    private String source;

	    @NotBlank(message = "Destination station is required")
	    private String destination;

	    @Positive(message = "Total seats must be greater than 0")
	    private Integer totalSeats;



	    private LocalDateTime arrivalTime;

		public TrainRequest(long trainNumber, String trainName, String source, String destination, Integer totalSeats,
				LocalDateTime arrivalTime) {
			super();
			this.trainNumber = trainNumber;
			this.trainName = trainName;
			this.source = source;
			this.destination = destination;
			this.totalSeats = totalSeats;
	
			this.arrivalTime = arrivalTime;
		}
		

		public long getTrainNumber() {
			return trainNumber;
		}


		public void setTrainNumber(long trainNumber) {
			this.trainNumber = trainNumber;
		}


		public String getTrainName() {
			return trainName;
		}


		public void setTrainName(String trainName) {
			this.trainName = trainName;
		}


		public String getSource() {
			return source;
		}


		public void setSource(String source) {
			this.source = source;
		}


		public String getDestination() {
			return destination;
		}


		public void setDestination(String destination) {
			this.destination = destination;
		}


		public Integer getTotalSeats() {
			return totalSeats;
		}


		public void setTotalSeats(Integer totalSeats) {
			this.totalSeats = totalSeats;
		}



	

		public LocalDateTime getArrivalTime() {
			return arrivalTime;
		}


		public void setArrivalTime(LocalDateTime arrivalTime) {
			this.arrivalTime = arrivalTime;
		}

		@Override
		public String toString() {
			return "TrainRequest [trainNumber=" + trainNumber + ", trainName=" + trainName + ", source=" + source
					+ ", destination=" + destination + ", totalSeats=" + totalSeats 
					+ ", arrivalTime=" + arrivalTime + "]";
		}
	    
	    
}
