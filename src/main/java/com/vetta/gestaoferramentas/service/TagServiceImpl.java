package com.vetta.gestaoferramentas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetta.gestaoferramentas.dao.TagDAO;
import com.vetta.gestaoferramentas.model.Tag;
import com.vetta.gestaoferramentas.model.Tool;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagDAO tagDAO;
	
	@Override
	public Tag createTag(Tag tag) {
		tagDAO.createObject(tag);
		return tag;
	}

	@Override
	public List<Tag> listallTags() {
		return tagDAO.listaAllTagsByName();
	}

	@Override
	public void removeTagById(Long id) {
		
		Tag tag = tagDAO.findTagById(id);		
		if(tag != null) {			
			tagDAO.removeObject(tag);
		}
	}

	@Override
	public List<Tool> findToolsByTagName(String name) {
		return tagDAO.findToolsByTagName(name);
	}
}
