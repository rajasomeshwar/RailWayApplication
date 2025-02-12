package com.service;


import java.util.HashSet;



import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dto.LoginRequest;
import com.dto.LoginResponseDTO;
import com.exception.InvalidNameException;
import com.exception.UserAlreadyExists;
import com.exception.UserNotFoundException;
import com.exception.UserPasswordWrongException;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;


@Service
@Transactional
public class AuthService {
   
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
  
	 private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	
    private final UserRepository userRepository;

  
    private final RoleRepository roleRepository;

  
    private PasswordEncoder passwordEncoder;

 
    private AuthenticationManager authenticationManager;


    private TokenService tokenService;
@Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService
			) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		
	}

	public String register(String username, String password){
         if(!isUserValid(username))
         {
        	 throw new InvalidNameException("UserName Invalid ! ");
         }
    	// check if exists;
    	System.out.println("her ");
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").orElse(null);

        Set<Role> authorities = new HashSet<>();
      
        authorities.add(userRole);
        if(userRepository.existsByUsername(username))
        {
        	throw new UserAlreadyExists("Name is already exists ");
        }
        var userdetails= userRepository.save(new User(0, username, encodedPassword, authorities));
    
      // boolean isSent= sendCodeTomail(username);
        
       
            return "Account Created ! ";
    
      
        
	}
   
   

	
	 private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

	    private boolean isUserValid(String username) {
	        if (username == null) {
	            return false;
	        }
	        Matcher matcher = USERNAME_PATTERN.matcher(username);
	        return matcher.matches();
	    }
	public LoginResponseDTO loginUser(String username, String password){

        try{
        	  if(!isUserValid(username))
              {
             	 throw new InvalidNameException("Please enter valid UserName");
              }
        	System.out.println(" her login serivce");
        	var user=userRepository.findByUsername(username).orElse(null);
        	System.out.println(" her login serivce"+user);
        	if(user == null)
        		 throw new UserNotFoundException("UserName does not  exists! ");
        	System.out.println("x " +user);
        
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
       
            String token = tokenService.generateJwt(auth);
             // userRepository.findByUsername(username).get()
            // raise unverify Exception;
             if(token==null)
             {
            	 //password mismatch
            	 throw new UserPasswordWrongException("Password is Wrong!");
            	 
             }
            return new LoginResponseDTO(userRepository.findByUsername(username).orElseGet(null), token);

        } catch(AuthenticationException e){
        	throw new UserPasswordWrongException("Password is Wrong!");
        }
    }

	

}