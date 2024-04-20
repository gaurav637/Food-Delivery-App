package com.fooddeliveryapp.Services;

import com.fooddeliveryapp.DTO.refreshTokenValidation;
import com.fooddeliveryapp.Model.User;
import com.fooddeliveryapp.Payload.authRequest;
import com.fooddeliveryapp.Payload.authResponse;

public interface authenticationServices {
    User signUpUser(User user);
    
    authResponse signInUser(authRequest auth);
    
    authResponse refreshToken(refreshTokenValidation refreshTokenValidation);
}
