package com.webuildit.bookingapi;

import org.springframework.boot.SpringApplication;

public class TestBookingapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookingapiApplication::main).with().run(args);
	}

}
