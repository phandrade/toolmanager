package com.vetta.gestaoferramentas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vetta.gestaoferramentas.model.Tool;
import com.vetta.gestaoferramentas.service.ToolService;

@RestController
public class ToolController {
	
	@Autowired
	private ToolService toolService;
	
	@GetMapping("/tools")
	public HashMap<String, List<Tool>> allTools(@RequestParam(value = "tag", required = false) String tagName, @RequestParam(value = "tool", required = false) String toolTitle) {
		HashMap<String, List<Tool>> result = new HashMap<>();
		List<Tool> toolList = new ArrayList<>();
		
		if(toolTitle == null && tagName == null) {
			toolList = toolService.listAllTools();
		} else {
			toolList = toolService.findToolsByTitleOrTagName(toolTitle, tagName);
		}
		result.put("data", toolList);
		return result;
	}
	
	@PostMapping("/tools")
	public Tool newTool(@RequestBody Tool tool) {
		return toolService.createTool(tool);
	}
	
	@DeleteMapping("/tools/{id}")
	public void deleteTool(@PathVariable Long id) {
		toolService.removeToolById(id);
	}
	
}
