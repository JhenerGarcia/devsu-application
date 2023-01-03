package com.devsu.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult {

	private String code;
	private String message;
	private LocalDateTime timestamp;

	@JsonProperty("data")
	@JsonSerialize(using = CustomSerializer.class)
	private ApiData data;

}
