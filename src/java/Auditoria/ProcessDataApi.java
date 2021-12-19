package Auditoria;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import javax.sql.DataSource;

/**
 *
 * @author NIRLEVIN
 */
@WebServlet(name = "ProcessDataApi", urlPatterns = {"/ProcessDataApi"})
public class ProcessDataApi extends HttpServlet {

    java.sql.Statement st = null;
    Connection cn = null;
    java.sql.ResultSet rs = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("application/json; charset=UTF-8");
        // response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String querySql = "select * from auditoria";
            DataSource source = ServiceAuditoriums.generateSqlConvertJson();
            cn = source.getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(querySql);
            com.google.gson.JsonObject gson = new JsonObject();
           
            JsonArray arreglo = new JsonArray();

            while (rs.next()) {
               JsonObject data = new JsonObject();
                data.addProperty("id", rs.getInt(1));
                 data.addProperty("usuario", rs.getString(2));
                  data.addProperty("accion", rs.getString(3));
                    data.addProperty("fecha_hora", rs.getString(4));
                      data.addProperty("modulo", rs.getString(5));
                        data.addProperty("ippublica", rs.getString(6));
                          data.addProperty("iplocal", rs.getString(7));
                            data.addProperty("descripcion", rs.getString(8));
                               arreglo.add(data);
              
            }
            gson.add("data", arreglo);
            request.getRequestDispatcher("/index.htm?json=" + gson.get("data").toString()).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessDataApi.class.getName()).log(Level.SEVERE, null, ex);
            out.print(ex.getMessage());
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
