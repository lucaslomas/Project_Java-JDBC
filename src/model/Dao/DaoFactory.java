package model.Dao;

import db.DB;
import model.Dao.impl.SellerDaoJDBC;

import java.sql.Connection;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
        }
    }
