package com.hotelservices.service;

import com.hotelservices.model.HotelDetails;
import com.hotelservices.model.TMSResponse;

public interface HotelService {

	HotelDetails saveHotelDetails(HotelDetails hotelDetails);

	HotelDetails getHotelDetails(Integer id);

	HotelDetails deleteHotelDetailsById(Integer hotelId, boolean status);

	HotelDetails getHotelDetails(Boolean isActive, String search);
}
