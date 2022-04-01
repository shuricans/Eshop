INSERT INTO users (login, password)
VALUES ('customer', '$2a$10$IT/yOmMG7qelKXIzslCGMOmFEmqZLLHUXBN4r4IyMXfbbx9Yrz7H6');

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT user_id FROM users WHERE login = 'customer'), (SELECT role_id FROM roles WHERE name = 'ROLE_GUEST');

INSERT INTO customer (user_id) SELECT (SELECT user_id FROM users WHERE login = 'customer');