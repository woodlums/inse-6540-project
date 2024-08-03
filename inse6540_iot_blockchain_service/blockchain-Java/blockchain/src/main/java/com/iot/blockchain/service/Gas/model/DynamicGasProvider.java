package com.iot.blockchain.service.Gas.model;

import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class DynamicGasProvider implements ContractGasProvider {

    private final Web3j web3j;

    public DynamicGasProvider(Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        try {
            // Fetch the current gas price from the network
            EthGasPrice gasPrice = web3j.ethGasPrice().send();
            return gasPrice.getGasPrice();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch gas price", e);
        }
    }

    @Override
    public BigInteger getGasPrice() {
        return getGasPrice(null);
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return DefaultGasProvider.GAS_LIMIT;
    }

    @Override
    public BigInteger getGasLimit() {
        return DefaultGasProvider.GAS_LIMIT;
    }
}