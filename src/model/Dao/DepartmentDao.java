package model.Dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department obj);
    void update(Department obj);
    void deleteByld(Integer id);
    Department findByld(Integer id);
    List<Department> findAll();


}
