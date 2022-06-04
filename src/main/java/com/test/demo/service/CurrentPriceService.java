package com.test.demo.service;

import com.test.demo.model.CurrentPrice;
import com.test.demo.DAO.CurrentPriceInterfaceDAO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentPriceService {

	@Autowired
	private CurrentPriceInterfaceDAO currentPriceInterfaceDAO;

	public CurrentPrice createOne(CurrentPrice request) {

		CurrentPrice currentPrice = new CurrentPrice();
		currentPrice.setCode(request.getCode());
		currentPrice.setCode_nameCh(request.getCode_nameCh());
		currentPrice.setUpd_time(new Date());
		return currentPriceInterfaceDAO.save(currentPrice);
	}

	public CurrentPrice getCurrentPrice(Integer id) {

		return currentPriceInterfaceDAO.findById(id).orElse(null);
	}
	
	public CurrentPrice getCurrentPriceByCode(String code) {
		return currentPriceInterfaceDAO.findByCode(code);
	}

	public CurrentPrice updCurrentPrice(Integer id, CurrentPrice request) {

		CurrentPrice currentPrice = getCurrentPrice(id);
		if (null != request.getCode()) {
			currentPrice.setCode(request.getCode());
		}
		if (null != request.getCode_nameCh()) {
			currentPrice.setCode_nameCh(request.getCode_nameCh());
		}
		currentPrice.setUpd_time(new Date());
		return currentPriceInterfaceDAO.save(currentPrice);
	}

	public void deleteOne(Integer id) {

		CurrentPrice currentPrice = getCurrentPrice(id);
		currentPriceInterfaceDAO.deleteById(currentPrice.getId());
	}

	public List<CurrentPrice> getAll() {

		List<CurrentPrice> result = (List) currentPriceInterfaceDAO.findAll();
		return result;
	}

}