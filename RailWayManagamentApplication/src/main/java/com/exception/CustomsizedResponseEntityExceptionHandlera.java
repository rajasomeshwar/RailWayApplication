package com.exception;

import java.time.LocalDateTime;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;





@ControllerAdvice
public class CustomsizedResponseEntityExceptionHandlera extends ResponseEntityExceptionHandler {
 
   @ExceptionHandler(UserAlreadyExists.class)
   public final ResponseEntity<ErrorDetails> HandleAlreadyVertifiedEmailException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   //@ExceptionHandler(TokenInvalidException.class)
   public final ResponseEntity<ErrorDetails> HandleTokenInvalidException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
 @ExceptionHandler(InvalidNameException.class)
   public final ResponseEntity<ErrorDetails> HandleNameException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   
  // @ExceptionHandler(MailNotFoundException.class)
   public final ResponseEntity<ErrorDetails> HandleMailNotFoundException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(InvalidCodeException.class)
   public final ResponseEntity<ErrorDetails> handleInvalidCodeException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(UserNotFoundException.class)
   public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   @ExceptionHandler(UserPasswordWrongException.class)
   public final ResponseEntity<ErrorDetails> handleUserPasswordWrongException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   
   @ExceptionHandler(InvalidTimeException.class)
   public final ResponseEntity<ErrorDetails> handleInvalidTimeException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   
   @ExceptionHandler(DuplicateTrainException.class)
   public final ResponseEntity<ErrorDetails> handleDuplicateTrainException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   
   @ExceptionHandler(NoTrainThereInThatPathException.class)
   public final ResponseEntity<ErrorDetails> handleNoTrainThereInThatPathException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   
   @ExceptionHandler(NoSuchTrainIdFoundException.class)
   public final ResponseEntity<ErrorDetails> handleNoSuchTrainIdFoundException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
   }
   
   @ExceptionHandler(InsuffentSeatsAvailableException.class)
   public final ResponseEntity<ErrorDetails> handleInsuffentSeatsAvailableException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
    }
   
   @ExceptionHandler(TokenExpiredLoginAgainException.class)
   public final ResponseEntity<ErrorDetails> handleTokenExpiredLoginAgainException(Exception ex,WebRequest request)
   {
	   var error=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
	   return new ResponseEntity<ErrorDetails>(error,HttpStatus.NOT_FOUND);
    }
}