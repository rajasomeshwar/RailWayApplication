package com.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exception.AccessDeniedException;
import com.exception.InsuffentSeatsAvailableException;
import com.exception.NoSuchTrainIdFoundException;
import com.model.Booking;
import com.model.Train;
import com.model.User;
import com.repository.BookingRepository;
import com.repository.TrainRepository;
import com.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired 
    private UserRepository userRepository;

    
    @Transactional
     public Booking bookSeat(Long trainId, User user ,int want_seats) {
       
        Train train = trainRepository.findByIdForUpdate(trainId)
                .orElseThrow(() -> new NoSuchTrainIdFoundException("Train not found"));

        if (train.getAvailableSeats() < want_seats ) {
            throw new InsuffentSeatsAvailableException("No seats available for booking");
        }

       
        train.setAvailableSeats(train.getAvailableSeats() - want_seats);
        trainRepository.save(train);

        // Create and save the booking.
        Booking booking = new Booking();
        booking.setTrain(train);
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking getBookingForUser(Long bookingId, String username, boolean isAdmin) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // If the user is not admin verify the booking belongs to the current user.
        if (!isAdmin && !booking.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Not authorized to view this booking");
        }

        return booking;
    }

	public List<Booking> getBookingByUser(String username, boolean isAdmin) {
		 List<Booking> booking = bookingRepository.findByuser(userRepository.findByUsername( username).get() ) ; 

            
	        // If the user is not admin, verify the booking belongs to the current user.
	        if ( booking != null && !isAdmin && !booking.get(0).getUser().getUsername().equals(username)) {
	            throw new AccessDeniedException("Not authorized to view this booking");
	        }

	        return booking;
	}

	

	
}
