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

import com.bluebarracuda.model.Post;
import com.bluebarracuda.model.Rating;
import com.bluebarracuda.model.User;
import com.bluebarracuda.repo.PostRepo;
import com.bluebarracuda.repo.RatingRepo;
import com.bluebarracuda.repo.UserRepo;

/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@Controller
// @RequestMapping(value = "/post")
public class PostController {

	/**
	 * PostRepo is a Spring managed dependency of PostController 
	 */
	private PostRepo postRepo;
	/**
	 * UserRepo is a Spring managed dependency of PostController
	 */
	private UserRepo userRepo;
	private RatingRepo ratingRepo;

	/**
	 * 
	 */
	public PostController() {

	}

	/**
	 * @param postRepo
	 * @param userRepo
	 */
	@Autowired
	public PostController(PostRepo postRepo, UserRepo userRepo, RatingRepo ratingRepo) {
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.ratingRepo = ratingRepo;
	}

	/**
	 * @return A list of all Post(s) associated with a specific User
	 */
	@GetMapping(value = "/post/getAllPosts")
	public @ResponseBody List<Post> getAllPosts() {
		System.out.println("Inside Get all posts");
		List<Post> posts = postRepo.SelectAll();
		if(posts.isEmpty()) {
			posts.add(new Post());			
		}
		return posts;

	}

	/**
	 * @param postId
	 * @return A single Post determined by the provided postId
	 */
	@PostMapping(value = "/post/getPostById")
	public @ResponseBody Post getPostById(@RequestParam("postId") int postId) {
		return postRepo.selectById(postId);
	}




	/**
	 * 
	 * Calls the appropriate postRepo method in order to add a new Post from a specific User
	 * 
	 * @param postText
	 * @param userId
	 */
	@PostMapping(value = "/post/newPost")

	public @ResponseBody boolean addPost(@RequestParam("postText") String postText,

			@RequestParam("user_id") int user_id) {
		

	
		System.out.println(postText + " : " + user_id);
		User user = userRepo.selectById(user_id);
		System.out.println(user);

		if (user != null) {
			Post post = new Post();
			post.setPostText(postText);
			post.setUser(user);
			System.out.println(post);
			postRepo.insert(post);
		}	
		return true;
	}
	
	@PostMapping(value="post/likePost")
	public @ResponseBody Post likePost(@RequestParam("postId") int postId, @RequestParam("user_id") int userId) {
		Post post = postRepo.selectById(postId);
		Rating rating = new Rating();
		rating.setUser(userRepo.selectById(userId));
		rating.setPost(post);
		ratingRepo.insert(rating);
		post.addRating(rating);
		postRepo.update(post);
		return post;		
	}

}
