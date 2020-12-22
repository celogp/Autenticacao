package com.sw1tech.app.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateCompon {

	public DateCompon() {
		System.out.println("passou na criação DateCompon");
	}
	
	public String formatLocalDateTime(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss").format(localDateTime);
	}
}
