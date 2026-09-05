package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/studentReg")
public class StudentReg extends HttpServlet {
private static final String URL = "jdbc:mySQL://localhost:3306/javalearning?useSSL=false&allowPublicKeyRetrieval=true";
private static final String USER = "root";
private static final String PASSWORD = "Trader@2004";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fname = request.getParameter("fname");
        String regnumber = request.getParameter("regnumber");
        String email = request.getParameter("email");

        request.setAttribute("fname", fname);
        request.setAttribute("regnumber", regnumber);
        request.setAttribute("email", email);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection(URL, USER, PASSWORD);

            String sql= "INSERT INTO student(fname,regnumber,email) VALUES(?,?,?)";
            PreparedStatement pstm= conn.prepareStatement(sql);
            pstm.setString(1,fname);
            pstm.setString(2,regnumber);
            pstm.setString(3,email);

            int rowAffected = pstm.executeUpdate();
            PrintWriter out = response.getWriter();

            if(rowAffected>0){
                request.setAttribute("message", "Registration Successful");
            }else{
                request.setAttribute("message", "Registration Failed");
            }
            request.getRequestDispatcher("display.jsp").forward(request, response);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}