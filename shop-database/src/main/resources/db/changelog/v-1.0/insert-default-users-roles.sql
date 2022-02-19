INSERT INTO users (login, password)
VALUES ('admin', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq'),
       ('guest', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq');

INSERT INTO roles (name)
VALUES ('ROLE_GUEST'), ('ROLE_MANAGER'), ('ROLE_ADMIN'), ('ROLE_SUPER_ADMIN');

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT user_id FROM users WHERE login = 'admin'), (SELECT role_id FROM roles WHERE name = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT user_id FROM users WHERE login = 'guest'), (SELECT role_id FROM roles WHERE name = 'ROLE_GUEST');