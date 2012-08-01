package com.zenika.blog.rpm;

import java.io.IOException;

public class UserNotFoundException extends IOException {
	private static final long serialVersionUID = -7684705707703516442L;
	
	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
