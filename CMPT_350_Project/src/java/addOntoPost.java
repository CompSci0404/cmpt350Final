/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.*; 
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Trooper
 */
@WebServlet(urlPatterns = {"/addOntoPost"})
public class addOntoPost extends HttpServlet {

    
    static String titleOfPost = null; 
    static String ForumBoard = null; 
    
    
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet addOntoPost</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addOntoPost at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        
        if(request.getParameter("TITLE") != null && request.getParameter("FORUM") != null) { 
            
            titleOfPost = request.getParameter("TITLE"); 
            ForumBoard = request.getParameter("FORUM"); 
            
            System.out.println("Aquried the title of the post!" + titleOfPost + " this is the forums board: " + ForumBoard);
            
        }else { 
            
            Connection conn = createConnection(); 
            
            try(PrintWriter out = response.getWriter()) { 
                 
                out.println(returnData(conn));
               
                
            }
            
        }
        
        
        
        
    }
    
    
    static String returnData(Connection conn) { 
        
        String content = "";
       
        try{
            
        
            Statement state = conn.createStatement(); 
        
            ResultSet rs = state.executeQuery("SELECT * FROM " + ForumBoard);
                    
            
          
          while(rs.next()) { 
              
              
             if(titleOfPost.equals(rs.getString("Title"))){ 
                 
                 content += rs.getString("Title") + "_" + rs.getString("posts") + "_" + rs.getString("postTime")+ "_"+rs.getString("name") ; 
                 
             
             }
              
              
              
          }
            
        }catch(Exception e) { 
            
            System.err.println("error is thrown:" + e);
            
        }
        
        
        return content; 
        
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

        
        
        
    }
    
    
    
    /**
     * creates a connection to the data base so we can store data there. 
     * 
     * @return  returns a successful connection to the data base for this website.
     */
     public static Connection createConnection(){
        
        Connection conn = null; 
        
        try{
            
             Class.forName("com.mysql.jdbc.Driver");   // for some reason I need this, I have exported my connector j in the libraries, still need to call this though?
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproj", "root", "yeet0404");  // connect to the msql server. 
          
        }catch(Exception e){
            
            System.err.println("exception was caught: " + e);
        }
        
        return conn; 
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
