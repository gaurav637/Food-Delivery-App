package com.fooddeliveryapp.Response;

import java.util.List;
import com.fooddeliveryapp.Model.Restaurant;

public class ResponseWrapper {
	
	private List<Restaurant> restaurants;
    private String errorMessage;

    public ResponseWrapper(List<Restaurant> restaurants, String errorMessage) {
        this.restaurants = restaurants;
        this.errorMessage = errorMessage;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
