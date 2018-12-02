package com.rmi.server.entity;

import java.io.InputStream;
import java.io.Serializable;

public class FlowImage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InputStream imageStream;

	public InputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(InputStream imageStream) {
		this.imageStream = imageStream;
	}
	
}
