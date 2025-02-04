package com.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class Password {
	private PasswordEncoder passwordEncodeer;
   public String passwordEncoder(String stn)
   {
	   return passwordEncodeer.encode(stn);
   }
}
