package com.demo.ts.controllers;

import com.demo.ts.resources.ResponseWrapper;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ReturnedResult {
	
	private final ResponseWrapper responseWrapper;
	
	@JsonCreator
	public ReturnedResult(ResponseWrapper responseWrapper) {
		this.responseWrapper = responseWrapper;
	}

	public ResponseWrapper getResponseWrapper() {
		return responseWrapper;
	}

	
}
