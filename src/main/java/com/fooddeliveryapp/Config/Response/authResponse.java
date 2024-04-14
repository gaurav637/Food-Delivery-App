package com.fooddeliveryapp.Config.Response;

import com.fooddeliveryapp.Model.USER_ROLE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class authResponse {

	private String jwtToken;
    private String message;
    private USER_ROLE role;
}
