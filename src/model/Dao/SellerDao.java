package model.Dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj);
    void update(Seller obj);
    void deleteByld(Integer id);
    Department findByld(Integer id);
    List<Seller> findAll();
}
