package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dto.TrainRequest;
import com.exception.DuplicateTrainException;
import com.exception.InvalidTimeException;
import com.exception.NoSuchTrainIdFoundException;
import com.exception.NoTrainThereInThatPathException;
import com.model.Train;
import com.repository.TrainRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainService {
   @Autowired
   private  TrainRepository trainRepository ;
   

    
    public ResponseEntity<?> addTrain(TrainRequest request ) {
     

        // Validate train timings
        if ( request.getArrivalTime() != null
            ) {
            throw new InvalidTimeException("Invalid Time of Train please Fix it ");
        }

       
        if (trainRepository.existsByTrainNumber(request.getTrainNumber())) {
        	
            throw new DuplicateTrainException("Train with number " + request.getTrainNumber() + " already exists");
        }

        Train train = new Train();
        train.setTrainNumber(request.getTrainNumber());
        train.setTrainName(request.getTrainName());
        train.setSource(request.getSource());
        train.setDestination(request.getDestination());
        train.setTotalSeats(request.getTotalSeats());
        train.setAvailableSeats(request.getTotalSeats());
        
        train.setArrivalTime(request.getArrivalTime());

        Train savedTrain = trainRepository.save(train);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Train added successfully");
        response.put("trainId", savedTrain.getId());
        response.put("trainNumber", savedTrain.getTrainNumber());
        response.put("trainDetails", savedTrain);

        return ResponseEntity.ok(response);
    }



	public List<Train> findTrainsByRoute(String source, String destination) {
		// TODO Auto-generated method stub
		
		 List<Train> data =  trainRepository.findBySourceAndDestination(source, destination);
		 if( data== null || data.isEmpty()  ) 
			 throw new NoTrainThereInThatPathException(" Sorry ! we will Add it soon !");
		 return data ; 
	}



	public Train update(int trainNumber, int newAvailable) {
		// TODO Auto-generated method stub
		Train data = trainRepository.findByTrainNumber( trainNumber ) ;
		 // already booked is greater 
		// the value is increaments 
		data = trainRepository.findByIdForUpdate( data.getId() ).orElse( null );
		if( data == null ) {
			throw new NoSuchTrainIdFoundException( "Train Number is Invalid ");
		}
		data.setAvailableSeats( data.getAvailableSeats() + newAvailable );
		data.setTotalSeats( data.getTotalSeats() + newAvailable);
		return data ;
	}



	public List<Train> allTrain() {
		// TODO Auto-generated method stub
		
		return trainRepository.findAll();
	}
 
}
