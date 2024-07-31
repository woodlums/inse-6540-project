package com.iot.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class IHashStorage extends Contract {
    public static final String BINARY = "6080604052348015600e575f80fd5b50335f806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f60025f6101000a81548160ff021916908315150217905550610eca806100745f395ff3fe60806040526004361061007e575f3560e01c806371dc61cb1161004d57806371dc61cb1461011b5780638da5cb5b14610143578063b187bd261461016d578063c4bc5da51461019757610085565b80633ccfd60b146100895780634245d48e1461009f578063439766ce146100c9578063501895ae146100df57610085565b3661008557005b5f80fd5b348015610094575f80fd5b5061009d6101ad565b005b3480156100aa575f80fd5b506100b361029f565b6040516100c09190610790565b60405180910390f35b3480156100d4575f80fd5b506100dd610373565b005b3480156100ea575f80fd5b50610105600480360381019061010091906107f4565b61041c565b6040516101129190610867565b60405180910390f35b348015610126575f80fd5b50610141600480360381019061013c91906109b3565b6104c2565b005b34801561014e575f80fd5b50610157610588565b6040516101649190610a39565b60405180910390f35b348015610178575f80fd5b506101816105ab565b60405161018e9190610a6c565b60405180910390f35b3480156101a2575f80fd5b506101ab6105bd565b005b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461023a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161023190610af5565b60405180910390fd5b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc4790811502906040515f60405180830381858888f1935050505015801561029c573d5f803e3d5ffd5b50565b60606001805480602002602001604051908101604052809291908181526020015f905b8282101561036a578382905f5260205f200180546102df90610b40565b80601f016020809104026020016040519081016040528092919081815260200182805461030b90610b40565b80156103565780601f1061032d57610100808354040283529160200191610356565b820191905f5260205f20905b81548152906001019060200180831161033957829003601f168201915b5050505050815260200190600101906102c2565b50505050905090565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610400576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103f790610af5565b60405180910390fd5b600160025f6101000a81548160ff021916908315150217905550565b6001818154811061042b575f80fd5b905f5260205f20015f91509050805461044390610b40565b80601f016020809104026020016040519081016040528092919081815260200182805461046f90610b40565b80156104ba5780601f10610491576101008083540402835291602001916104ba565b820191905f5260205f20905b81548152906001019060200180831161049d57829003601f168201915b505050505081565b60025f9054906101000a900460ff1615610511576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161050890610bba565b60405180910390fd5b600181908060018154018082558091505060019003905f5260205f20015f9091909190915090816105429190610d75565b50806040516105519190610e7e565b60405180910390207fc11dc30fab9e1049e39849c934756251850c71b749c0dff181b2a28074fca60e60405160405180910390a250565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60025f9054906101000a900460ff1681565b5f8054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461064a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161064190610af5565b60405180910390fd5b5f60025f6101000a81548160ff021916908315150217905550565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f6106d08261068e565b6106da8185610698565b93506106ea8185602086016106a8565b6106f3816106b6565b840191505092915050565b5f61070983836106c6565b905092915050565b5f602082019050919050565b5f61072782610665565b610731818561066f565b9350836020820285016107438561067f565b805f5b8581101561077e578484038952815161075f85826106fe565b945061076a83610711565b925060208a01995050600181019050610746565b50829750879550505050505092915050565b5f6020820190508181035f8301526107a8818461071d565b905092915050565b5f604051905090565b5f80fd5b5f80fd5b5f819050919050565b6107d3816107c1565b81146107dd575f80fd5b50565b5f813590506107ee816107ca565b92915050565b5f60208284031215610809576108086107b9565b5b5f610816848285016107e0565b91505092915050565b5f82825260208201905092915050565b5f6108398261068e565b610843818561081f565b93506108538185602086016106a8565b61085c816106b6565b840191505092915050565b5f6020820190508181035f83015261087f818461082f565b905092915050565b5f80fd5b5f80fd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b6108c5826106b6565b810181811067ffffffffffffffff821117156108e4576108e361088f565b5b80604052505050565b5f6108f66107b0565b905061090282826108bc565b919050565b5f67ffffffffffffffff8211156109215761092061088f565b5b61092a826106b6565b9050602081019050919050565b828183375f83830152505050565b5f61095761095284610907565b6108ed565b9050828152602081018484840111156109735761097261088b565b5b61097e848285610937565b509392505050565b5f82601f83011261099a57610999610887565b5b81356109aa848260208601610945565b91505092915050565b5f602082840312156109c8576109c76107b9565b5b5f82013567ffffffffffffffff8111156109e5576109e46107bd565b5b6109f184828501610986565b91505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610a23826109fa565b9050919050565b610a3381610a19565b82525050565b5f602082019050610a4c5f830184610a2a565b92915050565b5f8115159050919050565b610a6681610a52565b82525050565b5f602082019050610a7f5f830184610a5d565b92915050565b7f4f6e6c7920746865206f776e65722063616e2063616c6c20746869732066756e5f8201527f6374696f6e000000000000000000000000000000000000000000000000000000602082015250565b5f610adf60258361081f565b9150610aea82610a85565b604082019050919050565b5f6020820190508181035f830152610b0c81610ad3565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680610b5757607f821691505b602082108103610b6a57610b69610b13565b5b50919050565b7f436f6e74726163742069732070617573656400000000000000000000000000005f82015250565b5f610ba460128361081f565b9150610baf82610b70565b602082019050919050565b5f6020820190508181035f830152610bd181610b98565b9050919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f60088302610c347fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610bf9565b610c3e8683610bf9565b95508019841693508086168417925050509392505050565b5f819050919050565b5f610c79610c74610c6f846107c1565b610c56565b6107c1565b9050919050565b5f819050919050565b610c9283610c5f565b610ca6610c9e82610c80565b848454610c05565b825550505050565b5f90565b610cba610cae565b610cc5818484610c89565b505050565b5b81811015610ce857610cdd5f82610cb2565b600181019050610ccb565b5050565b601f821115610d2d57610cfe81610bd8565b610d0784610bea565b81016020851015610d16578190505b610d2a610d2285610bea565b830182610cca565b50505b505050565b5f82821c905092915050565b5f610d4d5f1984600802610d32565b1980831691505092915050565b5f610d658383610d3e565b9150826002028217905092915050565b610d7e8261068e565b67ffffffffffffffff811115610d9757610d9661088f565b5b610da18254610b40565b610dac828285610cec565b5f60209050601f831160018114610ddd575f8415610dcb578287015190505b610dd58582610d5a565b865550610e3c565b601f198416610deb86610bd8565b5f5b82811015610e1257848901518255600182019150602085019450602081019050610ded565b86831015610e2f5784890151610e2b601f891682610d3e565b8355505b6001600288020188555050505b505050505050565b5f81905092915050565b5f610e588261068e565b610e628185610e44565b9350610e728185602086016106a8565b80840191505092915050565b5f610e898284610e4e565b91508190509291505056fea264697066735822122078f8eb0e65228a151bbf35128c8e2a66bb53b6ea92fd836ffe88d97329f6f98e64736f6c63430008190033";

    private static String librariesLinkedBinary;

    public static final String FUNC_PAUSECONTRACT = "pauseContract";

    public static final String FUNC_RESUMECONTRACT = "resumeContract";

    public static final String FUNC_STOREHASH = "storeHash";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_GETHASHES = "getHashes";

    public static final String FUNC_HASHES = "hashes";

    public static final String FUNC_ISPAUSED = "isPaused";

    public static final String FUNC_OWNER = "owner";

    public static final Event HASHSTORED_EVENT = new Event("HashStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}));
    ;

    @Deprecated
    protected IHashStorage(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IHashStorage(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IHashStorage(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IHashStorage(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<HashStoredEventResponse> getHashStoredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(HASHSTORED_EVENT, transactionReceipt);
        ArrayList<HashStoredEventResponse> responses = new ArrayList<HashStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HashStoredEventResponse typedResponse = new HashStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hashValue = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static HashStoredEventResponse getHashStoredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(HASHSTORED_EVENT, log);
        HashStoredEventResponse typedResponse = new HashStoredEventResponse();
        typedResponse.log = log;
        typedResponse.hashValue = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<HashStoredEventResponse> hashStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getHashStoredEventFromLog(log));
    }

    public Flowable<HashStoredEventResponse> hashStoredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HASHSTORED_EVENT));
        return hashStoredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> pauseContract() {
        final Function function = new Function(
                FUNC_PAUSECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> resumeContract() {
        final Function function = new Function(
                FUNC_RESUMECONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> storeHash(String hashValue) {
        final Function function = new Function(
                FUNC_STOREHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(hashValue)), 
                Collections.<TypeReference<?>>emptyList());
        System.out.println("inside storeHash");
        System.out.println(function);
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getHashes() {
        final Function function = new Function(FUNC_GETHASHES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Utf8String>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> hashes(BigInteger param0) {
        final Function function = new Function(FUNC_HASHES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isPaused() {
        final Function function = new Function(FUNC_ISPAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static IHashStorage load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new IHashStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IHashStorage load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IHashStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IHashStorage load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new IHashStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IHashStorage load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IHashStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IHashStorage> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IHashStorage.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<IHashStorage> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IHashStorage.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IHashStorage> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IHashStorage.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IHashStorage> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IHashStorage.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class HashStoredEventResponse extends BaseEventResponse {
        public byte[] hashValue;
    }
}
