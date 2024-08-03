// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract IHashStorage {
    // Event to emit when a new hash is stored
    event HashStored(string indexed hashValue);

    // Address of the contract owner
    address public owner;

    // Array to store hashes
    string[] public hashes;

    // Boolean to check if contract is paused
    bool public isPaused;

    // Modifier to restrict access to the owner
    modifier onlyOwner() {
        require(msg.sender == owner, "Only the owner can call this function");
        _;
    }

    // Modifier to check if contract is not paused
    modifier whenNotPaused() {
        require(!isPaused, "Contract is paused");
        _;
    }

    // Constructor to set the contract owner
    constructor() {
        owner = msg.sender;
        isPaused = false;
    }

    // Function to store a new hash
    function storeHash(string memory hashValue) public whenNotPaused {
        hashes.push(hashValue);
        emit HashStored(hashValue);
    }

    // Function to get the stored hashes
    function getHashes() public view returns (string[] memory) {
        return hashes;
    }

    // Fallback function to receive ether
    receive() external payable {}

    // Function to pause the contract
    function pauseContract() public onlyOwner {
        isPaused = true;
    }

    // Function to resume the contract
    function resumeContract() public onlyOwner {
        isPaused = false;
    }

    // Function to withdraw all funds from the contract
    function withdraw() public onlyOwner {
        payable(owner).transfer(address(this).balance);
    }
}
