package Auditoria;

// Inserción auditoria
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceAuditoriums {

    public boolean insertAudit(String user, String action, String module, String ipPublic, String ipLocal, String description) throws java.io.IOException {
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

    public static String generateSqlConvertJson() {
        JSONArray json = new JSONArray();
        try {
            String querySql = "select * from auditoria";
            java.sql.Statement st = ModelDb.conexion().createStatement();
            java.sql.ResultSet rs = st.executeQuery(querySql);

            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= numColumns; i++) {
                    String column_name = rsmd.getColumnName(i);
                    try {
                        obj.put(column_name, rs.getObject(column_name));
                    } catch (JSONException ex) {
                        Logger.getLogger(ServiceAuditoriums.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                json.put(obj);
            }
        } catch (java.sql.SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return (String) json.toString();
    }

    public static List<JSONObject> getFormattedResult() {
        List<JSONObject> resList = new ArrayList<JSONObject>();
        try {
            // get column names

            String querySql = "select * from auditoria";
            java.sql.Statement st = ModelDb.conexion().createStatement();
            java.sql.ResultSet rs = st.executeQuery(querySql);

            ResultSetMetaData rsMeta = rs.getMetaData();
            int columnCnt = rsMeta.getColumnCount();
            List<String> columnNames = new ArrayList<String>();
            for (int i = 1; i <= columnCnt; i++) {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
            }

            while (rs.next()) { // convert each object to an human readable JSON object
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= columnCnt; i++) {
                    String key = columnNames.get(i - 1);
                    String value = rs.getString(i);
                    obj.put(key, value);
                }
                resList.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resList;
    }

    public static JSONArray convert() {

        JSONArray jsonArray = new JSONArray();

        try {

            String querySql = "select * from auditoria";

            java.sql.Statement st = ModelDb.conexion().createStatement();
            java.sql.ResultSet rs = st.executeQuery(querySql);

            while (rs.next()) {

                int columns = rs.getMetaData().getColumnCount();
                JSONObject obj = new JSONObject();

                for (int i = 0; i < columns; i++) {
                    try {
                        obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
                    } catch (JSONException ex) {
                        Logger.getLogger(ServiceAuditoriums.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                jsonArray.put(obj);
            }

            st = ModelDb.conexion().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceAuditoriums.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jsonArray;
    }

    public static void main(String[] args) {
        
        ServiceAuditoriums a = new ServiceAuditoriums();
        System.out.println(a.convert());
    }
}
