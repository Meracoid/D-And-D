package com.dd.exceptions;

public class UnknownMonsterException extends RuntimeException {
	
	public UnknownMonsterException(String message){
		super(message);
	}
}