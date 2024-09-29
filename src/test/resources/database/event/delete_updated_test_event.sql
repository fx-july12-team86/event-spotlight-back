DELETE FROM events_categories WHERE event_id = (SELECT id FROM events WHERE title = 'Test Updated Event Title');
DELETE FROM events_favorites WHERE event_id = (SELECT id FROM events WHERE title = 'Test Updated Event Title');
DELETE FROM events_my_events WHERE event_id = (SELECT id FROM events WHERE title = 'Test Updated Event Title');
DELETE FROM events WHERE title = 'Test Updated Event Title';