package com.hotelservices.service;

import com.tms.client.model.HotelDetails;
import com.tms.client.model.TMSResponse;

public interface HotelService {

	TMSResponse saveOrUpdateHotelDetails(HotelDetails hotelDetails);

	TMSResponse getHotelDetailsById(Integer id);

	TMSResponse deleteHotelDetailsById(Integer hotelId, boolean status);

	TMSResponse getHotelDetails(Boolean isActive, String search);

	HotelDetails saveHotelDetails(HotelDetails hotelDetails);

	HotelDetails findByHotelNameAndCityName(String hotelName, String cityName);
}
