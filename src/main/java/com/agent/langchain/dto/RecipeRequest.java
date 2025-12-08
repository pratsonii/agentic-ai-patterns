package com.agent.langchain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for recipe development using sequential flow pattern.
 */
public class RecipeRequest {

    @NotBlank(message = "Cuisine cannot be empty")
    @Size(min = 2, max = 100, message = "Cuisine must be between 2 and 100 characters")
    private String cuisine;

    @NotBlank(message = "Dietary preferences cannot be empty")
    @Size(min = 2, max = 200, message = "Dietary preferences must be between 2 and 200 characters")
    private String dietary;

    @NotBlank(message = "Meal type cannot be empty")
    @Size(min = 2, max = 50, message = "Meal type must be between 2 and 50 characters")
    private String mealType;

    public RecipeRequest() {
    }

    public RecipeRequest(String cuisine, String dietary, String mealType) {
        this.cuisine = cuisine;
        this.dietary = dietary;
        this.mealType = mealType;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDietary() {
        return dietary;
    }

    public void setDietary(String dietary) {
        this.dietary = dietary;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
