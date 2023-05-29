INSERT INTO user (email, firstname, is_deleted, last_login, lastname, password, profile_image_path, role, username)
VALUES ('email', 'name', 0, now(), 'lastname', '$2a$10$Nbp4OrkSAiEoAQDAJDx49e6DTqbkT19X9MjdKCn3HbtlJW6D69VhW', 'picture', 'ADMIN', 'admin');
INSERT INTO image (path, post_id, user_id) VALUES ('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxE8ah7QjsqhHguM2_-sf-HDzNDRfouRHW_t2tHWXfKA&s', null, 1);

INSERT INTO user (email, firstname, is_deleted, last_login, lastname, password, profile_image_path, role, username)
VALUES ('email2', 'zzzz', 0, now(), 'zzzzzz', '$2a$12$G8KcrTMVwT0KLIn04gCYnuSO8rC/o2pAHS0g6xtcSI1AwX7cSH8vy', 'picture', 'USER', 'user');

INSERT INTO groupp (creation_date, description, is_suspended, name, suspension_reason) VALUES (now(), 'group description', false, 'Group1', null);

INSERT INTO post (content, creation_date, is_deleted, user_id, group_id) VALUES ('azaza', now(), 0, 1, 1);

INSERT INTO comment (is_deleted, text, post_id, user_id, parent_comment_id) VALUES (false, 'comment text 1', 1, 1, null);
INSERT INTO comment (is_deleted, text, post_id, user_id, parent_comment_id) VALUES (false, 'comment text 2', 1, 1, null);

INSERT INTO comment (is_deleted, text, post_id, user_id, parent_comment_id) VALUES (false, 'reply text 1', null, 1, 1);
INSERT INTO comment (is_deleted, text, post_id, user_id, parent_comment_id) VALUES (false, 'reply text 2', null, 1, 1);

# INSERT INTO comments_replies (comment_parent, comment_child) VALUES (1, 2);

INSERT INTO group_admin (is_deleted, group_id, user_id) VALUES (false, 1, 1);
INSERT INTO group_request (approved, is_banned, request_date, response_date, group_id, user_id) VALUES (false, false, now(), null, 1, 1);

INSERT INTO user_friends (is_deleted, friend_id, user_id) VALUES (false, 2, 1);
