package com.hotelservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelservices.service.HotelService;
import com.tms.client.model.HotelDetails;
import com.tms.client.model.TMSResponse;

@RestController
@RequestMapping("/hotel")
public class HotelController {
	@Autowired
	HotelService hotelService;

	@PostMapping("/save-or-hotel-details")
	public TMSResponse saveOrUpdateHotelDetails(@RequestBody HotelDetails hotelDetails) {
		return hotelService.saveOrUpdateHotelDetails(hotelDetails);
	}

	@GetMapping("/get-hotel-details")
	public TMSResponse getHotelDetails(@RequestParam Boolean isActive, @RequestParam String search) {
		return hotelService.getHotelDetails(isActive, search);
	}

	@GetMapping("/get-hotel-details-by-id")
	public TMSResponse getHotelDetailsById(@RequestParam Integer hotelId) {
		return hotelService.getHotelDetailsById(hotelId);
	}

	@DeleteMapping("delete-hotel-details-by-id")
	public TMSResponse deleteHotelDetailsById(@RequestParam Integer hotelId,
			@RequestParam(required = false) boolean status) {
		return hotelService.deleteHotelDetailsById(hotelId, status);
	}

	// Internal Rest call
	@PostMapping("/save-hotel-details")
	public HotelDetails saveHotelDetails(@RequestBody HotelDetails hotelDetails) {
		return hotelService.saveHotelDetails(hotelDetails);
	}

	@GetMapping("/get-hotel-details-by-hotelname-and-cityname")
	public HotelDetails findByHotelNameAndCityName(@RequestParam String hotelName, @RequestParam String cityName) {
		return hotelService.findByHotelNameAndCityName(hotelName, cityName);
	}

}
