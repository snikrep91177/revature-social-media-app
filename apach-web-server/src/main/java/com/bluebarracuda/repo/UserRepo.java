package com.bluebarracuda.repo;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.bluebarracuda.model.User;

/**
 * @author 	Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@Repository("userRepo")
@Transactional
public class UserRepo {
	
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
	public UserRepo() {
	}

	/**
	 * @param sesFact
	 */
	@Autowired
	public UserRepo(SessionFactory sesFact) {
		this.sesFact = sesFact;
	}
	
	/**
	 * @param user
	 */
	public void insert(User user) {		
		System.out.println("In user insert");
		sesFact.getCurrentSession().save(user);
	}
	
	/**
	 * @param user
	 */
	public void update(User user) {
		sesFact.getCurrentSession().update(user);
	}
	
	/**
	 * @param user
	 */
	public void delete(User user) {
		sesFact.getCurrentSession().delete(user);;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public User selectById(int id) {
		return sesFact.getCurrentSession().get(User.class, id);
	}
	
	/**
	 * @param username
	 * @return
	 */
	public User selectByUsername(String username) {
		System.out.println(username +" in selectByUsername");
		List<User> users = sesFact.getCurrentSession().createNativeQuery("select * from"+
				" Users where user_name='"+username+"'", User.class).list();
		
		System.out.println("users.get() returns a: " + users.get(0));
		User user = users.get(0);
		return user;
	}
	
	public User selectByEmail(String email) {
		System.out.println(email +" in selectByEmail");
		List<User> users = sesFact.getCurrentSession().createNativeQuery("select * from"+
				" Users where email='"+email+"'", User.class).list();
		System.out.println(users.toString());
		return users.get(0);
	}
	
	/**
	 * @return
	 */
	public List<User> selectAll(){
		System.out.println();
		return sesFact.getCurrentSession().createQuery("from User", User.class).list();
	}
	
	/**
	 * @param username
	 * @param password
	 * @return
	 */
	
		public User getHash(String username, String password) {			
		List<User> user = sesFact.getCurrentSession().createQuery("FROM User" 
				+ " WHERE username= '" + username
                + "' AND password = GET_USER_HASH('" 
				+ username + "', '" + password + "')", User.class).list();

        if (user.size() == 0) {
            return null;
        }  
        return user.get(0);
	}


}
