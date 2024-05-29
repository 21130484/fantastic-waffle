package DAO;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.Recipes;

public class DatabaseHelper {

    public static List<Recipes> getAllRecipes(SQLiteDatabase db) {
        List<Recipes> recipesList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String instructions = cursor.getString(2);
                String image = cursor.getString(3);
                Recipes recipe = new Recipes(id, title, instructions, image);
                recipesList.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return recipesList;
    }

    public static List<Recipes> getRecipeFavorite(SQLiteDatabase db, int user_id) {
        List<Recipes> recipesList = new ArrayList<>();
        String query = "SELECT recipes.id, recipes.title, recipes.instructions, recipes.image " +
                "FROM recipes " +
                "INNER JOIN favorite_recipes ON recipes.id = favorite_recipes.recipe_id " +
                "WHERE favorite_recipes.user_id = ?";

        Cursor cursor = db.rawQuery(query,  new String[]{String.valueOf(user_id)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String instructions = cursor.getString(2);
                String image = cursor.getString(3);
                Recipes recipe = new Recipes(id, title, instructions, image);
                recipesList.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return recipesList;
    }

    public static String getIngredientName(SQLiteDatabase db, int recipeId) {
        String re = "";
        String query = "Select name from ingredients where recipe_id = ?";
        Cursor cursor = db.rawQuery(query,  new String[]{String.valueOf(recipeId)});
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                re += name + "\n";
            } while (cursor.moveToNext());
        }
        cursor.close();
        return re;
    }
}
