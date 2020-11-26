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
import system.dtos.DetailDTO;
import system.dtos.ProductDTO;

/**
 *
 * @author Admin
 */
public class DetailDAO implements Serializable {

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
    
    public boolean insertOrderDetail(String orderID, ProductDTO dto) throws Exception {
        boolean check = true;
        try {
            openConnection();
            String sql = "INSERT OrderDetails(OrderID, ProductID, Quantity, TotalPrice) VALUES (?, ?, ?, ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, orderID);
            preStm.setInt(2, dto.getId());
            preStm.setInt(3, dto.getCartQuantity());
            preStm.setFloat(4, dto.getPrice() * dto.getCartQuantity());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public List<DetailDTO> getOrderDetail(String orderID) throws Exception {
        List<DetailDTO> list = null;
        list = new ArrayList<>();
        DetailDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT Products.ProductName, OrderDetails.Quantity, TotalPrice FROM OrderDetails, Products WHERE OrderID = ? AND Products.productID = OrderDetails.ProductID";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, orderID);
            rs = preStm.executeQuery();
            while(rs.next()){
                String name = rs.getString("ProductName");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("TotalPrice");
                dto = new DetailDTO(name, quantity, price);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
