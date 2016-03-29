package com.nuance.expertassistant;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet to handle File upload request from Client
 *
 * @author Javin Paul
 */
public class FileUploadHandler extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "/usr/local/Nuance/MultiModalFramework/uploadedFiles";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //process only if its multipart content
        String ProjectID = "";

        for (Part p : request.getParts()) {
            
            System.out.println(" ******>The parts are :" + p.toString());
            
            if ("ProjectID".equals(p.getName())) {
                System.out.println("ProjectID" + p.getInputStream().toString());
            }

        }

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                String fileName = "";

                for (FileItem item : multiparts) {
                    System.out.println("Item is :" + item.getName());
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        fileName = name;
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));

                    }
                }

                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");

                System.out.println(" The file Name is : " + fileName);

                request.setAttribute("fileName", fileName);

            } catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        } else {
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }

        request.getRequestDispatcher("/result.jsp").forward(request, response);

    }

}
