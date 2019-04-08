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
    static int uniqueNum = 0; 
    
    
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
            
            
            if(request.getParameter("TEST") != null) { 
                
                Connection  conn = createConnection(); 
                
                try(PrintWriter out = response.getWriter()) { 
                 
                    out.println(returnComments(conn));
               
                
                }
                
            }else {
                Connection conn = createConnection(); 
            
                try(PrintWriter out = response.getWriter()) { 
                 
                    out.println(returnData(conn));
               
                
                }
            }
            
        }
        
        
        
        
    }
    
    /**
     * returnComments(Connection)
     * 
     * returns all the comments made on this forum page.
     * @param conn a connection to the desired MYSQL data base.
     * @return returns a html styled string that will be inserted into the webpage!
     */
    
    static String returnComments(Connection conn) {
        
           String text = ""; 

        try{
            
           
           
           Statement s = conn.createStatement(); 
           
           
            
           if(ForumBoard.equals("GD")) { 
           
                 ResultSet rs = s.executeQuery("SELECT * FROM gdcomment");
                 int counter = 2; 
                  while(rs.next()) { 
                    
               
                    if(rs.getInt("gdpostNum") == uniqueNum) { 
                      
                   
                        text += rs.getString("posts") + "\n"; 
                        counter ++; 
                    }
                    
                    
               
                  }
                
            } else if(ForumBoard.equals("TTT")) { 
                
                 ResultSet rs = s.executeQuery("SELECT * FROM tttcomment");
                 int counter = 2; 
                 
                  while(rs.next()) { 
                    
               
                    if(rs.getInt("tttpostNum") == uniqueNum) { 
                      
                   
                        text += rs.getString("posts") + "\n"; 
                        counter ++; 
                    }
                    
                    
               
                  }
                
            } else if (ForumBoard.equals("CC")) { 
                
                  ResultSet rs = s.executeQuery("SELECT * FROM cccomment");
                 int counter = 2; 
                 
                  while(rs.next()) { 
                    
               
                    if(rs.getInt("ccpostNum") == uniqueNum) { 
                      
                   
                        text += rs.getString("posts") + "\n"; 
                        counter ++; 
                    }
                    
                    
               
                  }
                
                
            } else if (ForumBoard.equals("PA")) { 
                
                   ResultSet rs = s.executeQuery("SELECT * FROM pacomment");
                 int counter = 2; 
                 
                  while(rs.next()) { 
                    
               
                    if(rs.getInt("papostNum") == uniqueNum) { 
                      
                   
                        text += rs.getString("posts") + "\n"; 
                        counter ++; 
                    }
                    
                    
               
                  }
                
            }
           
        
           
           
           
        }catch(Exception e) { 
            
            
            System.err.println("error occured:" + e);
        }
        
        
        return text;
    }
    
    
    /**
     * returns all the data from the main post, or HEAD post. this is what the user sees when they click on a post in the 
     * category that they are searching in. It is the head post.
     * 
     * @param conn
     * @return 
     */
    static String returnData(Connection conn) { 
        
        String content = "";
       
        try{
            
            
        
            Statement state = conn.createStatement(); 
        
            ResultSet rs = state.executeQuery("SELECT * FROM " + ForumBoard);
                    
          
          
          while(rs.next()) { 
             
             if(titleOfPost.equals(rs.getString("Title"))){  // find the title within the category, once we find it then we can send this data back.
                
                 uniqueNum = rs.getInt("postNum");          // aquire primary number for this item.
                 System.out.println("Number: " + uniqueNum);
                 content += rs.getString("Title") + "_" + rs.getString("posts") + "_" + rs.getString("postTime")+ "_"+rs.getString("name") ; 
                 // send all this data back so we can easliy cut this string and send it to the correct locations.
                 
             
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

                
         Connection conn = createConnection();  // new creation created. 
            
         
        try{
            
            String content = request.getParameter("DATA"); // grab the data that the user wants to post.
            
            
            try(PrintWriter out = response.getWriter()) {  //once data is done being processed into the DATA base, we will send it to the serverlet.
                 
                out.println(postComments(conn, content));
               
                
            }

            
        }catch(Exception e){
            
            System.err.println("error has been caught:" + e);
        }
        
    }
    
    
    /**
     * 
     * postComments(conn, data):
     * 
     * stores the correct posts for the correct Parent post.
     * @param  conn: the connection to the mysql data base.
     * @param data: the data that we want to store into the mysql data base.
     * @return returns a string containing posted data.
     */
    public static String postComments(Connection conn, String data) { 
      
        try{
            
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM " + ForumBoard);
        
        
        while(rs.next()) { 
            
            if(rs.getString("title").equals(titleOfPost)) { 
                
                if(ForumBoard.equals("GD")) { 
                    //System.out.println("made it in here!" + rs.getInt("postNum"));
                    s.execute("INSERT INTO gdcomment(posts, name, gdpostNum) VALUES ('"+data+"', 'anon',"+rs.getInt("postNum") +")"); // insert this data into the GDcomments tabel, basically a place to store all data here.
                       
                } else if(ForumBoard.equals("TTT")) { 
                    
                    
                    s.execute("INSERT INTO tttcomment(posts, name, tttpostNum) VALUES ('"+data+"', 'anon',"+rs.getInt("postNum") +")"); // insert this data into the GDcomments tabel, basically a place to store all data here.
                    
                } else if(ForumBoard.equals("CC")) {
                    
                      
                    s.execute("INSERT INTO cccomment(posts, name, ccpostNum) VALUES ('"+data+"', 'anon',"+rs.getInt("postNum") +")"); // insert this data into the GDcomments tabel, basically a place to store all data here.
                }else if(ForumBoard.equals("PA")) { 
                    
                    s.execute("INSERT INTO pacomment(posts, name, papostNum) VALUES ('"+data+"', 'anon',"+rs.getInt("postNum") +")"); // insert this data into the GDcomments tabel, basically a place to store all data here.

                }
                
            }
            
        }
        
        
        
        }catch(Exception e) { 
            
            
            System.err.println("error has been thrown:" + e);
        }
        
        
        
       
        return data; 
      
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
