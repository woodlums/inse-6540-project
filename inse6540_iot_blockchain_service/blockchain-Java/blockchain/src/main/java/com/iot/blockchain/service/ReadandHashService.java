package com.iot.blockchain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.iot.blockchain.entity.TemperatureRecordEntity;
import com.iot.blockchain.repository.TemperatureRecordRepo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
			// String data = String.valueOf(reading.getId()) + " " + reading.getTemperature() + " " + reading.getDate() + " " + reading.getTimestamp();
			String hashData = String.valueOf(reading.getHashed_value());
			LocalDateTime timeiso = reading.getTimestamp();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDate = dtf.format(timeiso);
			String data = formattedDate + reading.getReading_type() + reading.getValue() + reading.getUnit();
			String dataHex = convertToHex(data);
			String hashAndSalt = reading.getHashed_value();
			String salt = hashAndSalt.substring(hashAndSalt.lastIndexOf(":") + 1);
			String hash = sha256(salt+dataHex);
			
			
			System.out.println("INFO: Received a new record!");
			System.out.printf("INFO: Timestamp: %s, Type: %s, Value: %2.2f, Unit: %s \n", formattedDate, reading.getReading_type(), reading.getValue(), reading.getUnit());
			System.out.printf("INFO: Based on salt %s new hash is %s \n", salt, hash);
			
			// Call Ethereum contract with hash
			CompletableFuture<String> h = ethereumService.postHashedData(hash);
			
			
			// Mark the record as processed and include the new transaction id.
			reading.setProcessed(true);
			reading.setBlockchain_transaction(h.get());
			temperatureRecordRepo.save(reading);
			System.out.println("INFO: The record was saved to the Blockchain and marked as processed.");
		}
	}
	
	private String convertToHex(String input)
	{
		StringBuffer sb = new StringBuffer();
		char ch[] = input.toCharArray();
	      for(int i = 0; i < ch.length; i++) {
	         String hexString = Integer.toHexString(ch[i]);
	         sb.append(hexString);
	      }
	      return sb.toString();
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
		//return ("0x"+hexString.toString());
		return (hexString.toString());

	}
}
