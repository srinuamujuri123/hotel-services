package com.hotelservices.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelservices.common.CommonConstants.Hotel;
import com.hotelservices.dao.HotelDao;
import com.hotelservices.model.HotelDetails;
import com.hotelservices.model.TMSResponse;
import com.hotelservices.model.TMSResponse.Status;
import com.hotelservices.service.HotelService;
import com.hotelservices.utils.DateUtils;
import com.hotelservices.utils.TMSUtils;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	HotelDao hotelDao;

	@Override
	public TMSResponse saveOrUpdateHotelDetails(HotelDetails hotelDetails) {
		TMSResponse response = new TMSResponse();
		try {
			HotelDetails hotelNameObjFromDb = hotelDao.findByHotelName(hotelDetails.getHotelName());
			boolean isHotelExist = (hotelNameObjFromDb != null) && (hotelNameObjFromDb.getCityName() != null)
					&& (hotelNameObjFromDb.getCityName().equalsIgnoreCase(hotelDetails.getCityName()));
			if (isHotelExist) {
				response.setDetails(Hotel.HOTELEXIST);
			} else {
				hotelDetails.setCreatedOn(DateUtils.getTodayDate());
				hotelDetails.setUpdatedOn(DateUtils.getTodayDate());
				HotelDetails hotelDetailsDb = saveHotelDetails(hotelDetails);
				hotelDetails.setActive(true);
				response.setData(hotelDetailsDb);
				response.setDetails(Hotel.SAVE);
			}
			response.setStatus(Status.OK);
		} catch (Exception e) {

			response.setErrorMessage(TMSUtils.getExceptionDetails(e));
			response.setDetails(Hotel.ERROR);
			response.setStatus(Status.FAILED);
		}
		return response;
	}
	

	public HotelDetails saveHotelDetails(HotelDetails hotelDetails) {
		return hotelDao.save(hotelDetails);
	}


	@Override
	public TMSResponse getHotelDetailsById(Integer hotelId) {
		TMSResponse response = new TMSResponse();
		try {
			HotelDetails hotelDetailsById = hotelDao.findByHotelId(hotelId);
			if (hotelDetailsById != null) { // isPresent() and .get are optional method.
				response.setData(hotelDetailsById);
			} else {
				response.setData("Oops no data found for " + hotelId);
			}
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setDetails("Oops, Unable to fetch Data, please try after some time.");
			response.setErrorMessage(TMSUtils.getExceptionDetails(e));
			response.setStatus(Status.FAILED);
		}
		return response;
	}

	@Override
	public TMSResponse getHotelDetails(Boolean isActive, String search) {
		TMSResponse response = new TMSResponse();
		List<HotelDetails> hotelDetailsList = new ArrayList<HotelDetails>();
		try {
			if (StringUtils.isNotEmpty(search)) {
				// if(search != null || !search.isEmpty()) {
				hotelDetailsList = hotelDao.findAllByIsActiveAndHotelNameContaining(isActive, search);
			} else {
				hotelDetailsList = hotelDao.findAllByIsActive(isActive);
			}
			if (CollectionUtils.isNotEmpty(hotelDetailsList)) {
				// if(hotelDetailsList != null || hotelDetailsList.size() >0) {
				response.setData(hotelDetailsList);
				response.setCount(hotelDetailsList.size());
			} else {
				response.setDetails(Hotel.LISTNOTFOUND);
			}
			// return response.setStatus(Status.OK);
			response.setStatus(Status.OK);
			return response;

		} catch (Exception e) {
			response.setDetails("Oops, Unable to fetch Data");
			response.setErrorMessage(TMSUtils.getExceptionDetails(e));
			response.setStatus(Status.FAILED);
		}

		return response;

	}

	@Override
	public TMSResponse deleteHotelDetailsById(Integer hotelId, boolean status) {
		TMSResponse response = new TMSResponse();
		try {
			HotelDetails deleteHotelDetailsById = hotelDao.findByHotelId(hotelId); // Optional<HotelDetails>
			if (deleteHotelDetailsById != null) {
				if (status) {
					hotelDao.deleteByHotelId(hotelId);
				} else {
					deleteHotelDetailsById.setUpdatedOn(DateUtils.getTodayDate());
					deleteHotelDetailsById.setActive(status);
					response.setData(hotelDao.save(deleteHotelDetailsById));
				}
				response.setDetails(Hotel.DELETED);
			} else {
				response.setDetails(Hotel.RECORDNOTFOUND);
			}
			response.setStatus(Status.OK);
		} catch (Exception e) {
			response.setDetails(Hotel.UNABLETODELETE);
			response.setErrorMessage(TMSUtils.getExceptionDetails(e));
			response.setStatus(Status.FAILED);
		}
		return response;
	}

	@Override
	public HotelDetails findByHotelNameAndCityName(String hotelName, String cityName) {
		return hotelDao.findByHotelNameAndCityName(hotelName, cityName);
	}
}
