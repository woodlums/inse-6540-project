/*
 * package com.iot.blockchain.service;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service; import org.web3j.protocol.Web3j;
 * import org.web3j.protocol.core.methods.response.TransactionReceipt; import
 * org.web3j.protocol.http.HttpService; import
 * org.web3j.tx.gas.DefaultGasProvider;
 * 
 * import com.iot.blockchain.IHashStorage;
 * 
 * import org.web3j.tx.Contract;
 * 
 * import java.io.IOException; import java.math.BigInteger; import
 * java.util.concurrent.CompletableFuture;
 * 
 * @Service public class EthereumNewService {
 * 
 * // @Value("${ethereum.contract.address}") private String contractAddress =
 * "<Contract_address>";
 * 
 * // @Value("${ethereum.private.key}") private String privateKey =
 * "<your_private_key>"; private
 * IHashStorage contract; private Web3j web3j;
 * 
 * public EthereumNewService() { // Initialize Web3j with your Ethereum client
 * URL (e.g., Infura) this.web3j = Web3j.build(new
 * HttpService("https://sepolia.infura.io/v3/<Your_API_KEY>"))
 * ; this.contract = IHashStorage.load( contractAddress, web3j, new
 * ClientTransactionManager(web3j, privateKey), new DefaultGasProvider() ); }
 * 
 * public CompletableFuture<TransactionReceipt> postHashedData(String hash) {
 * try { // Load the contract HashStorage contract =
 * HashStorage.load(contractAddress, web3j, new
 * org.web3j.tx.ClientTransactionManager(web3j,privateKey), new
 * DefaultGasProvider() );
 * 
 * // Call the contract method to post data return
 * contract.storeHash(hash).sendAsync(); // return null; } catch (Exception e) {
 * throw new RuntimeException("Error interacting with Ethereum contract", e); }
 * }
 * 
 * }
 */
package com.iot.blockchain.service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.tx.RawTransactionManager;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import com.iot.blockchain.IHashStorage;

import org.web3j.tx.Contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class EthereumNewService {
	 private static final Logger log = LoggerFactory.getLogger(EthereumNewService.class);

    // @Value("${ethereum.contract.address}")
    private String contractAddress = "0x39408Eee90CF9625Ff673C4D215ED735167CD7D8";

    // @Value("${ethereum.private.key}")
    private String privateKey = "119ab6125d3d71e0424c18c6c668fdbe40869a5c71290747ab213a8287d7602a";

    private Web3j web3j;
    private Credentials credentials;


    public EthereumNewService() {
        // Initialize Web3j with your Ethereum client URL (e.g., Infura)
        this.web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/f9025580f11c45e29e59beb8464a059c"));
        this.credentials = Credentials.create(privateKey);
        log.info("Web3j initialized with URL: {}", "https://sepolia.infura.io/v3/f9025580f11c45e29e59beb8464a059c");
    }

    public CompletableFuture<String> postHashedData(String hashedData) {
        try {
        	log.info("Loading contract at address: {}", contractAddress);
            // Load the contract
            IHashStorage contract = IHashStorage.load(
                    contractAddress,
                    web3j,
                    new RawTransactionManager(web3j, credentials),
                    new DefaultGasProvider()
            );
            log.info("Sending hashed data to contract: {}", hashedData);
            // Call the contract method to post data
            return contract.storeHash(hashedData).sendAsync().thenApply(transactionReceipt -> {
             log.info("Transaction successful with hash (transaction Id): {}", transactionReceipt.getTransactionHash());
                        return transactionReceipt.getTransactionHash();
                    })
                    .exceptionally(ex -> {
                        log.error("Error sending transaction", ex);
                        throw new RuntimeException("Error sending transaction", ex);
                    });
        } catch (Exception e) {
        	log.error("Error interacting with Ethereum contract", e);
            throw new RuntimeException("Error interacting with Ethereum contract", e);
        }
    }
}

























