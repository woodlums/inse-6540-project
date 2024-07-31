package com.iot.blockchain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iot.blockchain.entity.TemperatureRecordEntity;

public interface TemperatureRecordRepo extends JpaRepository<TemperatureRecordEntity, Long>{
	    @Query("SELECT t FROM TemperatureRecordEntity t WHERE t.processed = false ORDER BY t.timestamp DESC")
	    List<TemperatureRecordEntity> findUnprocessedRecords();
	}


