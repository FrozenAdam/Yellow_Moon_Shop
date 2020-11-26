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
import system.dtos.LogDTO;

/**
 *
 * @author Admin
 */
public class LogDAO implements Serializable {

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

    public void insertLog(LogDTO dto) throws Exception {
        try {
            openConnection();
            String sql = "INSERT Logs(Username, productID, Date, Action) VALUES (?, ?, GETDATE(), ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setInt(2, dto.getId());
            preStm.setString(3, dto.getAction());
            preStm.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public List<LogDTO> getAllLogs() throws Exception {
        List<LogDTO> list = null;
        list = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT Username, Logs.productID, Date, Action, Products.ProductName FROM Logs, Products WHERE Products.productID = Logs.productID";
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            while(rs.next()){
                String user = rs.getString("Username");
                int id = rs.getInt("productID");
                String date = rs.getString("Date");
                String action = rs.getString("Action");
                String productName = rs.getString("ProductName");
                LogDTO dto = new LogDTO(user, id, date, action, productName);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
