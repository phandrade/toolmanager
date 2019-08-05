package com.vetta.gestaoferramentas.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

public abstract class BaseDAOImpl<T extends Serializable> {
	
	private Class< T > clazz;
	 
   @PersistenceContext
   private EntityManager em;
 
   public void setClazz(Class< T > clazzToSet) {
      clazz = clazzToSet;
   }
   
   protected final Session getCurrentSession(){
      return em.unwrap(Session.class);
   }

	@SuppressWarnings("unchecked")
	public List<T> listAllObjects() {		
		return getCurrentSession().createQuery( "from " + clazz.getName() ).list();
	}
	
	public void createObject(T object) {
		getCurrentSession().persist(object);
	}
	public void removeObject(T object) {
		getCurrentSession().remove(object);
	}
	public void updateObject(T object) {
		getCurrentSession().merge(object);
	}
	
}
