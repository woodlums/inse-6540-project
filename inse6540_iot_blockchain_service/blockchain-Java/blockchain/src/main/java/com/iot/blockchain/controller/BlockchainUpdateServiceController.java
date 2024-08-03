package com.iot.blockchain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.blockchain.service.ReadandHashService;

@RestController
@RequestMapping("/blockchainUpdate")
public class BlockchainUpdateServiceController {
    @Autowired
    private ReadandHashService readandHashService;
	@GetMapping("/getRecords")
	public String getTemperatureReading () throws Exception {
		readandHashService.processReadings();
		return ("Hello");
	}
}
