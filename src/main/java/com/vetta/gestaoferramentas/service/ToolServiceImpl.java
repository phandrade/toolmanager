package com.vetta.gestaoferramentas.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetta.gestaoferramentas.dao.TagDAO;
import com.vetta.gestaoferramentas.dao.ToolDAO;
import com.vetta.gestaoferramentas.model.Tag;
import com.vetta.gestaoferramentas.model.Tool;

@Service
@Transactional
public class ToolServiceImpl implements ToolService {
	
	@Autowired
	private ToolDAO toolDAO;
	@Autowired
	private TagDAO tagDAO;

	@Override
	public Tool createTool(Tool tool) {
		
		if(tool.getTags() != null) {
			createNewTags(tool.getTags());
		}
		
		toolDAO.createObject(tool);
		return tool;
	}

	private void createNewTags(List<Tag> tags) {
		
		for (Tag tag : tags) {	
			Tag dataTag = tagDAO.findTagByName(tag.getName());
			if(dataTag == null) {				
				tagDAO.createObject(tag);
			} else {
				tag.setId(dataTag.getId());
			}
		}
		
	}

	@Override
	public List<Tool> listAllTools() {
		return toolDAO.listAllToolsOrderByName();
	}

	@Override
	public void removeToolById(Long id) {
		
		Tool tool = toolDAO.findToolById(id);
		if(tool != null) {
			toolDAO.removeObject(tool);
		}
		
	}

	@Override
	public List<Tool> findToolsByTitleOrTagName(String toolTitle, String tagName) {
		List<Tool> result = new ArrayList<>();
		
		if(toolTitle != null && !toolTitle.isEmpty()) {
			List<Tool> toolsByTitle = toolDAO.findToolsByTitle(toolTitle);
			if(toolsByTitle != null) {
				result.addAll(toolsByTitle);
			}
		} else if(tagName != null && !tagName.isEmpty()) {
			List<Tool> toolListByTagName = tagDAO.findToolsByTagName(tagName);
			if(toolListByTagName != null && !toolListByTagName.isEmpty()) {
				result.addAll(toolListByTagName);
			}
		}		
		return result;
	}

}
