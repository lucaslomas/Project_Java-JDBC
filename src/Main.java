import model.Dao.DaoFactory;
import model.Dao.SellerDao;
import model.Dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Department department = new Department();
        List<Seller> list = sellerDao.findByAll();

        for (Seller obj: list) {
            System.out.println(obj);
        }


    }

}