INSERT INTO users (id, email, password, user_name, is_deleted)
VALUES (94, 'testUserEmail@i.com', '$2a$10$2le1ol1z1uuSfRNE09RW4OykpanyzgW3wJiqTgMPx6BVfiQZ0016G', 'Test User Name', false);
INSERT INTO users_roles (user_id, role_id) VALUES (94, 2);
INSERT INTO favorites (id) VALUES (94);
INSERT INTO my_events (id) VALUES (94);