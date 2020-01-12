CREATE TABLE users (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(20),
    join_date timestamp
);

CREATE TABLE board (
    id INTEGER auto_increment PRIMARY KEY,
    user_id VARCHAR(20),
    title VARCHAR(255),
    content CLOB,
    read_count INTEGER,
    write_date timestamp,
    update_date timestamp
);

CREATE TABLE board_files (
    id INTEGER auto_increment PRIMARY KEY,
    board_id INTEGER,
    file_name VARCHAR(255),
    save_file_name VARCHAR(255),
    mime_type VARCHAR(255),
    file_length INTEGER
)