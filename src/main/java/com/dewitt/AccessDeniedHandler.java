package com.dewitt;

import java.nio.file.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedHandler implements ExceptionMapper<AccessDeniedException>{

	@Override
	public Response toResponse(AccessDeniedException arg0) {
		return Response.status(403).type("text/plain").entity("Access forbidden, please authenticate.").build();
	}

}
