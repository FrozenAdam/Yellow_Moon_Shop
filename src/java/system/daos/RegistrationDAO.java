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
import system.dtos.RegistrationDTO;

/**
 *
 * @author Admin
 */
public class RegistrationDAO implements Serializable {

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

    public RegistrationDTO checkLogin(String user, String pass) throws Exception {
        RegistrationDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT Username, Fullname, Phone, Address, RoleID, StatusID FROM Account WHERE Username = ? and Password = ?";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, user);
            preStm.setString(2, pass);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String username = rs.getString("Username");
                String fullname = rs.getString("Fullname");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                int role = rs.getInt("RoleID");
                int status = rs.getInt("StatusID");
                dto = new RegistrationDTO(username, fullname, phone, address, role, status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean registerAccount(RegistrationDTO dto) throws Exception {
        boolean check = false;
        try {
            openConnection();
            String sql = "INSERT INTO Account(Username, Password, Fullname, Phone, Address, RoleID, StatusID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preStm = con.prepareStatement(sql);
            preStm.setString(1, dto.getUsername());
            preStm.setString(2, dto.getPassword());
            preStm.setString(3, dto.getFullname());
            preStm.setString(4, dto.getPhone());
            preStm.setString(5, dto.getAddress());
            preStm.setInt(6, dto.getRole());
            preStm.setInt(7, dto.getStatus());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<RegistrationDTO> getAllUsers() throws Exception {
        List<RegistrationDTO> list = new ArrayList<>();
        RegistrationDTO dto = null;
        try {
            openConnection();
            String sql = "SELECT Username, Fullname, Phone, [Address], Roles.RoleName, tblStatus.StatusName \n"
                    + "FROM Account, Roles, tblStatus \n"
                    + "WHERE Roles.RoleID = Account.RoleID AND Account.StatusID = tblStatus.StatusID AND (Account.StatusID = 0 OR Account.StatusID = 1)";
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            while(rs.next()){
                String username = rs.getString("Username");
                String fullname = rs.getString("Fullname");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String roleName = rs.getString("RoleName");
                String statusName = rs.getString("StatusName");
                dto = new RegistrationDTO(username, fullname, phone, address, roleName, statusName);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
