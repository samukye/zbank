### zBank

Show case for simple Bank service which allows the following operations
  - Create a new bank account for a customer, with an initial deposit amount.
  - Transfer amounts between any two accounts.
  - Retrieve balances for a given account.
  - Retrieve transfer history for a given account.

**Note:** For simplicity, this service uses double and simple math operations when dealing with transfers. A real world service will use something like Java Money and Currency API.

The service pre-populates the following customer list:

```json
[
  {
    "id": "1",
    "name": "John W"
  },
  {
    "id": "2",
    "name": "Alex S"
  },
  {
    "id": "3",
    "name": "Sam A"
  },
  {
    "id": "4",
    "name": "Adel H"
  }
]
```


The application uses spring boot and will start on port 8080. An in-memory h2 database by default to store data while the app is running. All data will be lost when restarting the app.

#### Start the app
```
mvn spring-boot:run
```

The application will start up on port 8080. A username and password is required to access the application which are specified in the configuration file. By default, username is *admin* and password is *admin*

#### Configuration
*application.properties* files contains application configuration. In addition to username and password, it's possible to enable/disable overdraft.
```
spring.security.user.name=admin
spring.security.user.password=admin
zbank.banking.overdraft.allowed=true
```

#### Overdraft
Overdraft allows an account to spend more than what is available in the current balance. When the property *zbank.banking.overdraft.allowed* is set true (default), it's possible to transfer an amount from one account to another even if the sender account does not have enough balance. In such case the balance will appear negative amount.

#### REST API

```
GET /v1/{customerId}/accounts
```
Gets the list of accounts a customer has.

```
GET /v1/accounts/{accoundId}
```
Gets information of a single account.

```
GET /v1/accounts/{accoundId}/history
```
Gets transaction history of an account.

```
POST /v1/{customerId}/accounts
```
Creates a new account for the specified customer. Example request body
```json
{"balance": 500.0}
```

```
POST /v1/transfer
```
Transfers an amount from one account to another. Example request body
```json
{
 "senderAccountId":"98a338c9-cc2a-42a7-a5ae-79dfaeefb34e", 
 "recipientAccountId":"3a03c529-7904-45b9-828e-c904cc68ac66",
 "amount": 100.0
}
```


