package com.bleuthings;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

	private final Random random = new Random();

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/swagger-ui.html";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<String> get(
			@RequestParam(value = "target", required = false) String requestParamValue,
			@RequestHeader(value = "target", required = false) String requestHeaderValue) {
		String target = requestParamValue != null
				? requestParamValue
				: requestHeaderValue;
		if (target == null) {
			return new ResponseEntity<String>("\"target\" parameter required. ",
					HttpStatus.BAD_REQUEST);
		}
		if (random.nextBoolean()) {
			final RestTemplate restTemplate = new RestTemplate();
			return restTemplate.getForEntity(target, String.class);
		} else {
			return new ResponseEntity<String>("",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
