/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import system.daos.ProductDAO;
import system.dtos.ProductDTO;

/**
 *
 * @author Admin
 */
public class CreateProductController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateProductController.class);
    private static final String SUCCESS = "SearchProductForAdminController";
    private static final String FAIL = "createproduct.jsp";

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
            boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
            if (!isMultiPart) {

            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    LOGGER.error("Error in UploadFile at CreatProductController: " + e.getMessage());
                }
                Iterator iter = items.iterator();
                Hashtable param = new Hashtable();
                String fileName = null;
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        param.put(item.getFieldName(), item.getString());
                    } else {
                        String itemName = item.getName();
                        fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                        String realPath = getServletContext().getRealPath("/") + "img\\" + fileName;
                        File saveFile = new File(realPath);
                        File destFile = new File(realPath);
                        if (destFile.exists()) {
                            destFile.delete();
                            item.write(saveFile);
                        } else {
                            item.write(saveFile);
                        }
                    }
                }
                String name = (String) param.get("txtProductName");
                String description = (String) param.get("txtDescription");
                float price = Float.parseFloat(param.get("txtPrice").toString());
                int quantity = Integer.parseInt(param.get("txtQuantity").toString());
                String created = (String) param.get("txtCreatedDate");
                String expired = (String) param.get("txtExpiredDate");
                int category = Integer.parseInt(param.get("txtCategory").toString());
                int status = 0;
                ProductDAO dao = new ProductDAO();
                ProductDTO dto = new ProductDTO(name, description, price, quantity, fileName, created, expired, category, status);
                if (dao.createProduct(dto)) {
                    url = SUCCESS;
                    request.setAttribute("SUCCESS", "Product name " + name + " added successfully");
                } else {
                    url = FAIL;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error at CreateProductController: " + e.getMessage());
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
