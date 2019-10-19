package com.bluebarracuda.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.bluebarracuda.model.User;

/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@Entity()
@Table(name="Rating")
public class Rating {	
	
	@Id
	@Column(name="rating_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ratingId;
	
	@OneToOne(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinColumn(name="post_id", nullable=false)
	private Post post;
	
	public Rating() {
		
	}

	public int getRatingId() {
		return ratingId;
	}

	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Rating [ratingId=" + ratingId + ", user=" + user + "]";
	}
	
}

