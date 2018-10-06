package com.example.aymen.personalcoach.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aymen.personalcoach.FoodHistory;
import com.example.aymen.personalcoach.UserInfo;
import com.example.aymen.personalcoach.tools.DateConverter;
import com.example.aymen.personalcoach.tools.RunningData;
import com.example.aymen.personalcoach.tools.WeightHistory;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aymen on 12/19/2017.
 */

public class DBhelper {
public final static String ip_address  = "http://192.168.1.3";
    public static final String TAG = "Response";

private  Context context;
    public DBhelper(Context context) {
        this.context = context;
    }

    public void caloriesPerDays(int id , VolleyCallback callback){

        ArrayList<Entry> cvalues = new ArrayList<>(  );

        RequestQueue queue = Volley.newRequestQueue(context);
          final  String url = ip_address+"/pc/api/getCaloriesHistory/"+id;
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d( TAG, "qsdfqsdf: "+response.toString() );
                        System.out.println("qsdf"+response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("days");
                            Log.d( TAG, "onResponse:  mlkjmlkjmlkj" );
                            for (int i = 0; i < array.length(); i++){
                                JSONObject j = array.getJSONObject(i);
                                Log.d( TAG, "onResponse:  "+j.get( "date" ) );
                               cvalues.add( new Entry( Float.valueOf(j.getString( "date" )) , Float.valueOf(j.getString( "calories" ))) );
                            }
                            callback.onSuccessResponse( cvalues );







                        } catch (JSONException e) {
                            Log.d( TAG, "onResponse: "+e.getMessage() );
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d( "fsdf", "onErrorResponse: " );
                        error.getStackTrace();
                        callback.onFail(error.getMessage());
                    }
                });

        Log.d( TAG, "caloriesPerDays: " );
// add it to the RequestQueue
        queue.add(stringRequest);
    }



    public void toDayEatenFood(int id , VolleyCallbackList callback){

    ArrayList<FoodHistory> foodHistories;

    RequestQueue queue = Volley.newRequestQueue(context);
    final String url = ip_address+"/pc/api/getFoodToday/"+id;

    foodHistories = new ArrayList<>(  );
          Log.i("link",url);
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray array  = object.getJSONArray("food");
                        for (int i = 0; i < array.length(); i++){
                            JSONObject j = array.getJSONObject(i);
                            foodHistories.add( new FoodHistory(  j.getInt( "id" ), j.getString( "foodname" ) , j.getDouble( "qte" ) ,j.getDouble( "calories" )) );
                       //     Log.d( TAG, "onResponse:  "+j.get( "foodname" ) );

                        }
                        callback.onSuccessResponse( foodHistories );



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    System.out.println("Erreur "+error.getMessage());
                    callback.onFail( error.getMessage() );
                }
            });


// add it to the RequestQueue
          queue.add(stringRequest);
    }


    public void getMostEatenFood(int id ,VolleyCallbackList callback){

        ArrayList<FoodHistory> foodHistories;

        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/pc/api/MostEeatentToday/"+id;

        foodHistories = new ArrayList<>();
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("food");
                            for (int i = 0; i < array.length(); i++){
                                JSONObject j = array.getJSONObject(i);
                                foodHistories.add( new FoodHistory(  j.getInt( "id" ), j.getString( "foodname" ) , j.getDouble( "qte" ) ,j.getDouble( "calories" )) );
                               // Log.d( TAG, "onResponse:  "+j.get( "foodname" ) );

                            }
                            callback.onSuccessResponse( foodHistories );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        callback.onFail( error.getMessage() );
                    }
                });


