package com.bluebarracuda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluebarracuda.email.MailService;
import com.bluebarracuda.model.User;
import com.bluebarracuda.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@CrossOrigin(origins="http://localhost:4200")
@Controller
public class EmailController {

		/**
		 * 	MailService is a dependancy of EmailController
		 */

		
		private User user;
		private UserRepo userRepo;
		private MailService mail;
	
	public EmailController() {
		
	}
	
	/**
	 * @param mail
	 */
	@Autowired
	public EmailController(MailService mail) {
		this.mail = mail;
	}
	
	

	/**
	 * Method called when a user clicks the forgot password button on the login screen. An auto generated email is sent to the user
	 * containing a randomly generated temporary password allowing the user to login and update their password.  
	 * 
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody void doRestPwd(@RequestParam("email") String email) throws JsonProcessingException {
	
			System.out.println("in the resetmapper");
			
			//generating random password
			String randString =  generateAlphaNumericPassword();
			
			// Creating message 
			String senderEmailId = "greenmonkeys83@gmail.com";
			String receiverEmailId = email;
			System.out.println(receiverEmailId);
			String subject = "Your Password Has Been Successfully Reset";
			String message = "It looks like "
					+ "you have forgotten your password. If that is not the case, please "
					+ "consider that someone decided to prank you a little bit. In any case, "
					+ "we suggest to change your password once you firstly log in."
					+ "\n\n Your new Password is: "+ randString;
			
			//sending email 
			mail.sendEmail(senderEmailId, receiverEmailId, subject, message);
			
			
			user = userRepo.selectByEmail(receiverEmailId);
			
		}
		
		/**
		 * called by doRestPwd. Method creates an random 8 digit alphanumeric temporary password to facilitate user password reset request  
		 * 
		 * @return 
		 */
		public static String generateAlphaNumericPassword(){
			final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			int len = 10;
			StringBuilder builder = new StringBuilder();
			while (len-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
			}
			return builder.toString().toLowerCase();
		}
		
		
	}
	


