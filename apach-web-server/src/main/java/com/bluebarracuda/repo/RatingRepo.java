package com.bluebarracuda.repo;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bluebarracuda.model.Rating;



/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@Repository("ratingRepo")
@Transactional
public class RatingRepo {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private SessionFactory sesFact;

	/**
	 * 
	 */
	public RatingRepo() {
	}
	

	/**
	 * @param sesFact
	 */
	@Autowired
	public RatingRepo(SessionFactory sesFact) {
		super();
		this.sesFact = sesFact;
	}
	
	/**
	 * @param rate
	 */
	public void insert(Rating rate) {
		
		sesFact.getCurrentSession().save(rate);
	}
	
	/**
	 * @param rate
	 */
	public void update(Rating rate) {
		sesFact.getCurrentSession().update(rate);
	}
	
	/**
	 * @param rate
	 */
	public void delete(Rating rate) {
		sesFact.getCurrentSession().delete(rate);;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public Rating selectById(int id) {
		return sesFact.getCurrentSession().get(Rating.class, id);
	}
	
	/**
	 * @return
	 */
	public List<Rating> selectAll(){
		return sesFact.getCurrentSession().createQuery("from Rating", Rating.class).list();
	}
	
}
