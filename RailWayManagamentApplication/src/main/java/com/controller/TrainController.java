package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.TrainRequest;
import com.dto.TrainUpdateRequest;
import com.model.Train;
import com.service.TrainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TrainController {
	@Autowired 
	 private  TrainService trainService;
	@PostMapping("/trains")
    public ResponseEntity<?> addTrain(
            @Validated @RequestBody TrainRequest request
    ) {
        return trainService.addTrain(request);
    }
	// Updating pending now
	@PostMapping("/trainUpdate") 
	public Train Update ( @RequestBody TrainUpdateRequest request  ) { 
		
		return trainService.update(request.getTrainNumber() , request.getNewAvailable() ) ;
		
	}
	// All trains
	@GetMapping("/trains")
    public ResponseEntity<?> aLLTrain(
    ) {
        return ResponseEntity.ok(trainService.allTrain());
    }
}