// add it to the RequestQueue
        queue.add(stringRequest);
    }

    public void addFood(int id , String foodname , Float qte  , Float calories){



        RequestQueue queue = Volley.newRequestQueue(context);  // this = context
        // { "id_user": "1" , "foodname": "2019", "qte": "1", "calories": "2" }

        final String url = ip_address+"/pc/api/wsf";
        Map<String,String> params = new HashMap<String, String>();
        params.put("id_user",String.valueOf( id));
        params.put("foodname",foodname);
        params.put("qte",String.valueOf( qte ));
        params.put("calories",String.valueOf( calories ));

        Log.d( TAG, "addFood: "+new JSONObject( params ) );
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, new JSONObject( params ),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }





    public void registerUser(UserInfo user , VolleyCallbackUserLogin volleyCallbackUserLogin ){



        RequestQueue queue = Volley.newRequestQueue(context);  // this = context
        // { "id_user": "1" , "foodname": "2019", "qte": "1", "calories": "2" }

        final String url = ip_address+"/pc/api/registerUser";
        Map<String,String> params = new HashMap<String, String>();
        params.put("first_name",user.getFirstname());
        params.put("last_name",user.getLastname());
        params.put("email",user.getEmail());
        params.put("password",user.getPassword());
        params.put("activty",user.getActivitylevel());
        params.put("goal",user.getGoal());
        params.put("gender",user.getGender());
        params.put("birthday",user.getDateOfBirth());
        params.put("weight",String.valueOf(user.getCurrentWheight()));
        params.put( "height" ,String.valueOf(  user.getHeight() ));
        params.put("role",user.getRole());

        Log.d( TAG, "addFood: "+new JSONObject( params ) );
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, new JSONObject( params ),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        volleyCallbackUserLogin.onSuccessResponse( user.getId(),  user.getEmail() , user.getPassword());


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                        Log.d("Error.Response", error.getMessage());
                        volleyCallbackUserLogin.onFail( error.getMessage() );
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }



    public void loginUser(String email , String password , VolleyCallbackUserLogin volleyCallbackUserLogin){



        RequestQueue queue = Volley.newRequestQueue(context);  // this = context
        // { "id_user": "1" , "foodname": "2019", "qte": "1", "calories": "2" }

        final String url = ip_address+"/pc/api/getUserByEmailAndPassword";
        Map<String,String> params = new HashMap<String, String>();
        params.put("email",email);
        params.put("password",password);


// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, new JSONObject( params ),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            boolean error = response.getBoolean("error");


                            if (!error) {
                                // user successfully logged in
                                // Create login session

                                // Now store the user in SQLite

                                JSONObject user = response.getJSONObject("user");
                                int id = user.getInt( "id" );
                                String first_name = user.getString("first_name");
                                String last_name = user.getString("last_name");
                                Toast.makeText(context, "Welcome "+first_name+" "+last_name, Toast.LENGTH_LONG).show();

                                Log.d( TAG, "Welcome "+first_name+" "+last_name );
                                volleyCallbackUserLogin.onSuccessResponse( id , email, password );
                                // Inserting row in users table

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = response.getString("error_msg");
                                Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            volleyCallbackUserLogin.onFail( e.getMessage() );
                        }


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                        Log.d( TAG, "onErrorResponse: " );
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }

    public void deleteFoodHistory(int id ,  VolleyCallbackDeleteFood volleyCallbackDeleteFood){





        final RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.start();
        String url = DBhelper.ip_address+"/pc/api/deleteFood/"+id;
        StringRequest dr = new StringRequest( Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        volleyCallbackDeleteFood.onSuccessResponse();
                     //   Toast.makeText(context , "Deleted",Toast.LENGTH_SHORT).show();

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyCallbackDeleteFood.onFail( "Something went wrong" );
                        Log.d( "excp", "onErrorResponse: "+error.getStackTrace()+"ff"+error.getMessage() );
                        Toast.makeText(context , " Error ",Toast.LENGTH_SHORT).show();

                    }
                }
        );

        mRequestQueue.add(dr);
    }




    public void getUserById(int id  , VolleyCallbackGetUserById volleyCallbackGetUserById){

        ArrayList<FoodHistory> foodHistories;

        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/pc/api/getUserById/"+id;

        foodHistories = new ArrayList<>(  );
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject user = new JSONObject(response);
                                UserInfo userInfo = new UserInfo(  ) ;
                                userInfo.setId( user.getInt( "unique_id" ) );
                                userInfo.setFirstname( user.getString( "first_name" ) );
                                userInfo.setEmail( user.getString( "email" ) );
                                userInfo.setActivitylevel( user.getString( "activty" ) );
                                userInfo.setGoal( user.getString( "goal" ) );
                                userInfo.setGender( user.getString( "gender" ) );
                                userInfo.setDateOfBirth( user.getString( "birthday" ) );
                                userInfo.setCurrentWheight( Float.valueOf(  user.getString( "weight" ) ));
                                userInfo.setHeight( Float.valueOf( user.getString( "height" ) ));
                                userInfo.setRole( user.getString( "role"));
                            Log.d( TAG, "onResponse: "+userInfo ); ;
                            volleyCallbackGetUserById.onSuccessResponse( userInfo );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        volleyCallbackGetUserById.onFail( error.getMessage() );
                    }
                });


