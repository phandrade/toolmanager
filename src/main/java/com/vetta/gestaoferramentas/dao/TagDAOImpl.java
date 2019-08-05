package com.vetta.gestaoferramentas.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.vetta.gestaoferramentas.model.Tag;
import com.vetta.gestaoferramentas.model.Tool;

@Repository
public class TagDAOImpl extends BaseDAOImpl<Tag> implements TagDAO {
	
	public TagDAOImpl() {
		setClazz(Tag.class);
	}

	@Override
	public List<Tool> findToolsByTagId(Long tagId) {
		
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
		Root<Tag> root = cq.from(Tag.class);
		
		Predicate tagById = cb.equal(root.get("id"), tagId);
		cq.where(tagById);
		
		TypedQuery<Tag> query = getCurrentSession().createQuery(cq);
		List<Tag> tagList = query.getResultList();
		return tagList == null || tagList.isEmpty() ? new ArrayList<Tool>() : tagList.get(0).getTools();
	}
	
	@Override
	public List<Tool> findToolsByTagName(String name) {
		
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
		Root<Tag> root = cq.from(Tag.class);
		
		Predicate tagById = cb.equal(root.get("name"), name);
		cq.where(tagById);
		
		TypedQuery<Tag> query = getCurrentSession().createQuery(cq);
		List<Tag> tagList = query.getResultList();
		return tagList == null || tagList.isEmpty() ? new ArrayList<Tool>() : tagList.get(0).getTools();			
	}

	@Override
	public List<Tag> listaAllTagsByName() {
		
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
		Root<Tag> root = cq.from(Tag.class);
		
		cq.orderBy(cb.asc(root.get("name")));		
		
		Query<Tag> query = getCurrentSession().createQuery(cq);
		
		return query.getResultList();
	}

	@Override
	public Tag findTagById(Long id) {

		if(id == null) {
			return null;
		}

		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
		Root<Tag> root = cq.from(Tag.class);
		
		Predicate tagById = cb.equal(root.get("id"), id);
		cq.where(tagById);
		Query<Tag> query = getCurrentSession().createQuery(cq);		
		List<Tag> tagList = query.getResultList();
		return tagList == null || tagList.isEmpty() ? null : tagList.get(0);
	}

	@Override
	public Tag findTagByName(String name) {
		
		if(name == null) {
			return null;
		}

		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
		Root<Tag> root = cq.from(Tag.class);
		
		Predicate tagById = cb.equal(root.get("name"), name);
		cq.where(tagById);
		Query<Tag> query = getCurrentSession().createQuery(cq);
		List<Tag> tagList = query.getResultList();
		return tagList != null && !tagList.isEmpty() ? tagList.get(0) : null;
	}


}
