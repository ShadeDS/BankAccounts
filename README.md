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
|**201**|Account was created|Account|
|**500**|Internal error|No Content|


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
|**Path**|**id**  <br>*required*|Account id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Account was found in database|Account|
|**404**|Account is not present in database|No Content|


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
|**Path**|**id**  <br>*required*|Account id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|Account was deleted|No Content|


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
|**200**|Transfer was completed successfully|No Content|
|**500**|Internal error|No Content|


## Definitions

<a name="account"></a>
### Account

|Name|Schema|
|---|---|
|**firstName**  <br>*optional*|string|
|**lastName**  <br>*optional*|string|
|**id**  <br>*required*|string|
|**balance**  <br>*required*|BigDecimal|


<a name="transfer"></a>
### Transfer

|Name|Schema|
|---|---|
|**from**  <br>*required*|string|
|**to**  <br>*required*|string|
|**amount**  <br>*required*|BigDecimal|
