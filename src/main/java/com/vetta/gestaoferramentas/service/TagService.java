package com.vetta.gestaoferramentas.service;

import java.util.List;

import com.vetta.gestaoferramentas.model.Tag;
import com.vetta.gestaoferramentas.model.Tool;

public interface TagService {

	public Tag createTag(Tag tag);
	public List<Tag> listallTags();
	public void removeTagById(Long id);
	public List<Tool> findToolsByTagName(String name);
}
