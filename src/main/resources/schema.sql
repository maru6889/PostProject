DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS likes;
-- DROP TABLE IF EXISTS upload_image;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS member;

create table member
(
    id         bigint auto_increment primary key,
    nickname   varchar(100) not null unique,
    login_id   varchar(100) not null unique,
    password   varchar(255) not null,
    user_role  enum('ROLE_ADMIN', 'ROLE_USER') not null default 'ROLE_USER',
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

create table category
(
    id   bigint auto_increment primary key,
    name varchar(100) not null unique
);

create table post
(
    id          bigint auto_increment primary key,
    title       varchar(100) not null,
    content     text         not null,
    views       int unsigned default 0,
    member_id   bigint       not null,
    category_id bigint       not null,
    created_at  datetime default current_timestamp,
    updated_at  datetime default current_timestamp on update current_timestamp,
    constraint fk_post_member foreign key (member_id) references member (id) on delete cascade
);

create table comment
(
    id         bigint auto_increment primary key,
    content    varchar(500) not null,
    member_id  bigint       not null,
    post_id    bigint       not null,
    created_at datetime default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,
    constraint fk_comment_member foreign key (member_id) references member (id) on delete cascade,
    constraint fk_comment_post foreign key (post_id) references post (id) on delete cascade
);

create table likes
(
    id         bigint auto_increment primary key,
    created_at datetime default current_timestamp,
    member_id  bigint not null,
    post_id    bigint not null,
    constraint fk_likes_member foreign key (member_id) references member (id) on delete cascade,
    constraint fk_likes_post foreign key (post_id) references post (id) on delete cascade
);



-- create table upload_image
-- (
--     id                bigint auto_increment primary key,
--     original_filename varchar(255) not null,
--     saved_filename    varchar(255) not null,
--     filesize          int          not null,
--     post_id           bigint       not null,
--     created_at        datetime default current_timestamp,
--     updated_at        datetime default current_timestamp on update current_timestamp,
--     constraint fk_upload_image_post foreign key (post_id) references post (id) on delete cascade
-- );