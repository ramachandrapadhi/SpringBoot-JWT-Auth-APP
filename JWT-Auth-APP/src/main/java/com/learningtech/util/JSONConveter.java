package com.learningtech.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConveter {

	private static ObjectMapper mapper = new ObjectMapper();

	public static <K, V> String mapToJson(Map<K, V> map) throws JsonProcessingException {
		return mapper.writeValueAsString(map);
	}

	public static String objToJson(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	@SuppressWarnings("unchecked")
	public static <V, K, T> Map<K, V> jsonToMap(String jsonText, Class<T> type)
			throws JsonMappingException, JsonProcessingException {
		return (Map<K, V>) mapper.readValue(jsonText, (Class<T>) type);
	}

	public static <T> T jsonToObj(String jsonText, Class<T> type) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(jsonText, (Class<T>) type);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonArrayToListOfObj(String jsonArray, Class<T> type)
			throws JsonMappingException, JsonProcessingException {
		return (List<T>) mapper.readValue(jsonArray, new TypeReference<List<Object>>() {
		});
	}

}
