CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE tasks
(
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    description   TEXT,
    status        VARCHAR(255),
    task_priority VARCHAR(255),
    author_id     BIGINT       NOT NULL,
    executor_id   BIGINT,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (executor_id) REFERENCES users (id)
);

CREATE TABLE comments
(
    id        BIGSERIAL PRIMARY KEY,
    content   TEXT,
    author_id BIGINT NOT NULL,
    task_id   BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE user_roles
(
    user_id BIGINT       NOT NULL,
    role    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    PRIMARY KEY (user_id, role)
);