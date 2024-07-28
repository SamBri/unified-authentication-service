package com.nothing.security.response;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor 
@NoArgsConstructor
public class RootResponse<T> {
	
	private int code;
	
	private String message;
	
	private T response;
	
	private String status;
	
    private OffsetDateTime timeStamp;
	

}
