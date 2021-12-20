package Auditoria;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceAuditoriums {
    
static DataSource dataSource = null;

    public boolean insertAudit(String user, String action, String module, String description) throws java.io.IOException {
        try {

            String querySql = "insert into auditoria (usuario,accion,fecha_hora,modulo,ippublica,iplocal,descripcion) values "
                    + "('" + user + "','" + action + "','" + module + "','SYSDATE()','" + CaptureInfo.getPublicIpAddress()
                    + "','" + CaptureInfo.getLocalIpAddress() + "','" + description + "');";
            java.sql.Statement st = ModelDb.conexion().createStatement();
            return st.executeUpdate(querySql) > 0;
        } catch (java.sql.SQLException ex) {
            java.util.logging.Logger.getLogger(ServiceAuditoriums.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return false;
        }
    }

    public static DataSource generateSqlConvertJson() {
        
        try {
            Context context;
            context = new InitialContext();
            dataSource =  (DataSource) context.lookup("java:comp/env/jdbc/pooldb");

        } catch (NamingException ex) {
            Logger.getLogger(ServiceAuditoriums.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataSource;
    }
}
