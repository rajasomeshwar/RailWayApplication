package com.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "trains")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long trainNumber;
    private String trainName ; 
    private String source;
    private String destination;
    private Integer totalSeats;
    private Integer availableSeats;
    

    private LocalDateTime arrivalTime;
    public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	

	public Train() {
    	
    }
    
   

	public Train(Long id, long trainNumber, String trainName, String source, String destination, Integer totalSeats,
			Integer availableSeats, LocalDateTime arrivalTime, Long version) {
		super();
		this.id = id;
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.source = source;
		this.destination = destination;
		this.totalSeats = totalSeats;
		this.availableSeats = availableSeats;
		
		this.arrivalTime = arrivalTime;
		this.version = version;
	}



	@Version
    private Long version;


	public Long getId() {
		return id;
	}

   public void setTrainNumber( long number ) {
	   this.trainNumber = number;
   }
   public long getTrainNumber() {
	   return this.trainNumber ; 
   }
   
	public void setId(Long id) {
		this.id = id;
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


	public Integer getAvailableSeats() {
		return availableSeats;
	}


	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}


	public Long getVersion() {
		return version;
	}


	public void setVersion(Long version) {
		this.version = version;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		// TODO Auto-generated method stub
		
	}
}