package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.model.Train;
import com.service.TrainService;

@RestController
@RequestMapping("/api/user")
public class TrainControllerUser {
	 @Autowired
	    private TrainService trainService;

	    @GetMapping("/trains")
	    public ResponseEntity<List<Train>> getTrains(@RequestParam String source, @RequestParam String destination) {
	        List<Train> trains = trainService.findTrainsByRoute(source, destination);
	        return ResponseEntity.ok(trains);
	    }
	    
}

