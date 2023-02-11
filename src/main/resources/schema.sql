CREATE TABLE account(
                        id int PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
                        login varchar UNIQUE NOT NULL CHECK(length(login) >= 2 AND length(login) <= 100),
                        user_password varchar NOT NULL,
                        email varchar NOT NULL UNIQUE,
                        birth_year int CHECK(birth_year >= 1900),
                        created_at TIMESTAMP
);

CREATE TABLE quote(
                        id int PRIMARY KEY AUTO_INCREMENT UNIQUE NOT NULL,
                        content varchar NOT NULL CHECK(length(content) >= 1 AND length(content) <= 300),
                        votes int,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP,
                        owner varchar REFERENCES account(login)
);