package box;

import java.util.List;

public interface Dao<T> {
	
	public List<T>getAll();
	
	public T getById(String id);
	
	public int add(T t);
	
	public int update(String id,T t);
	
	public int deleteById(String id);
}