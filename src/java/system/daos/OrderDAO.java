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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import system.dtos.OrderDTO;

/**
 *
 * @author Admin
 */
public class OrderDAO implements Serializable{

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
    
    public String getOrderID(String name) throws Exception {
        String id = null;
        try {
            openConnection();
            String sql = "SELECT OrderID FROM Orders WHERE Username LIKE ? OR (Fullname LIKE ? AND Fullname LIKE ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, "%" + name + "%");
            preStm.setString(2, "" + name + "");
            preStm.setString(3, "%" + name + "%");
            rs = preStm.executeQuery();
            if(rs.next()){
                id = rs.getString("OrderID");
            }
        } finally {
            closeConnection();
        }
        return id;
    }
    
    public boolean insertOrder(OrderDTO dto) throws Exception {
        boolean check = false;
        try {
            openConnection();
            String sql = "INSERT Orders(Username, TotalPrice, OrderDate, Fullname, Phone, OrderAddress, PaymentMethod, OrderStatus) VALUES (?, ?, GETDATE(), ?, ?, ?, ?, ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getUsername());
            preStm.setFloat(2, dto.getPrice());
            preStm.setString(3, dto.getFullname());
            preStm.setString(4, dto.getPhone());
            preStm.setString(5, dto.getAddress());
            preStm.setString(6, dto.getPaymentMethod());
            preStm.setBoolean(7, dto.isStatus());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public OrderDTO getOrderByID(String id) throws Exception {
        OrderDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT OrderID, Username, TotalPrice, OrderDate, Fullname, Phone, OrderAddress, PaymentMethod, OrderStatus FROM Orders WHERE OrderID = ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();
            if(rs.next()){
                String orderID = rs.getString("OrderID");
                String username = rs.getString("Username");
                float price = rs.getFloat("TotalPrice");
                String date = rs.getString("OrderDate");
                String fullname = rs.getString("Fullname");
                String phone = rs.getString("Phone");
                String address = rs.getString("OrderAddress");
                String payment = rs.getString("PaymentMethod");
                boolean status = rs.getBoolean("OrderStatus");
                dto = new OrderDTO(id, username, price, date, fullname, phone, address, payment, status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
