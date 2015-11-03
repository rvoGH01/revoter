package com.revoter.rest.controller;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractRestController {
	protected URI getNewResourceUri(Long resourceId) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(resourceId).toUri();
	}
}
