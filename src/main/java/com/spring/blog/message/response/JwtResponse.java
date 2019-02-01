package com.spring.blog.message.response;

public class JwtResponse {
	
   private String token;
   private String type = "JWT";

   public JwtResponse(String accessToken) {
       this.token = accessToken;
   }

   public String getAccessToken() {
       return token;
   }

   public void setAccessToken(String accessToken) {
       this.token = accessToken;
   }

   public String getTokenType() {
       return type;
   }

   public void setTokenType(String tokenType) {
       this.type = tokenType;
   }
}