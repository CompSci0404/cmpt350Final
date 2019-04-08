/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Joel
 */
public class CheckoutServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckoutServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath() + "</h1>");
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
        
        // retrieve all of the request parameters from the webpage
        String firstName = request.getParameter("FNAME");
        String lastName = request.getParameter("LNAME");
        String country = request.getParameter("COUNTRY");
        String province = request.getParameter("PROVINCE");
        String cardNumber = request.getParameter("CARDNUMBER");
        String expDate = request.getParameter("EXPDATE");
        String cvv = request.getParameter("CVV");
        
        // generate a unique game code
        String gameCode = UUID.randomUUID().toString();
        
        // encode the user's information
        byte[] firstNameEncoded = Base64.encodeBase64(firstName.getBytes());
        byte[] lastNameEncoded = Base64.encodeBase64(lastName.getBytes());
        byte[] countryEncoded = Base64.encodeBase64(country.getBytes());
        byte[] provinceEncoded = Base64.encodeBase64(province.getBytes());
        byte[] cardNumberEncoded = Base64.encodeBase64(cardNumber.getBytes());
        byte[] expDateEncoded = Base64.encodeBase64(expDate.getBytes());
        byte[] cvvEncoded = Base64.encodeBase64(cvv.getBytes());
        
        // test to see the encoded string
        System.out.println(new String(firstNameEncoded));
        System.out.println(new String(lastNameEncoded));
        System.out.println(new String(countryEncoded));
        System.out.println(new String(provinceEncoded));
        System.out.println(new String(cardNumberEncoded));
        System.out.println(new String(expDateEncoded));
        System.out.println(new String(cvvEncoded));
        
        // decode the user's information
        byte[] firstNameDecoded = Base64.decodeBase64(firstNameEncoded);
        byte[] lastNameDecoded = Base64.decodeBase64(lastNameEncoded);
        byte[] countryDecoded = Base64.decodeBase64(countryEncoded);
        byte[] provinceDecoded = Base64.decodeBase64(provinceEncoded);
        byte[] cardNumberDecoded = Base64.decodeBase64(cardNumberEncoded);
        byte[] expDateDecoded = Base64.decodeBase64(expDateEncoded);
        byte[] cvvDecoded = Base64.decodeBase64(cvvEncoded);
        
        // test to see the decoded string
        System.out.println(new String(firstNameDecoded));
        System.out.println(new String(lastNameDecoded));
        System.out.println(new String(countryDecoded));
        System.out.println(new String(provinceDecoded));
        System.out.println(new String(cardNumberDecoded));
        System.out.println(new String(expDateDecoded));
        System.out.println(new String(cvvDecoded));
        
        Connection conn = createConnection(); // connect to the paymentInfo database
        
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            Statement state = conn.createStatement();
            // insert the payment information into a SQL database
            state.execute("INSERT INTO "+ "purchaseInfo" +"(firstName, lastName, country, province, cardNumber, expDate, cvv) VALUES ('"+firstNameEncoded+"','"+lastNameEncoded+"','"+countryEncoded+"','"+provinceEncoded+"','"+cardNumberEncoded+"','"+expDateEncoded+"','"+cvvEncoded+"')");
            //display the game code to the user after the payment information has been processed
            out.println(gameCode);
        }   
        catch(Exception e) { 
            
            System.err.println("we got ourselves a bad error:" + e);
        }       
    }
    
    /**
     * creates a connection to the data base so we can store data there. 
     * 
     * @return  returns a successful connection to the data base for this website.
     */
     public static Connection createConnection(){
        
        Connection conn = null; 
        
        try{
            
             Class.forName("com.mysql.jdbc.Driver");
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
