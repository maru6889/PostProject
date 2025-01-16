LOAD DATA LOCAL INFILE '/Users/exia/Desktop/My/toy/python_practice/venv/members.csv'
    INTO TABLE member
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (nickname, login_id, password, user_role, created_at, updated_at);


LOAD DATA LOCAL INFILE '/Users/exia/Desktop/My/toy/python_practice/venv/posts.csv'
    INTO TABLE post
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (title, content, member_id, created_at, updated_at);

LOAD DATA LOCAL INFILE '/Users/exia/Desktop/My/toy/python_practice/venv/comments.csv'
    INTO TABLE comment
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
    (content, member_id, post_id, created_at, updated_at);