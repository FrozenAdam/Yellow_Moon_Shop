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
import system.dtos.StatusDTO;

/**
 *
 * @author Admin
 */
public class StatusDAO implements Serializable {

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

    public List<StatusDTO> getAllStatus() throws Exception {
        List<StatusDTO> list = null;
        list = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT StatusID, StatusName FROM tblStatus";
            preStm = con.prepareStatement(sql);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("StatusID");
                String name = rs.getString("StatusName");
                StatusDTO dto = new StatusDTO(id, name);
                list.add(dto);
            }
        } finally {
            closeConnection();
        }
        return list;
    }
}
