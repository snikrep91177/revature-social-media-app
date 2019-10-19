package com.bluebarracuda.repo;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bluebarracuda.model.Comment;

/**
 * @author  Arnold C. Sinko
 * 			Jacob Shanklin
 * 			Graham L Tyree
 * 			Mert Altun
 * 			Michael G. Perkins
 *
 */
@Repository("commentRepo")
@Transactional
public class CommentRepo {
		
		static {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * SessionFactory is a Spring managed dependancy of CommentRepo
		 */
		private SessionFactory sesFact;

		/**
		 * 
		 */
		public CommentRepo() {
		}
		

		/**
		 * @param sesFact
		 */
		@Autowired
		public CommentRepo(SessionFactory sesFact) {
			super();
			this.sesFact = sesFact;
		}
		
		/**
		 * @param com
		 */
		public void insert(Comment com) {
			
			sesFact.getCurrentSession().save(com);
		}
		
		/**
		 * @param com
		 */
		public void update(Comment com) {
			sesFact.getCurrentSession().update(com);
		}
		
		/**
		 * @param com
		 */
		public void delete(Comment com) {
			sesFact.getCurrentSession().delete(com);;
		}
		
		/**
		 * @param id
		 * @return
		 */
		public Comment selectById(int id) {
			return sesFact.getCurrentSession().get(Comment.class, id);
		}
		
		/**
		 * @return
		 */
		public List<Comment> selectAll(){
			return sesFact.getCurrentSession().createQuery("from Comment", Comment.class).list();
		}
		


}