// add it to the RequestQueue
        queue.add(stringRequest);

    }


    public  float calculateBMR(UserInfo userInfo ){
        float BMR;
        float cal;
        int age =   DateConverter.getAge( userInfo.getDateOfBirth());
        if (userInfo.getGender().equals("male"))
        {
            BMR = (int) (66 + (6.23 * userInfo.getCurrentWheight()) + (12.7 * userInfo.getHeight()) - (6.8 * age));
        }
        else
        {
            BMR = (int) (665 + (4.35 * userInfo.getCurrentWheight()) + (4.7 * userInfo.getHeight()) - (4.7 * age));
        }

        if (userInfo.getActivitylevel().equals( "low Active" ))
        {
            cal = (BMR * 1.375f);
        }
        else if (userInfo.getActivitylevel().equals( "Active" ))
        {
            cal = (BMR * 1.55f);
        }
        else if (userInfo.getActivitylevel().equals( "Very Active" ))
        {
            cal = (BMR * 1.725f);
        }
        else
        {
            cal = (BMR * 1.9f);
        }

        Toast.makeText( context , String.valueOf( cal ), Toast.LENGTH_SHORT ).show();
return cal;
    }

    public void updateWheightAndMBR( int id , float weight , float dailycal , VolleyCallbackUpdateWeightMBR volleyCallbackUpdateWeightMBR){




            RequestQueue queue = Volley.newRequestQueue(context);  // this = context

            final String url = ip_address+"/pc/api/updateWeight";
            Map<String,String> params = new HashMap<String, String>();
            params.put("id",String.valueOf( id ));
            params.put("weight",String.valueOf(weight  ));
            params.put("dailycal",String.valueOf( dailycal ));


            JsonObjectRequest getRequest = new JsonObjectRequest( Request.Method.PUT, url, new JSONObject( params ),
                    new com.android.volley.Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                boolean error = response.getBoolean("error");


                                if (!error) {



                                    volleyCallbackUpdateWeightMBR.onSuccessResponse( "Weight and BMR updated!" );

                                } else {
                                    String errorMsg = response.getString("error_msg");
                                    volleyCallbackUpdateWeightMBR.onFail( "Something went wrong" );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                volleyCallbackUpdateWeightMBR.onFail( "Something went wrong" );
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            volleyCallbackUpdateWeightMBR.onFail( error.getMessage() );
                            error.getStackTrace();
                            Log.d( TAG, "onErrorResponse: " );
                            Log.d("Error.Response", error.getMessage());
                        }
                    }
            );

            queue.add(getRequest);


    }



    public void getTodayCaloriesAndMBR(int id ,VolleyCallbackTodayCalMBR callback ) {
        ArrayList<FoodHistory> foodHistories;

        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/pc/api/getTodayCalories/"+id;

        foodHistories = new ArrayList<>();
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object = new JSONObject(response);

                            callback.onSuccessResponse( object.getString( "cals" ) ,object.getString( "mbr" ) ) ;



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        callback.onFail( error.getMessage() );
                    }
                });


// add it to the RequestQueue
        queue.add(stringRequest);

    }

    public void addWeight(int id_user , float weight){



        RequestQueue queue = Volley.newRequestQueue(context);  // this = context

        final String url = ip_address+"/pc/api/addWeigh";
        Map<String,String> params = new HashMap<String, String>();
        params.put("id_user",String.valueOf( id_user ));
        params.put("weight",String.valueOf(weight  ));



        JsonObjectRequest getRequest = new JsonObjectRequest( Request.Method.POST, url, new JSONObject( params ),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            boolean error = response.getBoolean("error");


                            if (!error) {




                            } else {
                                String errorMsg = response.getString("error_msg");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                        Log.d( TAG, "onErrorResponse: " );
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.add(getRequest);



    }


    public void getWeightHistory(int id , VolleyCallback callback){

        ArrayList<WeightHistory> weightHistories;

        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/pc/api/getWeightHistory/"+id;
        final ArrayList<Entry> data = new ArrayList<>();

        weightHistories = new ArrayList<>(  );
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("weights");
                            for (int i = 0; i < array.length(); i++){
                                JSONObject j = array.getJSONObject(i);
                                data.add( new Entry(  Float.valueOf(  j.getString( "date" ) )  , Float.valueOf(  j.getString( "weight" ))));

                            }
                            callback.onSuccessResponse( data );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        callback.onFail( error.getMessage() );
                    }
                });


