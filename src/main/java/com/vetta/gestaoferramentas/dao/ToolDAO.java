package com.vetta.gestaoferramentas.dao;

import java.util.List;

import com.vetta.gestaoferramentas.model.Tool;

public interface ToolDAO extends BaseDAO<Tool> {

	public List<Tool> listAllToolsOrderByName();
	public Tool findToolById(Long id);
	public List<Tool> findToolsByTitle(String toolTitle);
}
