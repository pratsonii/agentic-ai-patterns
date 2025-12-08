package com.agent.langchain.dto;

/**
 * Response DTO for recipe development.
 * Contains the complete recipe with ingredients, cooking instructions, and
 * nutritional information.
 */
public class RecipeResponse {

    private String recipe;

    public RecipeResponse() {
    }

    public RecipeResponse(String recipe) {
        this.recipe = recipe;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
