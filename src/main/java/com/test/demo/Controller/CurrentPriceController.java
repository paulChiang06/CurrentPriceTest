package com.test.demo.Controller;

import com.test.demo.model.CurrentPrice;

import org.springframework.http.MediaType;
import com.test.demo.service.CurrentPriceService;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrentPriceController {

	@Autowired
	private CurrentPriceService currentPriceService;

	@GetMapping("/{id}")
	public ResponseEntity<CurrentPrice> getCurrentPrice(@PathVariable("id") Integer id) {

		CurrentPrice currentPrice = currentPriceService.getCurrentPrice(id);
		return ResponseEntity.ok(currentPrice);
	}

	@GetMapping
	public ResponseEntity<List<CurrentPrice>> getAll() {

		List<CurrentPrice> currentPriceList = currentPriceService.getAll();
		return ResponseEntity.ok(currentPriceList);
	}

	@GetMapping("/original")
	public ResponseEntity<JSONObject> getOriginalData() throws MalformedURLException, IOException {
		JSONObject json = new JSONObject();
		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8")); // 避免中文亂碼問題
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			json = JSONObject.fromObject(sb.toString());
		} finally {
			is.close();
		}

		return ResponseEntity.ok(json);
	}

	@GetMapping("/conversion")
	public ResponseEntity<List<Map<String, Object>>> getConversionData() throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8")); // 避免中文亂碼問題
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			JSONObject json = JSONObject.fromObject(sb.toString());
			
			JSONObject time = JSONObject.fromObject(json.get("time"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.GERMANY);
			String date = time.get("updatedISO").toString().replaceAll("\\+0([0-9]){1}\\:00", "+0$100");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ISO8601DATEFORMAT.parse(date));

			JSONObject bpi = JSONObject.fromObject(json.get("bpi"));
			JSONObject USD = JSONObject.fromObject(bpi.get("USD"));
			result.add(setMap(sdf.format(calendar.getTime()),USD));
			
			JSONObject GBP = JSONObject.fromObject(bpi.get("GBP"));
			result.add(setMap(sdf.format(calendar.getTime()),GBP));
			
			JSONObject EUR = JSONObject.fromObject(bpi.get("EUR"));
			result.add(setMap(sdf.format(calendar.getTime()),EUR));
		} finally {
			is.close();
		}

		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<CurrentPrice> createOne(@RequestBody CurrentPrice request) {

		CurrentPrice currentPrice = currentPriceService.createOne(request);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(currentPrice.getId()).toUri();

		return ResponseEntity.created(location).body(currentPrice);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CurrentPrice> updateOne(@PathVariable("id") Integer id, @RequestBody CurrentPrice request) {

		CurrentPrice currentPrice = currentPriceService.updCurrentPrice(id, request);
		return ResponseEntity.ok(currentPrice);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteOne(@PathVariable("id") Integer id) {

		currentPriceService.deleteOne(id);
		return ResponseEntity.noContent().build();
	}
	
	public Map<String, Object> setMap(String date,JSONObject jsonObject) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("upd_time", date);
		map.put("code", jsonObject.get("code"));
		map.put("symbol", jsonObject.get("symbol"));
		map.put("rate", jsonObject.get("rate"));
		map.put("description", jsonObject.get("description"));
		CurrentPrice currentPrice=currentPriceService.getCurrentPriceByCode(jsonObject.get("code").toString());
		if(null != currentPrice ) {
			map.put("chname", currentPrice.getCode_nameCh());
		}		
		map.put("rate_float", jsonObject.get("rate_float"));
		return map;
	}
}
