package com.hotelservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelservices.model.HotelDetails;
import com.hotelservices.model.TMSResponse;
import com.hotelservices.service.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelController {
	@Autowired
	HotelService hotelService;

	@PostMapping("/save-hotel-details")
	public HotelDetails saveHotelDetails(@RequestBody HotelDetails hotelDetails) {
		return hotelService.saveHotelDetails(hotelDetails);
	}

	@GetMapping("/get-hotel-details")
	public HotelDetails getHotelDetails(@RequestParam Boolean isActive, @RequestParam String search) {
		return hotelService.getHotelDetails(isActive, search);
	}

	@GetMapping("/get-hotel-details-by-id")
	public HotelDetails getHotelDetailsById(@RequestParam Integer hotelId) {
		return hotelService.getHotelDetails(hotelId);
	}

	@DeleteMapping("delete-hotel-details-by-id")
	public HotelDetails deleteHotelDetailsById(@RequestParam Integer hotelId,
			@RequestParam(required = false) boolean status) {
		return hotelService.deleteHotelDetailsById(hotelId, status);
	}

}
