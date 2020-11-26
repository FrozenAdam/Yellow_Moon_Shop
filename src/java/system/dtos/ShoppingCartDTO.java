/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.dtos;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class ShoppingCartDTO implements Serializable{

    private String user;
    private HashMap<Integer, ProductDTO> cart;
    float total;

    public String getUser() {
        return user;
    }

    public HashMap<Integer, ProductDTO> getCart() {
        return cart;
    }

    public ShoppingCartDTO() {
        this.user = "GUEST";
        this.cart = new HashMap<Integer, ProductDTO>();
        this.total = 0;
    }

    public ShoppingCartDTO(String user) {
        this.user= user;
        this.cart = new HashMap<Integer, ProductDTO>();
        this.total = 0;
    }

    public void addToCart(ProductDTO dto) throws Exception {
        if (this.cart.containsKey(dto.getId())) {
            int cartQuantity = this.cart.get(dto.getId()).getCartQuantity() + dto.getCartQuantity();
            dto.setCartQuantity(cartQuantity);
        }
        this.cart.put(dto.getId(), dto);
    }

    public void removeFromCart(int id) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }

    public void updateCart(int id, int qty) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setCartQuantity(qty);
        }
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotal() throws Exception {
        this.total = 0;
        for (ProductDTO dto : this.cart.values()) {
            this.total = this.total + dto.getPrice() * dto.getCartQuantity();
        }
        return this.total;
    }
}
