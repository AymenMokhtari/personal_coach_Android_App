

package com.example.aymen.personalcoach;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;

        import com.android.volley.RequestQueue;
        import com.android.volley.toolbox.Volley;

        import com.fatsecret.platform.model.CompactFood;
        import com.fatsecret.platform.model.CompactRecipe;
        import com.fatsecret.platform.model.Food;
        import com.fatsecret.platform.model.Recipe;
        import com.fatsecret.platform.services.Response;
        import com.fatsecret.platform.services.android.Request;
        import com.fatsecret.platform.services.android.ResponseListener;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

        import java.util.List;

public class FatseceretTest extends AppCompatActivity {
final  String TAG = "FATsecret";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_fatseceret_test );
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        String key = "e395d07a1cdc49c0ad8055cc5800077b";
        String secret = "08ab85c648374c779dc709be58422ed8";
        String query = "oat";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);

        Log.d( TAG, "onCreate: search food"+req.toString() );
        req.searchFoods( requestQueue,"egg" );
        Log.d( TAG, "onCreate: searc food " );
        //This response contains the list of food items at zeroth page for your query
        req.searchFoods(requestQueue, query);

        //This response contains the list of food items at page number 3 for your query
        //If total results are less, then this response will have empty list of the food items
     req.searchFoods(requestQueue, query, 3);

        //This food object contains detailed information about the food item
        req.getFood(requestQueue, 29304L);

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
            food.getDescription();
            for ( int i = 0; i < food.getServings().size(); i++){
                Log.d( TAG, "details=================" );
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getMeasurementDescription());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getMetricServingUnit());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getServingDescription());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getServingUrl());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).toString());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getCalcium());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getCalories());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getCarbohydrate());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getCholesterol());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getFat());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getClass());
                Log.d( TAG, "onFoodResponse: "+food.getServings().get( i ).getProtein() );
                Log.d( TAG, "onFoodResponse: "+food.getDescription() );
            }
            System.out.println("FOOD NAME: " + food.getName());

            System.out.println("FOOD NAME: " + food.getUrl());
        }

        @Override
        public void onRecipeResponse(Recipe recipe) {
            Log.d( TAG, "onRecipeResponse: " );
            System.out.println("RECIPE NAME: " + recipe.getName());
        }

    }
}
