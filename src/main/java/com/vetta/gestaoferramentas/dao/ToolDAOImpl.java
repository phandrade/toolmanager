package com.vetta.gestaoferramentas.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.vetta.gestaoferramentas.model.Tool;

@Repository
public class ToolDAOImpl extends BaseDAOImpl<Tool> implements ToolDAO {
	

	public ToolDAOImpl() {
		setClazz(Tool.class);
	}

	@Override
	public List<Tool> listAllToolsOrderByName() {
		
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tool> cq = cb.createQuery(Tool.class);
		Root<Tool> root = cq.from(Tool.class);
		
		cq.orderBy(cb.asc(root.get("title")));		
		
		Query<Tool> query = getCurrentSession().createQuery(cq);
		
		return query.getResultList();
	}

	@Override
	public Tool findToolById(Long id) {
		
		if(id == null) {
			return null;
		}

		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tool> cq = cb.createQuery(Tool.class);
		Root<Tool> root = cq.from(Tool.class);
		
		Predicate toolById = cb.equal(root.get("id"), id);
		cq.where(toolById);
		Query<Tool> query = getCurrentSession().createQuery(cq);
		List<Tool> toolList = query.getResultList();
		return toolList == null || toolList.isEmpty() ? null : toolList.get(0);
	}

	@Override
	public List<Tool> findToolsByTitle(String toolTitle) {

		if(toolTitle == null) {			
			return listAllToolsOrderByName();
		}
		
		CriteriaBuilder cb = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Tool> cq = cb.createQuery(Tool.class);
		Root<Tool> root = cq.from(Tool.class);
		
		Predicate toolByTitle = cb.equal(root.get("title"), toolTitle);
		cq.where(toolByTitle);		
		
		Query<Tool> query = getCurrentSession().createQuery(cq);
		return query.getResultList();		
	}
}
