/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import system.daos.DetailDAO;
import system.daos.OrderDAO;
import system.daos.ProductDAO;
import system.dtos.OrderDTO;
import system.dtos.ProductDTO;
import system.dtos.RegistrationDTO;
import system.dtos.ShoppingCartDTO;

/**
 *
 * @author Admin
 */
public class ConfirmCartController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ConfirmCartController.class);
    private static final String SUCCESS = "GetProductController";
    private static final String FAIL = "cart.jsp";

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
        String url = SUCCESS;
        try {
            String paymentMethod = request.getParameter("txtPaymentMethod");
            HttpSession session = request.getSession();
            ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("CART");
            RegistrationDTO accountDTO = (RegistrationDTO) session.getAttribute("USER");
            OrderDAO orderDAO = new OrderDAO();
            ProductDAO productDAO = new ProductDAO();
            boolean valid = true;
            if (paymentMethod.isEmpty()) {
                valid = false;
            }

            //Payment
            boolean paymentStatus = false;
            if (paymentMethod.equalsIgnoreCase("Cash On Delivery")) {
                paymentStatus = false;
            } else {
                paymentStatus = true;
            }

            //Check quantity in database
            Iterator check = cart.getCart().entrySet().iterator();
            while (check.hasNext()) {
                Map.Entry entry = (Map.Entry) check.next();
                ProductDTO productDTO = (ProductDTO) entry.getValue();
                int currentQuantity = productDAO.getProductQuantity(productDTO.getId());
                if (productDTO.getQuantity() != currentQuantity) {
                    valid = false;
                    url = FAIL;
                }
            }
            if (valid) {
                if (accountDTO != null) { //Registered User
                    OrderDTO orderDTO = new OrderDTO(accountDTO.getUsername(), cart.getTotal(), accountDTO.getFullname(), accountDTO.getPhone(), accountDTO.getAddress(), paymentMethod, paymentStatus);
                    if (orderDAO.insertOrder(orderDTO)) {
                        Iterator iterator = cart.getCart().entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            ProductDTO productDTO = (ProductDTO) entry.getValue();
                            DetailDAO detail = new DetailDAO();
                            if (detail.insertOrderDetail(orderDAO.getOrderID(accountDTO.getUsername()), productDTO)) {
                                productDAO.updateProductQuantity(productDTO.getId(), productDTO.getQuantity() - productDTO.getCartQuantity());
                            }
                        }
                        url = SUCCESS;
                        request.setAttribute("WARN", "Order Completed. Your Order ID is: " + orderDAO.getOrderID(accountDTO.getUsername()));
                        session.removeAttribute("CART");
                    }
                } else { //User
                    String name = request.getParameter("txtFullname");
                    String phone = request.getParameter("txtPhone");
                    String address = request.getParameter("txtAddress");
                    OrderDTO orderDTO = new OrderDTO("NULL", cart.getTotal(), name, phone, address, paymentMethod, paymentStatus);
                    if (orderDAO.insertOrder(orderDTO)) {
                        Iterator iterator = cart.getCart().entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            ProductDTO productDTO = (ProductDTO) entry.getValue();
                            DetailDAO detail = new DetailDAO();
                            if (detail.insertOrderDetail(orderDAO.getOrderID(name), productDTO)) {
                                productDAO.updateProductQuantity(productDTO.getId(), productDTO.getQuantity() - productDTO.getCartQuantity());
                            }
                        }
                        url = SUCCESS;
                        request.setAttribute("WARN", "Order Completed. Your Order ID is: " + orderDAO.getOrderID(name) + " in order to track it please register.");
                        session.removeAttribute("CART");
                    }
                }
            } else {
                url = FAIL;
                request.setAttribute("WARN", "Fail to sent order. Try again");
            }
        } catch (Exception e) {
            LOGGER.error("Error at ConfirmCartController: " + e.getMessage());
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
