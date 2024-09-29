DELETE FROM my_events;
DELETE FROM favorites;
DELETE FROM users_roles WHERE user_id != 1;
DELETE FROM users WHERE id != 1;