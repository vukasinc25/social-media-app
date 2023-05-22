INSERT INTO user (email, first_name, is_deleted, last_login, last_name, password, profile_image_path, role, username)
VALUES ('email', 'name', 0, now(), 'lastname', '$2a$10$WyGU0850Gt6l9niernBpb.58pCPz8XXEaI4qvOyj5rdEYIygCat/u', 'picture', 'ADMIN', 'admin');

INSERT INTO groupp (creation_date, description, is_suspended, name, suspension_reason) VALUES (now(), 'group description', false, 'Group1', null);

INSERT INTO post (content, creation_date, is_deleted, user_id, group_id) VALUES ('azaza', now(), 0, 1, 1);

INSERT INTO comment (is_deleted, text, post_id, user_id) VALUES (false, 'comment text', 1, 1);
