package model.Dao.impl;

import db.DB;
import db.DbException;
import model.Dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.rmi.server.RemoteRef;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;
    Scanner sc = new Scanner(System.in);

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO seller " +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                            "VALUES " + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            int rowsAfaccted = st.executeUpdate();
            if(rowsAfaccted > 0){
               ResultSet rs = st.getGeneratedKeys();
               if (rs.next()){
                   int id = rs.getInt(1);
                   obj.setId(id);
                   DB.closeResultSet(rs);
               }

            }
            else throw new DbException("Erros! nenhuma linha foi afetada!");

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller obj = instantiateSeller(dep, rs);
                return obj;
            }
            return null;


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(Department dep, ResultSet rs) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(dep);
        return obj;
    }

    @Override
    public List<Seller> findDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE Department.Id = ? " +
                    "Order by Name ");
            st.setInt(1, department.getId());
            rs = st.executeQuery();
            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(dep, rs);
                sellerList.add(obj);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        }

    }

    public List<Seller> findByAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "Order by Name ");
            rs = st.executeQuery();
            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instantiateSeller(dep, rs);
                sellerList.add(obj);
            }
            return sellerList;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
