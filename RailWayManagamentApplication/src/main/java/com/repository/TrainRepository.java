package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.Train;

import jakarta.persistence.LockModeType;

@Repository

public interface TrainRepository extends JpaRepository<Train, Long> {
    boolean existsByTrainNumber(long l);
   
	List<Train> findBySourceAndDestination(String source, String destination);
	Train findByTrainNumber( long trainNumber );
	
	// this for lock 
	 @Lock(LockModeType.PESSIMISTIC_WRITE)
	    @Query("SELECT t FROM Train t WHERE t.id = :id")
	    Optional<Train> findByIdForUpdate(@Param("id") Long id);
}