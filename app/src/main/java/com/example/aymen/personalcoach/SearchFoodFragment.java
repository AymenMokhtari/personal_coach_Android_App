package com.example.aymen.personalcoach;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.Session;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.CompactRecipe;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Recipe;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class SearchFoodFragment extends Fragment  {
    Session session ;
    ListView search_food;
    ProgressBar progressBarRDI ;
    SearchView search_input;
    final  String TAG = "FATsecret";
    ArrayList<CompactFood> foodList;
    Food foodR;
    String keyword;
    View view ;
    Float qteFloat = 0f;
    Float calFloat = 0f;
    Float totalCalFloat = 0f;

    public int  j = 0 ;
    DBhelper helper ;
    SharedPreferences prefs;
    int idSP;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_food , container, false);
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);

         idSP =    prefs.getInt( "id" ,0);
        helper = new DBhelper( getActivity() );
        helper.getUserById( idSP, new DBhelper.VolleyCallbackGetUserById() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccessResponse(UserInfo userInfo) {
                Toast.makeText( getActivity() , userInfo.toString() , Toast.LENGTH_SHORT ).show();
                try {


                helper.updateWheightAndMBR( 1, 987, 987, new DBhelper.VolleyCallbackUpdateWeightMBR() {
                    @Override
                    public void onSuccessResponse(String succes_msg) {
                        Toast.makeText( getActivity() , succes_msg, Toast.LENGTH_SHORT );
                    }

                    @Override
                    public void onFail(String fail_msg) {
                        Toast.makeText( getActivity() , fail_msg, Toast.LENGTH_SHORT );

                    }
                } );
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {
                Log.d( TAG, "onFail: "+msg );

            }
        } );
        search_input = view.findViewById(R.id.search_input);
        search_food = view.findViewById( R.id.search_food );
        foodList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String key = "e395d07a1cdc49c0ad8055cc5800077b";
        String secret = "08ab85c648374c779dc709be58422ed8";
        String query = "oat";
        SearchFoodFragment.Listener listener = new SearchFoodFragment.Listener();


        Request req = new Request(key, secret, listener);

        search_input.setOnQueryTextListener( new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                search_food.setAdapter( null );
                foodList.clear();
                Handler mHandler = new Handler();
                Log.d( TAG, "onQueryTextChange: "+s );
                req.searchFoods(requestQueue, s);
                FoodCustomAdapter   foodAdapter = new FoodCustomAdapter(getActivity(), R.layout.one_item, foodList);
                foodAdapter.notifyDataSetChanged();
                search_food.setAdapter(foodAdapter);
                search_food.deferNotifyDataSetChanged();
                return false;

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();





        Log.d( TAG, "onCreate: search food"+req.toString() );
        //  req.searchFoods( requestQueue,"egg" );
        Log.d( TAG, "onCreate: searc food " );
        //This response contains the list of food items at zeroth page for your query

        //This response contains the list of food items at page number 3 for your query
        //If total results are less, then this response will have empty list of the food items
        // req.searchFoods(requestQueue, query, 3);

        //This food object contains detailed information about the food item

        Log.d( TAG, req.toString() );
        Log.d( TAG, "req: " );
        //This response contains the list of recipe items at zeroth page for your query
        req.searchRecipes(requestQueue, query);

        //This response contains the list of recipe items at page number 2 for your query
        //If total results are less, then this response will have empty list of the recipe items
        //   req.getRecipes(requestQueue, query, 2);

        //This recipe object contains detailed information about the recipe item
        req.getRecipe(requestQueue, 315L);
        Log.d( TAG, "onCreate: " );

        search_food.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CompactFood  food = (CompactFood)adapterView.getItemAtPosition( i );

                req.getFood(requestQueue, food.getId());

            }
        } );
        return  view;
    }

    class Listener implements ResponseListener {
        @Override
        public void onFoodListRespone(Response<CompactFood> response) {
            System.out.println("TOTAL FOOD ITEMS: " + response.getTotalResults());

            List<CompactFood> foods = response.getResults();
            //This list contains summary information about the food items

            System.out.println("=========FOODS============");
            for (CompactFood food: foods) {
                System.out.println(food.getName());

                foodList.add( food);
                Log.d( TAG, "onFoodListRespone: " );
            }
        }


        @Override
        public void onRecipeListRespone(Response<CompactRecipe> response) {
            System.out.println("TOTAL RECIPES: " + response.getTotalResults());

            List<CompactRecipe> recipes = response.getResults();
            System.out.println("=========RECIPES==========");
            for (CompactRecipe recipe: recipes) {

                Log.d( TAG, "onRecipeListRespone: " );
                System.out.println(recipe.getName());

            }
        }

        @Override
        public void onFoodResponse(Food food) {
            Log.d( TAG, "onFoodResponse: " );
            foodR = food;
            food.getDescription();
            List<String> spinnerArray =  new ArrayList<String>();

            for ( int i = 0; i < food.getServings().size(); i++){

                spinnerArray.add(food.getServings().get( i ).getServingDescription());


            }

            if (!getActivity().isFinishing()) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.food_popuplayout, (ViewGroup) view.findViewById(R.id.popup_element), false);
                final PopupWindow pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                progressBarRDI  = layout.findViewById( R.id.rdiProgress );
                progressBarRDI.setProgressTintList( ColorStateList.valueOf( Color.CYAN));

                progressBarRDI.setProgress( 50 );
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                Spinner spinner = layout.findViewById( R.id.mesumrementUnitsSpiner );
                spinner.setAdapter( adapter );

                EditText qte = layout.findViewById( R.id.qteText );
                TextView nametxt =  layout.findViewById(R.id.foodnamePop);
                TextView desctxt = layout.findViewById(R.id.foodDesPcop);
                TextView calorie = layout.findViewById(R.id.caloriesTxt);
                TextView protein = layout.findViewById(R.id.proteinTxt);
                TextView carb = layout.findViewById(R.id.carbsTxt);
                TextView fibre = layout.findViewById(R.id.fiberTxt);
                TextView calcium = layout.findViewById(R.id.calciumTxt);
                TextView fat = layout.findViewById(R.id.fatTxt);

                qte.setText( "1" );
                nametxt.setText( food.getName() );
                desctxt.setText( foodR.getDescription());
                Button btnAddFood  = layout.findViewById( R.id.btn_add_food );
                btnAddFood.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText( getActivity(), String.valueOf( qteFloat ), Toast.LENGTH_SHORT ).show();
                           helper.addFood( idSP ,food.getName() , qteFloat,totalCalFloat  );
                        helper.getTodayCaloriesAndMBR( idSP, new DBhelper.VolleyCallbackTodayCalMBR() {
                            @Override
                            public void onSuccessResponse(String cal , String mbr) {

try {

                           mListener.onFragmentInteraction(cal+ " Calories eaten today/ "+mbr);
}catch (Exception e){

}

                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        } );


// prepare the Request

                    }
                } );
                //init your button
                Button btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
                btnClosePopup.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        desctxt.setText( food.getDescription());

                        Log.d( TAG, "onClick: sqdf" );
                          pwindo.dismiss();

                    }
                });
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                BigDecimal cal =  food.getServings().get( position ).getCalories();
                BigDecimal protb =  food.getServings().get( position ).getProtein();
                BigDecimal carbb =  food.getServings().get( position ).getCarbohydrate();
                BigDecimal fatb =  food.getServings().get( position ).getFat();
                BigDecimal fibreb =  food.getServings().get( position ).getFiber();
                BigDecimal calcuimb =  food.getServings().get( position ).getCalcium();

                j = position;
                        try {


                             calFloat =  StringtoFloat (cal) ;
                            Float protFloat = StringtoFloat (protb);
                            Float carbFloat =StringtoFloat (carbb);
                            Float fatFloat =StringtoFloat (fatb);
                            Float fibreFloat =StringtoFloat (fibreb);
                            Float calcuimFloat =StringtoFloat (calcuimb);


                   qteFloat  =  Float.parseFloat( qte.getText().toString() );
                            totalCalFloat = calFloat * qteFloat;

                    calorie.setText( String.valueOf( calFloat *qteFloat));
                    protein.setText( String.valueOf( qteFloat*protFloat ) );
                    fibre.setText( String.valueOf( qteFloat*fibreFloat) );
                    carb.setText( String.valueOf( qteFloat*carbFloat ) );
                    calcium.setText( String.valueOf( qteFloat*calcuimFloat ) );
                    fat.setText( String.valueOf( fatFloat*protFloat ) );

                    progressBarRDI.setProgress( Math.round( (calFloat * qteFloat *100)/3000) );

                }catch (NumberFormatException e) {

                }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });                //show popup window after you have done initialization of views



                qte.addTextChangedListener( new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        BigDecimal cal =  food.getServings().get( j ).getCalories();
                        BigDecimal protb =  food.getServings().get( j ).getProtein();
                        BigDecimal carbb =  food.getServings().get( j ).getCarbohydrate();
                        BigDecimal fatb =  food.getServings().get( j ).getFat();
                        BigDecimal fibreb =  food.getServings().get( j ).getFiber();
                        BigDecimal calcuimb =  food.getServings().get( j ).getCalcium();
                        try {
                            qteFloat  =  Float.parseFloat( qte.getText().toString() );

                        }catch (NumberFormatException e){
                            Log.d( TAG, "onTextChanged: "+e.getMessage() );
                            qteFloat=0f;
                        }
                        Log.d( TAG, "onTextChanged: "+qteFloat );
                        Toast.makeText( getActivity(), String.valueOf( qteFloat ), Toast.LENGTH_SHORT ).show();

                         calFloat =  StringtoFloat (cal) ;
                        Float protFloat = StringtoFloat (protb);
                        Float carbFloat =StringtoFloat (carbb);
                        Float fatFloat =StringtoFloat (fatb);
                        Float fibreFloat =StringtoFloat (fibreb);
                        Float calcuimFloat =StringtoFloat (calcuimb);
                totalCalFloat = calFloat * qteFloat;
                            calorie.setText( String.valueOf( calFloat *qteFloat));
                            protein.setText( String.valueOf( qteFloat*protFloat ) );
                            fibre.setText( String.valueOf( qteFloat*fibreFloat) );
                            carb.setText( String.valueOf( qteFloat*carbFloat ) );
                            calcium.setText( String.valueOf( qteFloat*calcuimFloat ) );
                            fat.setText( String.valueOf( fatFloat*protFloat ) );

                            progressBarRDI.setProgress( Math.round( (calFloat * qteFloat *100)/3000) );


                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                } );

                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        }


        @Override
        public void onRecipeResponse(Recipe recipe) {
            Log.d( TAG, "onRecipeResponse: " );
            System.out.println("RECIPE NAME: " + recipe.getName());
        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomePage)getActivity()).setActionBarTitle("");

    }
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }


    public Float StringtoFloat(BigDecimal x){

        Float result ;
        try {
         result = x.floatValue();
        }catch (Exception e){
            result =0f;
        }
        return  result;
    }
}
