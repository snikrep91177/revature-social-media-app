package com.bluebarracuda.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.bluebarracuda.model.User;
import com.bluebarracuda.repo.UserRepo;


@CrossOrigin(origins = "http://localhost:4200")

/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@Controller
@RequestMapping(value="/user")
public class UserController {	

	/**
	 * UserRepo is a Spring managed dependency of UserController
	 */
	private UserRepo userRepo;
	
	/**
	 * 
	 */
	public UserController() {
	}

	/**
	 * @param userRepo
	 */
	@Autowired
	public UserController(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	/**
	 * @return a List of all Users
	 */
	@GetMapping(value="/getAllUsers")
	public @ResponseBody List<User> getAllUsers(){
		System.out.println("All user controller");
		return userRepo.selectAll();
	}
	
	/**
	 * @param userId
	 * @return a specific User determined by the given userId
	 */
	@PostMapping(value="/getUserById")
	public @ResponseBody User getUserById(@RequestParam("user_id") int user_id) {
		return userRepo.selectById(user_id);
	}
	
	/**
	 * @param username
	 * @return a specific User determined by the given "usesrname"
	 */
	@PostMapping(value="/getUserByUsername")
	public @ResponseBody User getUserByUsername(@RequestParam("username") String username) {
		return userRepo.selectByUsername(username);
	}

	/**
	 * @param username
	 * @param password
	 * @return 
	 */
	@PostMapping(value="/authenticate")
	public @ResponseBody User login(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("In Auth, Username input: " + username);
		User tmp = userRepo.selectByUsername(username);
		if(tmp.getPassword() == password)
			return userRepo.selectByUsername(username);
		else return null;
	}
	

}