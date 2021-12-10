package Auditoria;

import java.util.logging.Level;
import java.util.logging.Logger;

// Inserción auditoria

public class ServiceAuditoriums {

    public boolean insertAudit(String user, String action, String module, String ipPublic, String ipLocal, String description) throws java.io.IOException {
        try {

            String querySql = "insert into auditoria (usuario,accion,fecha_hora,modulo,ippublica,iplocal,descripcion) values "
                    + "('" + user + "','" + action + "','" + module + "','SYSDATE()','" + CaptureInfo.getPublicIpAddress()
                    + "','" + CaptureInfo.getLocalIpAddress() + "','" + description + "');";

            java.sql.Statement st = ModelDb.conexion().createStatement();

            return st.executeUpdate(querySql) > 0;

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(ServiceAuditoriums.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
