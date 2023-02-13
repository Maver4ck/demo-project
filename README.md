# Kameleoon Trial Task Documentation

[Accounts API](#accounts)

[Quotes API](#quotes)

[Votes API](#votes)

## Accounts

The following API allows to process all standard CRUD operations with Accounts.

### Create account

Request:
```http
POST /api/accounts/create
Content-type: application/json

{
    "login": "dstreltsov",
    "password": "example-password",
    "email": "dstreltsov@test.zz",
    "birthYear": 1999
}
```
Response:
```http
{
    "id": 11,
    "login": "dstreltsov",
    "email": "dstreltsov@test.zz",
    "birthYear": 1999,
    "createdAt": "2023-02-13T10:18:24.072+00:00"
}
```

#### Request Fields

| name          | type          | required | description       |
| ------------- | ------------- |:--------:| ----------------- |
| login         | string(2-100) | Y        | username/login    |
| password      | string        | Y        | password          |
| email         | string        | Y        | email             |
| birthYear     | number(int)   | N        | birth year (YYYY) |

#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| id            | number(int)   | unique ID in the system |
| login         | string(2-100) | username/login          |
| password      | string        | password                |
| email         | string        | email                   |
| birthYear     | number(int)   | birth year (YYYY)       |
| createdAt     | timestamp     | Exact date of creation  |

### Get account details

Request:
```http
GET /api/accounts/view-account?login={id}
```
Response:
```http
{
    "id": 11,
    "login": "dstreltsov",
    "email": "dstreltsov@test.zz",
    "birthYear": 1999,
    "createdAt": "2023-02-13T10:18:24.072+00:00"
}
```

#### Request Parameters

| name          | type          | required | description      |
| ------------- | ------------- |:--------:| ---------------- |
| login         | string(2-100) | Y        | username/login   |


#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| id            | number(int)   | unique ID in the system |
| login         | string(2-100) | username/login          |
| password      | string        | password                |
| email         | string        | email                   |
| birthYear     | number(int)   | birth year (YYYY)       |
| createdAt     | timestamp     | Exact date of creation  |

### Edit account

You are only allowed to change **birthYear** since account is created. Login + password + email are used to identify that it is exactly the correct user tries to change user's info.

Request:
```http
POST /api/accounts/edit
Content-type: application/json

{
    "login": "dstreltsov",
    "password": "example-password",
    "email": "dstreltsov@test.zz",
    "birthYear": **1930**
}
```
Response:
```http
{
    "id": 11,
    "login": "dstreltsov",
    "email": "dstreltsov@test.zz",
    "birthYear": **1930**,
    "createdAt": "2023-02-13T10:18:24.072+00:00"
}
```

#### Request Fields

| name          | type          | required | description       |
| ------------- | ------------- |:--------:| ----------------- |
| login         | string(2-100) | Y        | username/login    |
| password      | string        | Y        | password          |
| email         | string        | Y        | email             |
| birthYear     | number(int)   | Y        | birth year (YYYY) |

#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| id            | number(int)   | unique ID in the system |
| login         | string(2-100) | username/login          |
| password      | string        | password                |
| email         | string        | email                   |
| birthYear     | number(int)   | birth year (YYYY)       |
| createdAt     | timestamp     | Exact date of creation  |


### Delete account

Request:
```http
POST /api/accounts/delete
Content-type: application/json

{
    "login": "dstreltsov"
}
```
Response:
```http
{
    "message": "Successfully deleted."
}
```

#### Request Fields

| name          | type          | required | description       |
| ------------- | ------------- |:--------:| ----------------- |
| login         | string(2-100) | Y        | username/login    |

#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| message       | string        | result message          |

## Quotes

The following API allows to process all standard cread, read, update, delete operations, view worst and best quotes, view random quote and quote's vote history.

### Create quote

Request:
```http
POST /api/quotes/create
Content-type: application/json

{
    "login": "dstreltsov",
    "content": "This is test task."
}
```
Response:
```http
{
    "message": "Success."
}
```

#### Request Fields

| name          | type          | required | description       |
| ------------- | ------------- |:--------:| ----------------- |
| login         | string(2-100) | Y        | username/login    |
| content       | string(1-300) | Y        | quote's content   |

#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| message       | string        | result message          |

### Get quote details

Request:
```http
GET /api/quotes/view/id/{id}
```
Response:
```http
{
    "id": 22,
    "content": "This is test task.",
    "votes": 0,
    "updatedAt": "2023-02-13T10:46:53.178+00:00",
    "createdAt": "2023-02-13T10:46:53.178+00:00",
    "owner": {
        "id": 12,
        "login": "dstreltsov",
        "email": "dstreltsov@test.zz",
        "birthYear": 1999,
        "createdAt": "2023-02-13T10:42:10.849+00:00"
    }
}
```

#### Request Parameters

| name          | type          | required | description      |
| ------------- | ------------- |:--------:| ---------------- |
| login         | string(2-100) | Y        | username/login   |


#### Response Fields

| name                | type          | description                   |
| ------------------- | ------------- | ----------------------------- |
| id                  | number(int)   | unique quote ID in the system |
| content             | string(1-300) | quote's content               |
| votes               | number(int)   | number of votes for the quote |
| updatedAt           | timestamp     | time when quote last updated  |
| createdAt           | timestamp     | time when quote created       |
| id                  | number(int)   | unique quote ID in the system |
| owner               | object        | owner object                  |
| owner.id            | number(int)   | unique user ID in the system  |
| owner.login         | string(2-100) | username/login                |
| owner.email         | string        | email                         |
| owner.birthYear     | number(int)   | birth year (YYYY)             |
| owner.createdAt     | timestamp     | Exact date of creation        |

### Edit quote

Request:
```http
POST /api/quotes/edit/id/{id}
Content-type: application/json

{
    "login": "dstreltsov",
    "content": "This is some other content."
}
```
Response:
```http
{
    "message": "Success."
}
```

#### Request Parameters

| name          | type          | required | description      |
| ------------- | ------------- |:--------:| ---------------- |
| id            | number(int)   | Y        | unique quote ID  |

#### Request Fields

| name          | type          | required | description       |
| ------------- | ------------- |:--------:| ----------------- |
| login         | string(2-100) | Y        | username/login    |
| content       | string(1-300) | Y        | quote's content   |

#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| message       | string        | result message          |


### Delete quote

Request:
```http
POST /api/quotes/delete/id/{id}
Content-type: application/json

{
    "login": "dstreltsov"
}
```
Response:
```http
{
    "message": "Successfully deleted."
}
```

#### Request Parameters

| name          | type          | required | description      |
| ------------- | ------------- |:--------:| ---------------- |
| id            | number(int)   | Y        | unique quote ID  |

#### Request Fields

| name          | type          | required | description       |
| ------------- | ------------- |:--------:| ----------------- |
| login         | string(2-100) | Y        | username/login    |

#### Response Fields

| name          | type          | description             |
| ------------- | ------------- | ----------------------- |
| message       | string        | result message          |

### Get random quote details

Request:
```http
GET /api/quotes/view-random
```
Response:
```http
{
    "id": 22,
    "content": "This is some other content.",
    "votes": 0,
    "updatedAt": "2023-02-13T10:54:21.902+00:00",
    "createdAt": "2023-02-13T10:46:53.178+00:00",
    "owner": {
        "id": 12,
        "login": "dstreltsov",
        "email": "dstreltsov@test.zz",
        "birthYear": 1999,
        "createdAt": "2023-02-13T10:42:10.849+00:00"
    }
}
```

#### Response Fields

| name                | type          | description                   |
| ------------------- | ------------- | ----------------------------- |
| id                  | number(int)   | unique quote ID in the system |
| content             | string(1-300) | quote's content               |
| votes               | number(int)   | number of votes for the quote |
| updatedAt           | timestamp     | time when quote last updated  |
| createdAt           | timestamp     | time when quote created       |
| id                  | number(int)   | unique quote ID in the system |
| owner               | object        | owner object                  |
| owner.id            | number(int)   | unique user ID in the system  |
| owner.login         | string(2-100) | username/login                |
| owner.email         | string        | email                         |
| owner.birthYear     | number(int)   | birth year (YYYY)             |
| owner.createdAt     | timestamp     | Exact date of creation        |

### Get 10 best/worst quote details

Request:
```http
GET /api/quotes/view/{option} //best or worst
```

Response:
```http
{
    "id": 22,
    "content": "This is some other content.",
    "votes": 0,
    "updatedAt": "2023-02-13T10:54:21.902+00:00",
    "createdAt": "2023-02-13T10:46:53.178+00:00",
    "owner": {
        "id": 12,
        "login": "dstreltsov",
        "email": "dstreltsov@test.zz",
        "birthYear": 1999,
        "createdAt": "2023-02-13T10:42:10.849+00:00"
    }
}
```

As a result you will receive list of Quotes matching your requirements.

#### Request Parameters

| name          | type          | required | possible values  |
| ------------- | ------------- |:--------:| ---------------- |
| option        | string        | Y        | best, worst      |

### Get quote's vote history

Request:
```http
GET /api/quotes/view/history/id/{id}
```
As a response you will receive an image in base64 format.
Response:
```http
{
    "message": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAoAAAAHgCAYAAAA10dzkAABAD0lEQVR42u3df2id53338cEYY4wxBmOM/THGYIwxxhiDMcYYxljYcwnKHCzHo0nTpjaeuyQWWess3pZNXkK0apGjEWrN4JbajzHO7EQ1xWmsxm2dda4tnOZJFzWz3fxqjVXpsZP0kRY1nq5H1/3kCOn4nKNzpPPj/vH6wgf56BzJ532+133rc32v73XfPxGEEEIIIUSh4id8BEIIIYQQDKAQQgghhGAAhRBCCCEEAyiEEEIIIRhAIYRI/UnvJ35iiYQQggEUQhQifu/3fm+JCTp8+PAtr/nCF76w5DW/+7u/W1gDyDQKIRhAIUTm4x//8R+XGJrNmzff8pru7u4lr4k/007TlTcDyEQKIRhAIURH49KlS0vMyE/91E+FDz74YOH5+O/4vcWviT9TVAOY5f9XCCEYQCHEQvz+7//+EkPyzDPPLDw3MjKy5Lm4ZJwX88MACiEEAyhEYeOzn/3sEkNyzz33LDwX/734ucc..."
}
```
After decoding it will look as in example:

![image](https://user-images.githubusercontent.com/40277106/218442501-43bee6e1-28e4-41d3-a4f2-9e5b11eb3625.png)

#### Request Parameters

| name          | type          | required | description      |
| ------------- | ------------- |:--------:| ---------------- |
| id            | number(int)   | Y        | unique quote ID  |

## Votes

The following API allows to either upvote or downvote a quote.

Request:
```http
POST /api/vote/up
Content-type: application/json

{
    "quoteId": "22"
}
```

Request:
```http
POST /api/vote/down
Content-type: application/json

{
    "quoteId": "22"
}
```

Response:
```http
{
    "message": "Vote counted."
}
```

#### Request Fields

| name          | type          | required | description      |
| ------------- | ------------- |:--------:| ---------------- |
| quoteId       | number(int)   | Y        | unique quote ID  |
