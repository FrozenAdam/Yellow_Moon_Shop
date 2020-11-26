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
public class OrderDTO {

    private String uid, username, date, fullname, phone, address, paymentMethod;
    private float price;
    private boolean status;

    public OrderDTO(String username, float price, String fullname, String phone, String address, String paymentMethod, boolean status) {
        this.username = username;
        this.price = price;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public OrderDTO(String uid, String username, float price, String date, String fullname, String phone, String address, String paymentMethod, boolean status) {
        this.uid = uid;
        this.username = username;
        this.price = price;
        this.date = date;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
