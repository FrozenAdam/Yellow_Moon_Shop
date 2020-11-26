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
import org.apache.log4j.Logger;
import system.daos.ProductDAO;

/**
 *
 * @author Admin
 */
public class SearchProductForAdminController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchProductForAdminController.class);

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
            String search = request.getParameter("txtSearch");
            int page;
            String pageStr = request.getParameter("txtPage");
            String movePage = request.getParameter("movePage");
            ProductDAO dao = new ProductDAO();
            if (search != null) {
                if (pageStr == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(pageStr);
                }
                int productCount = dao.countPageForSearch(search);
                int pageCount = (int) Math.ceil(productCount / (double) 3);

                if (movePage == null); else if (movePage.equals("next")) {
                    if (page < pageCount) {
                        page = page + 1;
                    }
                } else if (movePage.equals("prev")) {
                    if (page > 1) {
                        page = page - 1;
                    }
                }
                request.setAttribute("PRODUCTINFO", dao.getAllProduct(search, page));
                request.setAttribute("CURRENT", page);
                request.setAttribute("MAX", pageCount);
            } else {
                    if (pageStr == null) {
                        page = 1;
                    } else {
                        page = Integer.parseInt(pageStr);
                    }
                    int productCount = dao.countPage();
                    int pageCount = (int) Math.ceil(productCount / (double) 3);

                    if (movePage == null); else if (movePage.equals("next")) {
                        if (page < pageCount) {
                            page = page + 1;
                        }
                    } else if (movePage.equals("prev")) {
                        if (page > 1) {
                            page = page - 1;
                        }
                    }
                    request.setAttribute("PRODUCTINFO", dao.getAllProduct("", page));
                    request.setAttribute("CURRENT", page);
                    request.setAttribute("MAX", pageCount);
                }
        } catch (Exception e) {
            LOGGER.error("Error at SearchProductForAdminController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("manage.jsp").forward(request, response);
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
