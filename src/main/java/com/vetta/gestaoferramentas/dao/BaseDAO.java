package com.vetta.gestaoferramentas.dao;

import java.util.List;

public interface BaseDAO<T> {

	public List<T> listAllObjects();	
	public void createObject(T object);
	public void removeObject(T object);
	public void updateObject(T object);
}
