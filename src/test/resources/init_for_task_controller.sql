INSERT INTO users (id,username, password, email)
VALUES (1,'user1', '$2a$10$oLwczdGVPQgQRb.eigM0deAW4fV.OYLHM3jXfls3gbb24dxIImJBm', 'user1@example.com'),
       (2,'user2', '$2a$10$oLwczdGVPQgQRb.eigM0deAW4fV.OYLHM3jXfls3gbb24dxIImJBm', 'user2@example.com'),
       (3,'user3', '$2a$10$oLwczdGVPQgQRb.eigM0deAW4fV.OYLHM3jXfls3gbb24dxIImJBm', 'user3@example.com'),
       (4,'user4', '$2a$10$oLwczdGVPQgQRb.eigM0deAW4fV.OYLHM3jXfls3gbb24dxIImJBm', 'user4@example.com'),
       (5,'user5', '$2a$10$oLwczdGVPQgQRb.eigM0deAW4fV.OYLHM3jXfls3gbb24dxIImJBm', 'user5@example.com');

INSERT INTO tasks (id,title, description, status, task_priority, author_id, executor_id)
VALUES (1,'Task 1 by User1', 'Description for Task 1', 'WAITING', 'HIGH', 1, 2),
       (2,'Task 2 by User1', 'Description for Task 2', 'IN_PROGRESS', 'MEDIUM', 1, 3),
       (3,'Task 1 by User2', 'Description for Task 3', 'COMPLETED', 'LOW', 2, 4),
       (4,'Task 1 by User3', 'Description for Task 4', 'IN_PROGRESS', 'HIGH', 3, 1),
       (5,'Task 1 by User4', 'Description for Task 5', 'IN_PROGRESS', 'MEDIUM', 4, 5);

INSERT INTO comments (id,content, author_id, task_id)
VALUES (1,'Comment 1 on Task 1', 2, 1),
       (2,'Comment 2 on Task 1', 3, 1),
       (3,'Comment on Task 2', 4, 2),
       (4,'Nice job on Task 3', 1, 3),
       (5,'Feedback on Task 4', 4, 4),
       (6,'Good luck with Task 5', 5, 5);

INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_USER'),
       (5, 'ROLE_USER');