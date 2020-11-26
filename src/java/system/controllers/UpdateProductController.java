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
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import system.daos.LogDAO;
import system.daos.ProductDAO;
import system.dtos.LogDTO;
import system.dtos.ProductDTO;
import system.dtos.RegistrationDTO;

/**
 *
 * @author Admin
 */
public class UpdateProductController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateProductController.class);
    private static final String SUCCESS = "SearchProductForAdminController";
    private static final String FAIL = "updateproduct.jsp";

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
        String url = FAIL;
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
                    LOGGER.error("Error in UploadFile at UpdateProductController: " + e.getMessage());
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
                HttpSession session = request.getSession();
                int id = Integer.parseInt(param.get("txtProductID").toString());
                String name = (String) param.get("txtProductName");
                String description = (String) param.get("txtDescription");
                float price = Float.parseFloat(param.get("txtPrice").toString());
                int quantity = Integer.parseInt(param.get("txtQuantity").toString());
                String created = (String) param.get("txtCreatedDate");
                String expired = (String) param.get("txtExpiredDate");
                int category = Integer.parseInt(param.get("txtCategory").toString());
                int status = Integer.parseInt(param.get("txtStatus").toString());
                ProductDAO dao = new ProductDAO();
                ProductDTO dto = new ProductDTO(name, description, price, quantity, fileName, created, expired, category, status);
                LogDAO log = new LogDAO();
                RegistrationDTO account = (RegistrationDTO) session.getAttribute("USER");
                LogDTO logdto = new LogDTO(account.getUsername(), id, "UPDATE");
                log.insertLog(logdto);
                if (dao.updateProduct(dto, id)) {
                    url = SUCCESS;
                    request.setAttribute("SUCCESS", "Product name " + name + " updated successfully");
                } else {
                    url = FAIL;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error at UpdateProductController: " + e.getMessage());
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
