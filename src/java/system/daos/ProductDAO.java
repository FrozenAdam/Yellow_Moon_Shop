/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import system.dtos.ProductDTO;

/**
 *
 * @author Admin
 */
public class ProductDAO implements Serializable {

    private Connection con;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void openConnection() throws Exception {
        Context ctx = new InitialContext();
        Context envCtx = (Context) ctx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("DBCon");
        con = ds.getConnection();
    }

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public boolean createProduct(ProductDTO dto) throws Exception {
        boolean check = false;
        try {
            openConnection();
            String sql = "INSERT INTO Products(ProductName, ProductDescription, ProductPrice, quantity, ProductImage, CreatedDate, ExpiredDate, CategoryID, StatusID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setString(2, dto.getDescription());
            preStm.setFloat(3, dto.getPrice());
            preStm.setInt(4, dto.getQuantity());
            preStm.setString(5, dto.getImage());
            preStm.setString(6, dto.getCreatedDate());
            preStm.setString(7, dto.getExpiredDate());
            preStm.setInt(8, dto.getCategoryID());
            preStm.setInt(9, dto.getStatusID());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateProduct(ProductDTO dto, int id) throws Exception {
        boolean check = false;
        try {
            openConnection();
            String sql = "UPDATE Products SET ProductName = ?, ProductDescription = ?, ProductPrice = ?, quantity = ?, ProductImage = ?, CreatedDate = ?, ExpiredDate = ?, CategoryID = ?, StatusID = ? WHERE ProductID = ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setString(2, dto.getDescription());
            preStm.setFloat(3, dto.getPrice());
            preStm.setInt(4, dto.getQuantity());
            preStm.setString(5, dto.getImage());
            preStm.setString(6, dto.getCreatedDate());
            preStm.setString(7, dto.getExpiredDate());
            preStm.setInt(8, dto.getCategoryID());
            preStm.setInt(9, dto.getStatusID());
            preStm.setInt(10, id);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<ProductDTO> getAllProduct(String name, int page) throws Exception {
        List<ProductDTO> list = null;
        list = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT * FROM (\n"
                    + "		SELECT ROW_NUMBER() OVER (ORDER BY Products.CreatedDate DESC) AS rownumber, \n"
                    + "		Products.productID, Products.ProductName, Products.ProductDescription, Products.ProductPrice, Products.quantity, Products.ProductImage, Products.CreatedDate, Products.ExpiredDate,\n"
                    + "		Products.CategoryID, Products.StatusID, Categories.CategoryName, tblStatus.StatusName\n"
                    + "		FROM Products, Categories, tblStatus WHERE ProductName LIKE ? AND Products.CategoryID = Categories.CategoryID AND Products.StatusID = tblStatus.StatusID) AS tblPaging WHERE rownumber > ? AND rownumber <= ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, "%" + name + "%");
            preStm.setInt(2, page * 3 - 3);
            preStm.setInt(3, page * 3);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("productID");
                String ProductName = rs.getString("ProductName");
                String description = rs.getString("ProductDescription");
                float price = rs.getFloat("ProductPrice");
                int quantity = rs.getInt("quantity");
                String img = rs.getString("ProductImage");
                String created = rs.getString("CreatedDate");
                String expired = rs.getString("ExpiredDate");
                int categoryid = rs.getInt("CategoryID");
                int status = rs.getInt("StatusID");
                ProductDTO dto = new ProductDTO(id, ProductName, description, price, quantity, img, created, expired, categoryid, status);
                dto.setCategoryName(rs.getString("CategoryName"));
                dto.setStatusName(rs.getString("StatusName"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public ProductDTO getProduct(int searchid) throws Exception {
        ProductDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT productID, ProductName, ProductDescription, ProductPrice, quantity, ProductImage, CreatedDate, ExpiredDate, CategoryID, StatusID FROM Products WHERE productID = ?";
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, searchid);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("productID");
                String ProductName = rs.getString("ProductName");
                String description = rs.getString("ProductDescription");
                float price = rs.getFloat("ProductPrice");
                int quantity = rs.getInt("quantity");
                String img = rs.getString("ProductImage");
                String created = rs.getString("CreatedDate");
                String expired = rs.getString("ExpiredDate");
                int categoryid = rs.getInt("CategoryID");
                int status = rs.getInt("StatusID");
                dto = new ProductDTO(id, ProductName, description, price, quantity, img, created, expired, categoryid, status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public List<ProductDTO> getProductForUser(int page) throws Exception {
        List<ProductDTO> list = null;
        list = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT * FROM (\n"
                    + "		SELECT ROW_NUMBER() OVER (ORDER BY Products.CreatedDate DESC) AS rownumber, \n"
                    + "		Products.productID, ProductName, ProductDescription, ProductPrice, quantity, ProductImage, CreatedDate, ExpiredDate, Products.CategoryID, Products.StatusID, Categories.CategoryName\n"
                    + "		FROM Products, Categories WHERE Products.CategoryID = Categories.CategoryID AND ExpiredDate > GETDATE() AND (Products.StatusID = 0 OR Products.StatusID = 1) AND quantity > 0) AS tblPaging WHERE rownumber > ? AND rownumber <= ?";
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, page * 4 - 4);
            preStm.setInt(2, page * 4);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String ProductName = rs.getString("ProductName");
                String description = rs.getString("ProductDescription");
                float price = rs.getFloat("ProductPrice");
                int quantity = rs.getInt("quantity");
                String img = rs.getString("ProductImage");
                String created = rs.getString("CreatedDate");
                String expired = rs.getString("ExpiredDate");
                int categoryid = rs.getInt("CategoryID");
                int status = rs.getInt("StatusID");
                ProductDTO dto = new ProductDTO(productID, ProductName, description, price, quantity, img, created, expired, categoryid, status);
                dto.setCategoryName(rs.getString("CategoryName"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<ProductDTO> searchProductForUser(String name, float min, float max, int category, int page) throws Exception {
        List<ProductDTO> list = null;
        list = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT * FROM (\n"
                    + "		SELECT ROW_NUMBER() OVER (ORDER BY Products.CreatedDate DESC) AS rownumber, \n"
                    + "		Products.productID, ProductName, ProductDescription, ProductPrice, quantity, ProductImage, CreatedDate, ExpiredDate, Products.CategoryID, Products.StatusID, Categories.CategoryName\n"
                    + "		FROM Products, Categories WHERE Products.ProductName LIKE ? AND (ProductPrice >= ? AND ProductPrice <= ?) AND Products.CategoryID = ? AND Products.CategoryID = Categories.CategoryID AND ExpiredDate > GETDATE() AND (Products.StatusID = 0 OR Products.StatusID = 1) AND quantity > 0) AS tblPaging WHERE rownumber > ? AND rownumber <= ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, "%" + name + "%");
            preStm.setFloat(2, min);
            preStm.setFloat(3, max);
            preStm.setInt(4, category);
            preStm.setInt(5, page * 4 - 4);
            preStm.setInt(6, page * 4);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("productID");
                String ProductName = rs.getString("ProductName");
                String description = rs.getString("ProductDescription");
                float price = rs.getFloat("ProductPrice");
                int quantity = rs.getInt("quantity");
                String img = rs.getString("ProductImage");
                String created = rs.getString("CreatedDate");
                String expired = rs.getString("ExpiredDate");
                int categoryid = rs.getInt("CategoryID");
                int status = rs.getInt("StatusID");
                ProductDTO dto = new ProductDTO(productID, ProductName, description, price, quantity, img, created, expired, categoryid, status);
                dto.setCategoryName(rs.getString("CategoryName"));
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public void updateProductQuantity(int id, int quantity) throws Exception {
        try {
            openConnection();
            String sql = "UPDATE Products SET quantity = ? WHERE productID = ?";
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, quantity);
            preStm.setInt(2, id);
            preStm.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int countPage() throws Exception {
        int count = 0;
        try {
            openConnection();
            String query = "SELECT Count(Products.productID) AS Count FROM Products, Categories, tblStatus WHERE Products.CategoryID = Categories.CategoryID AND Products.StatusID = tblStatus.StatusID";
            preStm = con.prepareStatement(query);
            rs = preStm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("Count");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public int countPageForSearch(String name) throws Exception {
        int count = 0;
        try {
            openConnection();
            String query = "SELECT Count(Products.productID) AS Count FROM Products, Categories, tblStatus WHERE Products.ProductName LIKE ? AND Products.CategoryID = Categories.CategoryID AND Products.StatusID = tblStatus.StatusID";
            preStm = con.prepareStatement(query);
            preStm.setString(1, "%" + name + "%");
            rs = preStm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("Count");
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public int getProductQuantity(int id) throws Exception {
        int quantity = 0;
        try {
            openConnection();
            String sql = "SELECT quantity FROM Products WHERE productID = ?";
            preStm = con.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if(rs.next()){
                quantity = rs.getInt("quantity");
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }
}
