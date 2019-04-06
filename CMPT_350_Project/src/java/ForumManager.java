/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.*; 
import java.text.SimpleDateFormat;

/**
 *
 * @author Joel
 */
public class ForumManager extends HttpServlet {

    static String forumSelected = null; 
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
            out.println("<title>Servlet ForumManager</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForumManager at " + request.getContextPath() + "</h1>");
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
        
        if(request.getParameter("FORUM") != null){
            
           forumSelected =  request.getParameter("FORUM");
           System.out.println("hey we got a message from the webpage this is what we got for a message:" + forumSelected);
        }
        
        try(PrintWriter out = response.getWriter()){
            out.println("get sent!");
            
            
        }
        
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
        
        String title = request.getParameter("TITLE");
        String post = request.getParameter("POST"); 
        
        
        Connection conn = createConnection(); 
        
        try{
        
            Statement state = conn.createStatement(); 
            System.out.println("We are perparing to inseret into a table: " + title);
            
            String date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            
            
            state.execute("INSERT INTO "+forumSelected+"(posts, postTime, name, title) VALUES ('"+post+"','"+date+ "','anon','"+title+"')");
            
            
            try(PrintWriter out = response.getWriter()){
                
                out.println("data has been sent!");
            
            }
            
        } catch(Exception e) { 
            
            System.err.println("we got ourselves a bad error:" + e);
        }
        
        
    }

    
    
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
