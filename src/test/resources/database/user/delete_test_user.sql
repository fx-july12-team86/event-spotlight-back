DELETE FROM my_events WHERE id = (SELECT id FROM users WHERE email = 'testUserEmail@i.com');
DELETE FROM favorites WHERE id = (SELECT id FROM users WHERE email = 'testUserEmail@i.com');
DELETE FROM users_roles WHERE user_id = (SELECT id FROM users WHERE email = 'testUserEmail@i.com');
DELETE FROM users WHERE email = 'testUserEmail@i.com';