/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.dtos;

/**
 *
 * @author Admin
 */
public class RegistrationDTO {

    private String username, password, fullname, address, phone, roleName, statusName;
    private int role, status;

    public RegistrationDTO(String username, String fullname, String phone, String address, int role, int status) {
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public RegistrationDTO(String username, String fullname, String phone, String address, String role, String status) {
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.roleName = role;
        this.statusName = status;
    }

    public RegistrationDTO(String username, String password, String fullname, String phone, String address, int role, int status) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
