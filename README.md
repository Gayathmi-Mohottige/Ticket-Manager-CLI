# Ticket Manager

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Setup Instructions](#setup-instructions)
5. [Usage](#usage)

## Project Overview

This application designed to simulate the process of ticket transactions. It allows vendors to add tickets and customers to retrive tickets from a shared ticket pool. 

Key features of the system include:
- Take configurations via user inputs
- Vendors add tickets
- Customers retrieve tickets
- Transactions and Configurations are saved to JSON files.

## Features
- **Simulation Control**: Allows to start and stop using the command line interface.
- **Vendors Add Tickets**: Vendors can add tickets to the pool at a rate.
- **Customers Retieve Tickets**: Customers can purchase tickets from the pool at a rate.
- **Transaction Logging**: All transactions are saved in JSON files.

## Technologies Used
- **Java SDK 21**: The project is built using Java SDK version 21.
- **Google Gson Library**

## Setup Instructions
**Download the project**
clone the repository:
```bash
git clone https://github.com/Gayathmi-Mohottige/Ticket-Manager-CLI.git
```

## Usage
After user starts the system the system asks the user, if they want to load the configurations from ther previous session or not. If user enters yes the system will load the configurations successfully. If user chooses to start a new configuration the system will prompt user to enter the new configurations. After user enters the 4 configurations and the vendor and customer count the system asks user to enter 1 to start the simulation or 2 to quit. If user chooses 2 the system will end without satrting the simulation. If not the simulation process will start and the vendor will start to add and customers will satrt to retrieve the tickets. After the max ticket count is reached and all the transactions are done or if the user press 2 to quit the simulation will stop and the transactions and configurations will be save in JSON files.
