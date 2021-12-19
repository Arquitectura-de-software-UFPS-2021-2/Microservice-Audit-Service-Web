package Auditoria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
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
    
    
        private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();

    }
        
            public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void getJsonRequest() throws IOException, JSONException  {

                // Creacion y Construcción de array para enviar los datos a la base de datos...
        java.util.ArrayList<String> arreglo = new java.util.ArrayList<>();
        // Se obtiene token del usuario ingresado o x accion que realice en su eventualidad
        //  JSONObject jsonUser = readJsonFromUrl("http://18.235.152.56/login?student_code=1151651&password=hola");
        // Se obtiene token accediendo a la posicion del objecto api_token
        //JSONArray objectToken = jsonUser.getJSONArray("api_token");
        // Nuevamente se consume para acceder a la data...
        JSONObject json = readJsonFromUrl("http://18.235.152.56/students?api_token=" + "ajo8IzTFPGgmPalA6u14DON4K7X6mHvFvs9x1gLJEZChSAWAfzWu8jklVK5iJ1GmfomJEXaO9mrue7rCbThEKQgHKLVm5pncez04lme1T43pLw66Px5kGv3ueXyQYqUNOi4JCvBrFeIo6iP71aosu7");
        // Obtenemos los objetos correspondiente a la aduitoria.
        JSONArray objectData = json.getJSONArray("data");
        // Recorremos Los objetos de acuerdo a la longitud de lo consumido para el microservicio de auditoria
        for (int iterator = 0; iterator < objectData.length(); iterator++) {
            System.out.println(
                    "Fecha Creacion " + json.getJSONArray("data").toJSONObject(objectData).toJSONArray(objectData).getJSONObject(iterator).get("created_at")
                    + " Fecha Actualizacion " + json.getJSONArray("data").toJSONObject(objectData).toJSONArray(objectData).getJSONObject(iterator).get("updated_at")
                    + " Descripcion " + json.getJSONArray("data").toJSONObject(objectData).toJSONArray(objectData).getJSONObject(iterator).get("email"));
            // Añadimos los obtenido en un ArrayList
            //    arreglo.add((String)json.getJSONArray("data").toJSONObject(objectData).toJSONArray(objectData).getJSONObject(iterator).get("created_at"));
        }

        // Los parametros que se debe recibir son: String user, String action, String module, String description es decir...
        // new ServiceAuditoriums().insertAudit(arreglo.get(0), arreglo.get(1), arreglo.get(2), arreglo.get(3));
        // Finaliza el proceso de consumo para el microservicio aditoria...
        
    }
}
