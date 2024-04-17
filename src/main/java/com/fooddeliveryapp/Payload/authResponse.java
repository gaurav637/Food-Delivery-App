package com.fooddeliveryapp.Payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class authResponse {

	private String jwtToken;
    private String refreshToken;
    private String username;
}
