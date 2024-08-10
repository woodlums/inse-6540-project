package com.iot.blockchain.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name="readings")
/* @Data */
public class TemperatureRecordEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private LocalDateTime timestamp;
    private Double value;
    @Column(columnDefinition = "char", length=1)
    private String unit;
    @Column(columnDefinition = "char", length=2)
    private String reading_type;
    private String hashed_value;
    private Boolean processed = false;
    private String blockchain_transaction;
		
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getReading_type() {
		return reading_type;
	}
	public void setReading_type(String reading_type) {
		this.reading_type = reading_type;
	}
	public String getHashed_value() {
		return hashed_value;
	}
	public void setHashed_value(String hashed_value) {
		this.hashed_value = hashed_value;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	public String getBlockchain_transaction() {
		return this.blockchain_transaction;
	}
	public void setBlockchain_transaction(String blockchain_transaction) {
		this.blockchain_transaction = blockchain_transaction;
	}
}
