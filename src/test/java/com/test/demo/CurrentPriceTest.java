package com.test.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.model.CurrentPrice;
import com.test.demo.service.CurrentPriceService;

import net.sf.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
class CurrentPriceTest {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CurrentPriceService currentPriceService;

    @Test
	public void testGetAllCurrentPrice() throws Exception {

		// 設定資料
		List<CurrentPrice> list = new ArrayList();
		CurrentPrice currentPrice = new CurrentPrice();
    	currentPrice.setId(1);
    	currentPrice.setCode("USD");
    	currentPrice.setCode_nameCh("美金");
    	currentPrice.setUpd_time(new Date());
    	list.add(currentPrice);

		// 模擬todoService.getTodos() 回傳 expectedList
		Mockito.when(currentPriceService.getAll()).thenReturn(list);

		// 模擬呼叫[GET] /api/todos
		String returnString = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/test").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString();

		Iterable<CurrentPrice> actualList = (Iterable<CurrentPrice>) objectMapper.readValue(returnString, CurrentPrice.class);

		// 判定回傳的body是否跟預期的一樣
		assertEquals(list, actualList);
	}
    
    @Test
    public void testrCreateOne() throws Exception {
        // 設定資料
    	CurrentPrice currentPrice = new CurrentPrice();
    	currentPrice.setId(1);
    	currentPrice.setCode("USD");
    	currentPrice.setCode_nameCh("美金");
    	currentPrice.setUpd_time(new Date());

        JSONObject object = new JSONObject();
        object.put("id", 1);
        object.put("code", "USD");

        // 模擬currentPriceService.createTodo(todo) 回傳 id 1
        Mockito.when(currentPriceService.createOne(any()));

        // 模擬呼叫[POST] /api/todos
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/api/test")
                .accept(MediaType.APPLICATION_JSON) //response 設定型別
                .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                .content(String.valueOf(object))) // body 內容
                .andReturn().getResponse().getContentAsString();

        assertEquals(1, Integer.parseInt(actual));

    }

    @Test
    public void testUpdateOne() throws Exception {
        // 模擬currentPriceService.updateTodo(1, xxx) 成功，回傳true
        Mockito.when(currentPriceService.updCurrentPrice(any(),any()));

        JSONObject todoObject = new JSONObject();
        todoObject.put("id", 1);
        todoObject.put("code", "USD");
        todoObject.put("code_nameCh","美金");


        // 模擬呼叫[PUT] /api/todos/{id}
         mockMvc.perform(MockMvcRequestBuilders.put("/api/test/1")
                .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                .content(String.valueOf(todoObject))); // body 內容
    }

    @Test
    public void testDeleteOne() throws Exception {
        // 模擬currentPriceService.deleteTodo(1) 成功回傳true
//        Mockito.when(currentPriceService.deleteOne(1));

        // 模擬呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/test/1")
                .contentType(MediaType.APPLICATION_JSON)); // request 設定型別
    }



}
