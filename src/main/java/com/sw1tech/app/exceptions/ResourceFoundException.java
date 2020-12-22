package com.sw1tech.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND)
public class ResourceFoundException  extends RuntimeException {
	private static final long serialVersionUID = 2787231853674689098L;

	public ResourceFoundException(String message) {
        super(message);
    }
	
}
