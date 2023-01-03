package com.devsu.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomSerializer extends JsonSerializer<Object> {

	public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		ApiData dataDTO = (ApiData) value;
		jgen.writeObjectField(dataDTO.getName(), dataDTO.getData());
		jgen.writeEndObject();
	}
}
