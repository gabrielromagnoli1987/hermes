package model.dao;

public interface Storable<T> {
	
	public boolean create(T object);
	
	public T retrieve(T object);
	
	public boolean update(T object);
	
	public boolean delete(T object);

}
