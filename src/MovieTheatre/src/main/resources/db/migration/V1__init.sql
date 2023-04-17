-- drop table films if exists cascade;
-- drop table sessions if exists cascade;
-- drop table tickets if exists cascade;

-- SQLite syntax:
-- 'cascade' not supported at 'drop'
-- but can be used in 'create table':
-- fieldName fieldType REFERENCES table (column) ON DELETE CASCADE
drop table if exists films;
drop table if exists sessions;
drop table if exists tickets;

--
-- 1. структура таблиц БД
--

CREATE TABLE IF NOT EXISTS films (
    --id bigserial primary key,
    id integer primary key autoincrement,
    title VARCHAR(255) not null,
    duration int not null,
    created_at datetime default current_timestamp, --timestamp is not a valid data type for SQLite
    updated_at datetime default current_timestamp --timestamp
);

CREATE TABLE IF NOT EXISTS sessions (
    --id bigserial primary key,
    id integer primary key autoincrement,
    film_id int not null references films (id) on delete cascade,
    showtime datetime not null, --timestamp
    created_at datetime default current_timestamp, --timestamp
    updated_at datetime default current_timestamp --timestamp
);

CREATE TABLE IF NOT EXISTS tickets (
    --id bigserial primary key,
    id integer primary key autoincrement,
    number int not null,
    session_id int not null references sessions (id) on delete cascade,
    price int not null,
    created_at datetime default current_timestamp, --timestamp
    updated_at datetime default current_timestamp --timestamp
);


--
-- 2. данные таблиц БД
--
insert into films (title, duration) values -- complex values surrounded with external brackets is a syntax error
	("1", 90),
	("2", 60),
	("3", 120),
	("4", 60),
	("5", 120);

insert into sessions (film_id, showtime) values
    (1, "2023-03-06 09:00"), --1
    (5, "2023-03-06 09:30"), --2
    (4, "2023-03-06 10:30"), --3
    (2, "2023-03-06 11:00"), --4
    (3, "2023-03-06 12:30"), --5
    (4, "2023-03-06 13:30"), --6
    (2, "2023-03-06 14:00"), --7
    (3, "2023-03-06 15:00"), --8
    (1, "2023-03-06 17:30"), --9
    (5, "2023-03-06 19:00"), --10
    (4, "2023-03-06 21:30"), --11
    (2, "2023-03-06 22:45"); --12

insert into tickets (number, session_id, price) values
    (100, 1, 50),
    (101, 1, 50),
    (102, 1, 50),
    (103, 1, 75),
    (104, 1, 75),
    (105, 2, 70),
    (106, 2, 70),
    (107, 2, 70),
    (108, 2, 70),
    (109, 3, 65),
    (110, 3, 65),
    (112, 3, 65),
    (113, 3, 90),
    (114, 3, 90),
    (115, 3, 90),
    (116, 4, 55),
    (117, 4, 55),
    (118, 4, 70),
    (119, 5, 80),
    (119, 5, 80),
    (120, 5, 80),
    (122, 6, 75),
    (123, 6, 75),
    (124, 6, 75),
    (125, 6, 75),
    (126, 7, 90),
    (127, 7, 90),
    (128, 7, 90),
    (129, 7, 90),
    (130, 7, 45),
    (131, 8, 85),
    (132, 8, 85),
    (132, 8, 95),
    (133, 8, 95),
    (134, 9, 65),
    (135, 9, 65),
    (136, 9, 85),
    (137, 9, 65),
    (138, 9, 85),
    (139, 9, 65),
    (140, 10, 70),
    (141, 10, 70),
    (142, 10, 90),
    (143, 10, 90),
    (144, 10, 90),
    (145, 10, 70),
    (146, 11, 95),
    (147, 11, 95),
    (148, 11, 95),
    (149, 11, 95),
    (150, 11, 95),
    (151, 11, 95),
    (152, 11, 95),
    (153, 12, 80),
    (154, 12, 80),
    (155, 12, 90),
    (156, 12, 90),
    (157, 12, 90),
    (158, 12, 80),
    (159, 12, 90),
    (160, 12, 90);
