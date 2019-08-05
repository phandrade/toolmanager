package com.vetta.gestaoferramentas.dao;

import java.util.List;

import com.vetta.gestaoferramentas.model.Tag;
import com.vetta.gestaoferramentas.model.Tool;

public interface TagDAO extends BaseDAO<Tag> {

	public List<Tool> findToolsByTagId(Long tagId);
	public List<Tag> listaAllTagsByName();
	public Tag findTagById(Long id);
	public Tag findTagByName(String name);
	List<Tool> findToolsByTagName(String name);
}
