# Piggy Bank

This Piggy Bank project is a small Demo Project utilizing Spring Boot.

## Sub-Domains
The project is structured into the following sub-domains:

- **transfers**: takes all transfers that happened and stores them in a database. 
    It then notifies all the listeners that are interested in the transfer event.
- **accounttwin**: manages the accounts and their balances. It takes the transfer events and uses 
    them to update the balances of the accounts.

## Spring
The project uses Spring Boot to provide WebServices. The SPIs of the Domain are implemented as Spring Components and
the Domain API Services are configured as Spring Beans.

## Database
The project uses an in-memory H2 database to store the data.