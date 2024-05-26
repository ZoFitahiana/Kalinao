package org.restaurant.app.repository;

import java.util.List;

public interface Operation <T>{
   T findById();
   List<T> findAll();
   T save(T toSave);
   T Update(T toUpdate);
   void delete(int id);
}