// add it to the RequestQueue
        queue.add(stringRequest);
    }

    public void addRunningDate(int id_user   , Double distance , String avg_speed  , Double calories_burned){


        RequestQueue queue = Volley.newRequestQueue(context);  // this = context
        // { "id_user": "1" , "foodname": "2019", "qte": "1", "calories": "2" }

        final String url = ip_address+"/pc/api/addRunningDate";
        Map<String,String> params = new HashMap<String, String>();
        params.put("id_user",String.valueOf( id_user));
        params.put("distance",String.valueOf( distance ));
        params.put("avg_speed", avg_speed );
        params.put("calories_burned",String.valueOf( calories_burned ));

        Log.d( TAG, "addFood: "+new JSONObject( params ) );
// prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, new JSONObject( params ),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getStackTrace();
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }
    public void getRunningDate(int id , VolleyCallbackRunningData callback){

        ArrayList<WeightHistory> weightHistories;

        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/pc/api/getRunningDate/"+id;
        ArrayList<RunningData> runningData = new ArrayList<>(  );
        weightHistories = new ArrayList<>(  );
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("runningData");
                            for (int i = 0; i < array.length(); i++){
                                JSONObject j = array.getJSONObject(i);


                                String distance ="0.0" ;
                                try {
                                    distance=    j.getString( "distance" ).substring(0, 4);
                                }catch (StringIndexOutOfBoundsException e){
                                    distance = "0.0";
                                }

                                String calories_burned ="0.0" ;
                                try {
                                    calories_burned=   j.getString( "calories_burned" ).substring(0, 4);
                                }catch (StringIndexOutOfBoundsException e){
                                    calories_burned = "0.0";
                                }
                                runningData.add( new RunningData( Double.parseDouble(distance ) ,j.getString( "avg_speed" ) ,Double.parseDouble( calories_burned) ,j.getString( "date" )) );
                            }
                            callback.onSuccessResponse( runningData );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        callback.onFail( error.getMessage() );
                    }
                });


// add it to the RequestQueue
        queue.add(stringRequest);
    }



    public void updateStepTarget( int id , int dailySteps , VolleyCallbackUpdateDailySteps volleyCallbackUpdateDailySteps){




        RequestQueue queue = Volley.newRequestQueue(context);  // this = context

        final String url = ip_address+"/pc/api/updateStepTarget";
        Map<String,String> params = new HashMap<String, String>();
        params.put("unique_id",String.valueOf( id ));
        params.put("sterp_target",String.valueOf(dailySteps));


        JsonObjectRequest getRequest = new JsonObjectRequest( Request.Method.PUT, url, new JSONObject( params ),
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            boolean error = response.getBoolean("error");


                            if (!error) {



                                volleyCallbackUpdateDailySteps.onSuccessResponse( "Weight and BMR updated!" );

                            } else {
                                String errorMsg = response.getString("error_msg");
                                volleyCallbackUpdateDailySteps.onFail( "Something went wrong" );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            volleyCallbackUpdateDailySteps.onFail( "Something went wrong" );
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyCallbackUpdateDailySteps.onFail( error.getMessage() );
                        error.getStackTrace();
                        Log.d( TAG, "onErrorResponse: " );

                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.add(getRequest);


    }

    public void getDalyStepsById(int id  , VolleyCallbackGetDailySteps volleyCallbackGetDailySteps){

        ArrayList<FoodHistory> foodHistories;

        RequestQueue queue = Volley.newRequestQueue(context);
        final String url = ip_address+"/pc/api/getUserById/"+id;

        foodHistories = new ArrayList<>(  );
        Log.i("link",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject step = new JSONObject(response);
                             String steps = step.getString( "sterp_target" ) ;
                            volleyCallbackGetDailySteps.onSuccessResponse( steps );



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("Erreur "+error.getMessage());
                        volleyCallbackGetDailySteps.onFail( error.getMessage() );
                    }
                });


// add it to the RequestQueue
        queue.add(stringRequest);

    }


    public interface VolleyCallback {
        void onSuccessResponse(ArrayList<Entry>  result);
        void onFail(String msg);
    }

    public interface VolleyCallbackWeightHistory {
        void onSuccessResponse(HashMap<Float, String>  result);
        void onFail(String msg);
    }

    public interface VolleyCallbackList {
        void onSuccessResponse(ArrayList<FoodHistory>  result);
        void onFail(String msg);
    }
    public interface VolleyCallbackUserLogin {
        void onSuccessResponse(int id , String email,   String password);
        void onFail(String msg);
    }

    public interface VolleyCallbackDeleteFood {
        void onSuccessResponse();
        void onFail(String msg);
    }
    public interface VolleyCallbackGetUserById {
        void onSuccessResponse(UserInfo userInfo);
        void onFail(String msg);
    }
    public interface VolleyCallbackUpdateWeightMBR {
        void onSuccessResponse(String succes_msg);
        void onFail(String fail_msg);
    }
    public interface VolleyCallbackUpdateDailySteps {
        void onSuccessResponse(String succes_msg);
        void onFail(String fail_msg);
    }

    public interface VolleyCallbackTodayCalMBR {
        void onSuccessResponse(String cal  , String  mbr);
        void onFail(String fail_msg);
    }

    public interface VolleyCallbackRunningData {
        void onSuccessResponse(ArrayList<RunningData>  result);
        void onFail(String msg);
    }



    public interface VolleyCallbackGetDailySteps {
        void onSuccessResponse(String steps);
        void onFail(String fail_msg);
    }

}
