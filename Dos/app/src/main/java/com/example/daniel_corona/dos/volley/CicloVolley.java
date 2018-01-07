package com.example.daniel_corona.dos.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.daniel_corona.dos.aplicacion.AppController;
import com.example.daniel_corona.dos.constantes.G;
import com.example.daniel_corona.dos.pojos.Ciclo;
import com.example.daniel_corona.dos.proveedor.BitacoraProveedor;
import com.example.daniel_corona.dos.sync.Sincronizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danniel-coronna on 13/11/2016.
 */

public class CicloVolley {
    final static String ruta = G.RUTA_SERVIDOR + "/ciclo";
    //ContentResolver resolvedor;
    //RequestQueue queue;
    //Context context;

    //public CicloVolley(ContentResolver resolvedor, Context context) {
    //    this.resolvedor = resolvedor;
    //    //this.queue = Volley.newRequestQueue(context);
    //}

    public static void getAllCiclo(){
        String tag_json_obj = "getAllCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta;
        // prepare the Request

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        //Log.d("Response", response.toString());
                        Sincronizacion.realizarActualizacionesDelServidorUnaVezRecibidas(response);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        // add it to the RequestQueue
        //queue.add(getRequest);
        AppController.getInstance().addToRequestQueue(getRequest, tag_json_obj);
    }

    public static void addCiclo(Ciclo ciclo, final boolean conBitacora, final int idBitacora){
        String tag_json_obj = "addCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PK_ID", ciclo.getID());
            jsonObject.put("nombre", ciclo.getNombre());
            jsonObject.put("abreviatura", ciclo.getAbreviatura());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(postRequest);
        AppController.getInstance().addToRequestQueue(postRequest, tag_json_obj);
    }

    public static void updateCiclo(Ciclo ciclo, final boolean conBitacora, final int idBitacora){
        String tag_json_obj = "updateCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta + "/" + ciclo.getID();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PK_ID", ciclo.getID());
            jsonObject.put("nombre", ciclo.getNombre());
            jsonObject.put("abreviatura", ciclo.getAbreviatura());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        //Log.d("Response", response.toString());
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", error.getMessage());
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                }
        );
        //queue.add(putRequest);
        AppController.getInstance().addToRequestQueue(putRequest, tag_json_obj);
    }

    public static void delCiclo(int id, final boolean conBitacora, final int idBitacora){
        String tag_json_obj = "updateCiclo"; //En realidad debería ser un identificar único para luego poder cancelar la petición.
        String url = ruta + "/" + String.valueOf(id);

        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(true);

        StringRequest delRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(conBitacora) BitacoraProveedor.delete(AppController.getResolvedor(), idBitacora);
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        AppController.getInstance().getSincronizacion().setEsperandoRespuestaDeServidor(false);

                    }
                }
        );
        //queue.add(delRequest);
        AppController.getInstance().addToRequestQueue(delRequest, tag_json_obj);
    }
}
