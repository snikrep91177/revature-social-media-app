package com.bluebarracuda.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluebarracuda.model.User;
import com.bluebarracuda.repo.UserRepo;

/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@CrossOrigin(origins = "https://localhost:4200")
@Controller
@RequestMapping(value="/auth")
public class SessionController {

	/**
	 * UserRepo is a Spring managed dependency of SessionController
	 */
	private UserRepo userRepo;

	/**
	 * @param userRepo
	 */
	public SessionController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * 
	 * Method which creates a session to persist a User while logged in 
	 * 
	 * @param session
	 */
	@GetMapping(value = "")
	public @ResponseBody void createSession(HttpSession session) {
		String name = (String) session.getAttribute("loggedName");
		System.out.println(name);

		String pass = (String) session.getAttribute("loggedPass");
		System.out.println(pass);
	}
	
	/**
	 * 
	 * Method which extracts the parameters from an HTTP request and calls the appropriate method 
	 * of the UserRepo to register a new User and hash their password
	 * 
	 * @param usernameParam
	 * @param passwordParam
	 * @param firstnameParam
	 * @param lastnameParam
	 * @param emailParam
	 */
	@PostMapping(value="/updateUserProfile")
	public @ResponseBody User updateUser(@RequestParam("username") String usernameParam,
			@RequestParam("password") String passwordParam,@RequestParam("hobbies") String hobbiesParam, @RequestParam("email") String emailParam, 
			@RequestParam("profession") String professionParam){
				
				System.out.println("in the update user method");
				String username = usernameParam;
				String password = passwordParam;
				String hobbies = hobbiesParam;
				String email = emailParam;
				String profession = professionParam;

				User newUser = new User(username, password);
				User updateUser = new User(username, password);
				updateUser.setUsername(username);
				updateUser.setHobbies(hobbies);
				updateUser.setPassword(password);
				updateUser.setEmail(email);
				updateUser.setOccupation(profession);
				System.out.println(updateUser.toString());
				userRepo.update(updateUser);
				
				return updateUser;
			}
	
	@PostMapping(value = "/registerUser")
	public @ResponseBody ResponseEntity<User> registerUser(@RequestParam("username") String usernameParam,
			@RequestParam("password") String passwordParam, @RequestParam("firstname") String firstnameParam,
			@RequestParam("lastname") String lastnameParam, @RequestParam("email") String emailParam) {

		System.out.println("in the register user method");
		String username = usernameParam;
		String password = passwordParam;
		String firstname = firstnameParam;
		String lastname = lastnameParam;
		String email = emailParam;		

		User newUser = new User(username, password);
		newUser.setEmail(email);
		newUser.setFirstName(firstname);
		newUser.setLastName(lastname);	
		System.out.println(newUser.toString());
		userRepo.insert(newUser);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
		
	}

	/**
	 * 
	 * Method logs in a User after validating username and password exist in the database
	 * 
	 * 
	 * @param username
	 * @param password
	 * @return the newly logged in user
	 */
	@PostMapping(value = "/authenticate")
	public @ResponseBody ResponseEntity<User> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		System.out.println("In Auth, Username input: " + username);
		User tmp = userRepo.selectByUsername(username);

		System.out.println("In Auth, Username input: " + username);	
		User user = userRepo.getHash(username, password);
		if (user == null) {
			return  new ResponseEntity<User>(new User(), HttpStatus.UNAUTHORIZED);
		}		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
