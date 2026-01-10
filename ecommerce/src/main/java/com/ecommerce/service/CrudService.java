package com.ecommerce.service;

import java.util.List;

public interface CrudService<T> {

	public T  saveEntity(T entity);
	public T  updateEntity(T entity);
	public void  deleteEntity(Long id);
	public List<T> fetchAll();
}
