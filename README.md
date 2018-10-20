# BankAccounts

## Overview
REST API of Bank Accounts project


### Server settings
*Host* : localhost  
*Port* : 4567 


## Paths


### Create new account
```
POST /api/account
```


#### Description
Creates new bank account in database


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**account**  <br>*required*|account|[Account](#account)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**201**|Account was created|[Account](#account)|
|**400**|Account with the same id already exists in database|string|


#### Produces
* `application/json`


### Get account
```
GET /api/account/{id}
```


#### Description
Returns account with specified id from database


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|Account id|uuid|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Account was found in database|[Account](#account)|
|**404**|Account is not present in database|string|


#### Produces
* `application/json`


### Delete account
```
DELETE /api/account/{id}
```


#### Description
Removes account with specified id from database


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Path**|**id**  <br>*required*|Account id|uuid|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Account was deleted|string|


### Transfer money between accounts
```
POST /api/transfer
```


#### Description
Transfers money from one account to another


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Body**|**transfer**  <br>*required*|transfer information|[Transfer](#transfer)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Transfer was completed successfully|string|
|**400**|Transfer failed because sender has insufficient funds|string|
|**500**|Internal error|string|


## Definitions

<a name="account"></a>
### Account

|Name|Schema|
|---|---|
|**firstName**  <br>*optional*|string|
|**lastName**  <br>*optional*|string|
|**id**  <br>*optional*|uuid|
|**balance**  <br>*required*|BigDecimal|


<a name="transfer"></a>
### Transfer

|Name|Schema|
|---|---|
|**from**  <br>*required*|uuid|
|**to**  <br>*required*|uuid|
|**amount**  <br>*required*|BigDecimal|
