/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.function.PostManagement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 *
 * @author Dadvid
 */
public class FileUpload extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 200000 * 1024;
    private int maxMemSize = 200000 * 1024;
    private File file;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("currentUser");
        ArrayList<String> errorList = new ArrayList();
        Boolean isStored = false;

        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            List fileItems = upload.parseRequest(request);

            Iterator i = fileItems.iterator();

            String caption = "";
            String description = "";
            String fileName = "";
            ArrayList<String> tagList = new ArrayList();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();

                InputStream stream = fi.getInputStream();
                if (!fi.isFormField()) {
                    fileName = fi.getName();
                    long sizeInBytes = fi.getSize();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    
                    String directory = getServletContext().getInitParameter("file-upload");
                    
                    file = new File(directory, fileName);
                    
                    fi.write(file);
                } else {
                    if (fi.getFieldName().contains("newCaption")) {
                        caption = Streams.asString(stream);
                    } else if (fi.getFieldName().contains("newDescription")) {
                        description = Streams.asString(stream);
                    } else if (fi.getFieldName().contains("tag[]")) {
                        tagList.add(Streams.asString(stream));
                    }
                }
            }
            isStored = PostManagement.storePost(currentUser, caption, description, fileName, tagList, errorList);

            if (isStored = true) {
                out.println(fileItems);
            } else {
                out.println(errorList);
            }

        } catch (Exception ex) {
            errorList.add(ex.getMessage());
            out.println(errorList);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
