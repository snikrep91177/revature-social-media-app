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
import com.bluebarracuda.model.Comment;
import com.bluebarracuda.repo.CommentRepo;

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
@RequestMapping(value="/comment")
public class CommentController {

	/**
	 * 	CommentRepo is a dependancy of CommentController
	 */
	private CommentRepo commentRepo;
	
	public CommentController() {
		
	}
	
	/**
	 * @param commentRepo
	 */
	@Autowired
	public CommentController(CommentRepo commentRepo) {
		this.commentRepo = commentRepo;
	}
	
	/**
	 * @return A list of Comment(s) from User(s) associated with a single Post
	 */
	@GetMapping(value="/getAllComments")
	public @ResponseBody List<Comment> getAllComment(){
		return commentRepo.selectAll();
	}
	

	/**
	 * @param num
	 * @return A single Comment determined by the provided "id"
	 */
	@PostMapping(value="/getCommentById")
	public @ResponseBody Comment getCommentById(@RequestParam("id") int num) {
		return commentRepo.selectById(num);
	}
	
}