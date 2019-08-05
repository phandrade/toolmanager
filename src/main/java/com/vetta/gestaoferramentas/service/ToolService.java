package com.vetta.gestaoferramentas.service;

import java.util.List;

import com.vetta.gestaoferramentas.model.Tool;

public interface ToolService {

	public Tool createTool(Tool tool);
	public List<Tool> listAllTools();
	public void removeToolById(Long id);
	public List<Tool> findToolsByTitleOrTagName(String tooltitle, String tagName);
	
}
