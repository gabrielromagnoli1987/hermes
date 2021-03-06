package model.dao;

import java.util.List;


public interface Storable<T> {
	
	public boolean create(T object);
	
	public T retrieve(T object);
	
	public T retrieveById(T object);
	
	public List<T> retrieveAll();
	
	public boolean update(T object);
	
	public boolean delete(T object);

	public List<T> retrieveFilteredBy(Object[] filters);

}
