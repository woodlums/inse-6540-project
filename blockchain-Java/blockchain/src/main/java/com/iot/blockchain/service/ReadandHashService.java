package com.iot.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.iot.blockchain.entity.TemperatureRecordEntity;
import com.iot.blockchain.repository.TemperatureRecordRepo;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public class ReadandHashService {
	@Autowired
	private TemperatureRecordRepo temperatureRecordRepo;
	@Autowired
	private EthereumNewService ethereumService;

	@Scheduled(fixedRate = 30000)
	public void processReadings() throws Exception {
		List<TemperatureRecordEntity> readings = temperatureRecordRepo.findUnprocessedRecords();

		for (TemperatureRecordEntity reading : readings) {
			String data = String.valueOf(reading.getId()) + " " + reading.getTemperature() + " " + reading.getDate()
					+ " " + reading.getTimestamp();
			System.out.println(data);
			String hash = sha256(data);
			// Call Ethereum contract with hash (not implemented here)
			System.out.println("Hash: " + hash);
			ethereumService.postHashedData(hash);
			// Mark the record as processed
			reading.setProcessed(true);
			temperatureRecordRepo.save(reading);
		}
	}

	private String sha256(String data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

		StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
		for (byte b : hashBytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return ("0x"+hexString.toString());

	}
}
