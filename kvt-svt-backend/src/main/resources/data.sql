INSERT INTO user (email, first_name, is_deleted, last_login, last_name, password, profile_image_path, role, username)
VALUES ('email', 'name', 0, now(), 'lastname', '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'picture', 'ADMIN', 'admin');

INSERT INTO groupp VALUES (DEFAULT, null, 'opis', null, 'naziv', null);

INSERT INTO post (content, creation_date, is_deleted, user_id) VALUES ('azaza', now(), 0, null);

INSERT INTO comment VALUES (DEFAULT, 0, 'text', 1);
