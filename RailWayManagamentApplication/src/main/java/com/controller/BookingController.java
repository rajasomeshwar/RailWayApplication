package com.controller;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.BookingRequest;
import com.exception.TokenExpiredLoginAgainException;
import com.model.Booking;
import com.model.User;
import com.repository.UserRepository;
import com.service.AuthService;
import com.service.BookingService;
import com.service.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/userx")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userService; // Assuming you have a service to retrieve user details

    /**
     * Endpoint to book a seat on a train.
     * Expects a valid trainId in the request body.
     *
     * @param bookingRequest contains the trainId to book a seat on.
     * @param principal the authenticated user's details.
     * @return the Booking record if successful.
     */
    @PostMapping("/bookings")
    public ResponseEntity<?> bookSeat(@RequestBody BookingRequest bookingRequest, Principal principal) {
        try {
            // Retrieve the authenticated user.
        	System.out.println("UEser Enter ");
            Optional<User> user = userService.findByUsername(principal.getName());
           if( user.isEmpty() || user == null ) {
        	   throw new TokenExpiredLoginAgainException("In valid JWT Token ! please Login Again ");
           }
            // Attempt to book the seat.
            Booking booking = bookingService.bookSeat(bookingRequest.getTrainId(), user.get() , bookingRequest.getRequiredSeats());
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle exceptions such as "No seats available"
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable Long bookingId, Principal principal) {
        try {
   
            String username = principal.getName();
            // Check if the current user has an admin role.
            boolean isAdmin = isAdmin(username);

            Booking booking = bookingService.getBookingForUser(bookingId, username, isAdmin);
            return new ResponseEntity<>(booking, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @GetMapping("/bookingsAll")
    public ResponseEntity<?> getBooking(Principal principal) {
        try {
            String username = principal.getName();
            // Check if the current user has an admin role.
            boolean isAdmin = isAdmin(username);
 
            java.util.List<Booking> booking = bookingService.getBookingByUser( username, isAdmin);
            return new ResponseEntity<>(booking, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    public boolean isAdmin(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
    }
}
