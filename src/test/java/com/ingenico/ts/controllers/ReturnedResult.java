package com.ingenico.ts.controllers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ingenico.ts.resources.ResponseWrapper;

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
