package com.bluebarracuda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluebarracuda.model.Rating;
import com.bluebarracuda.repo.RatingRepo;

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
@RequestMapping(value="/rating")
public class RatingController {
	

	/**
	 * RatingRepo is a Spring managed dependancy of of Rating Controller 
	 */
	private RatingRepo ratingRepo;
	
	/**
	 * 
	 */
	public RatingController() {
		
	}
	
	
	/**
	 * @param ratingRepo
	 */
	@Autowired
	public RatingController(RatingRepo ratingRepo) {
		this.ratingRepo = ratingRepo;
	}
	
	/**
	 * @return A List of all Ratings associated with a specific Post
	 */
	@GetMapping(value="/getAllRatings")
	public @ResponseBody List<Rating> getAllRating(){
		return ratingRepo.selectAll();
	}
	

	/**
	 * 
	 * @param num
	 * @return A single Rating determined by the provided "id"
	 */
	@PostMapping(value="/getRatingById")
	public @ResponseBody Rating getRatingById(@RequestParam("id") int num) {
		return ratingRepo.selectById(num);
	}
	
	/**
	 * @param num
	 * @return 
	 */
	@PostMapping(value="{num}/getRatingByUri")
	public @ResponseBody Rating getRatingByUri(@PathVariable("num") int num) {
		return ratingRepo.selectById(num);
	}

}