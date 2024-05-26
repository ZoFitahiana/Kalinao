package org.restaurant.app.operation;

import java.util.List;

public interface CrudOperation<T>{
   T findById();
   List<T> findAll();
   T save(T toSave);
   T Update(T toUpdate);
   void delete(int id);
}
