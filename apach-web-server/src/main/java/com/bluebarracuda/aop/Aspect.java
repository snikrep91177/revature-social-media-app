package com.bluebarracuda.aop;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;

import com.bluebarracuda.controller.SessionController;

public class Aspect {
	
	@After("execution(* com.bluebarracuda.controller.Session*(ResponseEntity<User> , ..))")  
	public void logging(JoinPoint jp) {
		
		final Logger log  = Logger.getLogger(SessionController.class);
		
		log.setLevel(Level.INFO);
		
	}

	@After("execution(* com.bluebarracuda.controller.Session*(User , ..))")  
	public void loggingupdate(JoinPoint jp) {
		
		final Logger log  = Logger.getLogger(SessionController.class);
		
		log.setLevel(Level.INFO);
		
	}

}
