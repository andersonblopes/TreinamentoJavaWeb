/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Helena
 */

public class Impressao extends HttpServlet {
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        Logar logar = new Logar();
        
        Connection con;
        response.setContentType("application/pdf");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        InputStream reportStream = getServletConfig().getServletContext().getResourceAsStream("/relatorios/relClientes.jasper");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clientes",logar.getUsuario(),logar.getSenha());
            JasperRunManager.runReportToPdfStream(reportStream,servletOutputStream,new HashMap(),con);
            con.close();
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            //Mostra o stack trace no browser
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            response.setContentType("text/plain");
            response.getOutputStream().print(stringWriter.toString());
        } catch (JRException ex) {
            Logger.getLogger(Impressao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getServletInfo(){
        return "short description";
    } // </editor-fold>

}
