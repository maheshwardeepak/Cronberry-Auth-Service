package com.cronberry.service.constants;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;



@Component
public class BaseManagerImpl {
	

	public static ResponseEntity<Map<String, Object>> sendSuccessResponse(final Object result) {
		final Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(UIConstants.STATUS, UIConstants.TRUE);
		resultMap.put(UIConstants.DATA, result);
		resultMap.put(UIConstants.WEB_VERSION, UIConstants.WEB_VERSION_CODE);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}
	
	protected ResponseEntity<Map<String, Object>> sendSuccessResponse(final Object result,int count) {
		final Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(UIConstants.STATUS, UIConstants.TRUE);
		resultMap.put(UIConstants.DATA, result);
		resultMap.put("count", count);
		resultMap.put(UIConstants.WEB_VERSION, UIConstants.WEB_VERSION_CODE);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}
	
	public static ResponseEntity<Map<String, Object>> sendErrorResponse(final Object msg) {
		final Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(UIConstants.STATUS, UIConstants.FALSE);
		resultMap.put(UIConstants.ERROR_MSG, msg);
		resultMap.put(UIConstants.EMPTY_DATA, "");
		resultMap.put(UIConstants.WEB_VERSION, UIConstants.WEB_VERSION_CODE);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}
	
	protected ResponseEntity<Map<String, Object>> sendErrorResponseBlank(final Object msg) {
		final Map<String, Object> resultMap = new HashMap<>();
		resultMap.put(UIConstants.STATUS, UIConstants.FALSE);
		resultMap.put(UIConstants.DATA, null);
		resultMap.put(UIConstants.WEB_VERSION, UIConstants.WEB_VERSION_CODE);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}
	
}
