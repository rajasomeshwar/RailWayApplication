package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Booking;
import com.model.Train;
import com.model.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	
 

	List<Booking> findByuser(User user);
	Optional<Booking> findById( Long id);
}