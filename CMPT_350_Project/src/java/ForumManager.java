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
 *Forum manager, looks after the forum, handles all requests made by the 
 * forum pages.
 * 
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
        
        if(request.getParameter("FORUM") != null){  // what current forum page are we on right now? This maintains that question.
            
           forumSelected =  request.getParameter("FORUM");
           System.out.println("hey we got a message from the webpage this is what we got for a message:" + forumSelected);
        }
        
        
        Connection conn = createConnection();   // start a new connection to the data base. We want to return data to this forum page.
        
        try(PrintWriter out = response.getWriter()){
            
               out.println(returnTitle(conn));  // return this content to the forum page.
            
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
        
        String title = request.getParameter("TITLE");   // when posting a forum post we need two pieces, the first is the Title. The second is the actual message.
        String post = request.getParameter("POST"); 
        
        
        Connection conn = createConnection();           // time to inseret into the data base.
        
        try{
        
            Statement state = conn.createStatement(); 
            System.out.println("We are perparing to inseret into a table: " + title);
            
            String date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            
            
            
            state.execute("INSERT INTO "+forumSelected+"(posts, postTime, name, title) VALUES ('"+post+"','"+date+ "','anon','"+title+"')");    // everything we want to the insert into the data base.
                                                                                                                                                // we want post, a date, a name, and a title.            
            
            try(PrintWriter out = response.getWriter()){
                
                out.println("data has been sent!");
            
            }
            
        } catch(Exception e) { 
            
            System.err.println("we got ourselves a bad error:" + e);
        }
        
        
    }

    
    
    /**
     * 
     * a custom method designed, its purpose is to return the title of all the posts in this table.
     * it will find all the titles, select them and return that information to the Get command.
     * @param conn the connection to the data base. 
     * @return: a String that contains all titles in the data base. 
     */
    
     public static String returnTitle(Connection conn){
         
        try{
            String allItemsInThisColumn = "";   // string that will store all item present in the data base.

            Statement state = conn.createStatement();
            
           
           ResultSet resultSet = state.executeQuery("SELECT * FROM "+forumSelected);   // command ran on the data base.
           
           System.out.println("returning forums for" +forumSelected);
           
            while( resultSet.next()){                                                   // aquire all items from the data base until their is nothing left.
              
              allItemsInThisColumn += resultSet.getString("title") + "\n";                    // add to string, so we can send it to the webpage.
            } 
          
           
            return allItemsInThisColumn; 
           
        
        } catch(Exception e){
            
            System.err.println("error was thrown:" + e); 
        }
        
        return null; 
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
