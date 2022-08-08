package interfaces;

import java.util.List;

public interface DataRepository<T> {

    public T getById(String id);

    public void delete(String id);

    public T save(T obj);

    public void deleteAll();

    public List<T> getAll();
}
