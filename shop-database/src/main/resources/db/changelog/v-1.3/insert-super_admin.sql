INSERT INTO users (login, password)
VALUES ('super_admin', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq');

INSERT INTO users_roles (user_id, role_id)
SELECT (SELECT user_id FROM users WHERE login = 'super_admin'),
       (SELECT role_id FROM roles WHERE name = 'ROLE_SUPER_ADMIN');