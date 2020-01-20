package com.umesh.game;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StartGame implements ApplicationListener<ApplicationReadyEvent>{

	@Autowired
	RestTemplate restTemplate;
	int counter = 0;
	
	public void getChallenge() throws JSONException {
		String response = restTemplate.getForObject(Constant.CHALLENGE_URL, String.class);
		System.out.println("======in challenge====" + response);
		getInput();
	}


	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		try {
			getChallenge();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getInput() throws JSONException {
		String response = restTemplate.getForObject(Constant.INPUT_URL, String.class);
		JSONObject jsonObject = new JSONObject(response);
		String input = jsonObject.getString("text");
		System.out.println("======in getInput====" + response);
		postOutput(input.toCharArray().length);
	}
	
	public void postOutput(long output) throws JSONException {
		System.out.println("======in postOutput====" + output);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("count", output);
		String response = restTemplate.postForObject(Constant.OUTPUT_URL, jsonObject.toString(), String.class);
		counter++;
		getChallenge();
	}
}
