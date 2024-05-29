package com.nimesa.assignment.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

   @JsonProperty("status_code")
   private int statusCode;

   @JsonProperty("status_message")
   private String statusMessage;

   private T data;

   public BaseResponse(int statusCode, String statusMessage) {
       this.statusCode = statusCode;
       this.statusMessage = statusMessage;
   }
}
