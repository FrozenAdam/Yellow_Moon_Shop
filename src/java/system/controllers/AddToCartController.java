/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import system.daos.ProductDAO;
import system.dtos.ProductDTO;
import system.dtos.RegistrationDTO;
import system.dtos.ShoppingCartDTO;

/**
 *
 * @author Admin
 */
public class AddToCartController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(AddToCartController.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("txtProductID"));
            ProductDAO dao = new ProductDAO();
            ProductDTO dto = dao.getProduct(id);
            dto.setCartQuantity(1);

            HttpSession session = request.getSession();
            RegistrationDTO acc = (RegistrationDTO) session.getAttribute("USER");
            ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("CART");

            if (acc != null) { //Registerd User
                if (cart == null) {
                    cart = new ShoppingCartDTO(acc.getUsername());
                }
            } else { //Guest
                if (cart == null) {
                    cart = new ShoppingCartDTO();
                }
            }

            boolean valid = true;
            if (cart.getCart().get(id) != null) {
                if (cart.getCart().get(id).getCartQuantity()+ 1 > dto.getQuantity()) {
                    valid = false;
                    request.setAttribute("WARN", "Amount product in cart exceeded in stock");
                }
            }
            if (valid) {
                cart.addToCart(dto);
                cart.setTotal(cart.getTotal());
                session.setAttribute("CART", cart);
            }
        } catch (Exception e) {
            LOGGER.error("Error at AddToCartController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("GetProductController").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
