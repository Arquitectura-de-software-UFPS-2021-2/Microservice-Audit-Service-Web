package Auditoria;

import com.mysql.cj.log.Log;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CaptureInfo {

    private static String ipLocal = "";
    private static String ipublica = "<IP Publica no encontrada>";

    public static String getLocalIpAddress() {

        try {
            ipLocal = java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException ex) {
            ipLocal = "<IP Local no encontrada>" + ex.getLocalizedMessage();
        }
        return ipLocal;
    }

    public static String getPublicIpAddress() throws java.net.MalformedURLException, java.io.IOException {

        java.net.URL connection = new java.net.URL("http://checkip.amazonaws.com/");
        java.net.URLConnection conOpen = connection.openConnection();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conOpen.getInputStream()));
        ipublica = reader.readLine();
        return ipublica;
    }

    public static void getJsonRequest() throws JSONException {
        try {
            //creamos una URL donde esta nuestro webservice
            // java.net.URL url = new java.net.URL("http://wservice.viabicing.cat/v2/stations");
            //java.net.URL url = new java.net.URL("https://gorest.co.in/public/v1/users");
            //  java.net.URL url = new java.net.URL(" http://api.plos.org/search?q=title:DNA");
            java.net.URL url = new java.net.URL("https://jsonplaceholder.typicode.com/users");
            
            
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            // indicamos por que verbo HTML ejecutaremos la solicitud
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            

            if (conn.getResponseCode() != 200) {
                //si la respuesta del servidor es distinta al codigo 200 lanzaremos una Exception
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader((conn.getInputStream())));
            //creamos un StringBuilder para almacenar la respuesta del web service
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = br.read()) != -1) {
                sb.append((char) cp);
            }
            //en la cadena output almacenamos toda la respuesta del servidor
            String output = sb.toString();
            //convertimos la cadena a JSON a traves de la libreria GSON

            com.google.gson.JsonObject json = new com.google.gson.Gson().fromJson(output, com.google.gson.JsonObject.class);
         
            
            
            //imprimimos como Json
            //System.out.println("salida como JSON" + json);
            //imprimimos como String
            //System.out.println(output);

            JSONObject objetoJson = new JSONObject(output);

            //objetoJson.getJSONObject("meta").getJSONObject("pagination").getJSONObject("total");
            // Acceder a cada objeto en las posiciones 
            System.out.println(output);
 
  
            //objetoJson.getJSONObject("pagination");
            conn.disconnect();
        } catch (IOException | RuntimeException e) {
            System.out.println("Err "+e.getMessage());
        }
    }

    public static void main(String[] args) {

        try {
            CaptureInfo.getJsonRequest();
        } catch (JSONException ex) {
            Logger.getLogger(CaptureInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
