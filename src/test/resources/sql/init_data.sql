INSERT INTO users (username, password, email)
VALUES ('user1', 'password1', 'user1@example.com'),
       ('user2', 'password2', 'user2@example.com'),
       ('user3', 'password3', 'user3@example.com'),
       ('user4', 'password4', 'user4@example.com'),
       ('user5', 'password5', 'user5@example.com');

INSERT INTO tasks (title, description, status, task_priority, author_id, executor_id)
VALUES ('Task 1 by User1', 'Description for Task 1', 'OPEN', 'HIGH', 1, 2),
       ('Task 2 by User1', 'Description for Task 2', 'IN_PROGRESS', 'MEDIUM', 1, 3),
       ('Task 1 by User2', 'Description for Task 3', 'COMPLETED', 'LOW', 2, NULL),
       ('Task 1 by User3', 'Description for Task 4', 'OPEN', 'HIGH', 3, 1),
       ('Task 1 by User4', 'Description for Task 5', 'OPEN', 'MEDIUM', 4, 5);


INSERT INTO comments (content, author_id, task_id)
VALUES ('Comment 1 on Task 1', 2, 1),
       ('Comment 2 on Task 1', 3, 1),
       ('Comment on Task 2', 4, 2),
       ('Nice job on Task 3', 1, 3),
       ('Feedback on Task 4', 4, 4),
       ('Good luck with Task 5', 5, 5);


INSERT INTO user_roles (user_id, role)
VALUES (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4, 'ADMIN'),
       (5, 'MODERATOR');