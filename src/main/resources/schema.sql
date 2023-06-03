--Удаление таблиц

drop table IF EXISTS friendships;
drop table IF EXISTS likes;
drop table IF EXISTS film_genre;
drop table IF EXISTS genre;
drop table IF EXISTS users;
drop table IF EXISTS films;
drop table IF EXISTS mpa;


--Создание USER
create TABLE IF NOT EXISTS users (
	id INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING(50) NOT NULL,
	login CHARACTER VARYING(50) NOT NULL,
	email CHARACTER VARYING(50) NOT NULL,
	birthday DATE NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);

--Создание MPA
create TABLE IF NOT EXISTS mpa (
	mpa_id INTEGER NOT NULL AUTO_INCREMENT,
	name CHARACTER VARYING(50) NOT NULL,
	description CHARACTER VARYING(200),
	CONSTRAINT mpa_pk PRIMARY KEY (mpa_id)
);

--Создание FILM
create TABLE IF NOT EXISTS films (
	id INTEGER NOT NULL AUTO_INCREMENT,
	name CHARACTER VARYING(50) NOT NULL,
	description CHARACTER VARYING(200) NOT NULL,
	release_date DATE NOT NULL,
	duration INTEGER NOT NULL,
	mpa_id INTEGER,
	CONSTRAINT film_pk PRIMARY KEY (id),
	CONSTRAINT film_fk FOREIGN KEY (mpa_id) REFERENCES mpa(mpa_id) ON UPDATE NO ACTION
);

--Создание FRIENDSHIP
create TABLE IF NOT EXISTS friendships (
	user_id INTEGER NOT NULL,
	friend_id INTEGER NOT NULL,
	status_id INTEGER,
	CONSTRAINT friendships_pk PRIMARY KEY (friend_id,user_id),
	CONSTRAINT friendships_fk FOREIGN KEY (user_id) REFERENCES users(id) ON delete CASCADE ON update CASCADE,
	CONSTRAINT friendship_fk_1 FOREIGN KEY (friend_id) REFERENCES users(id) ON delete CASCADE ON update CASCADE
);

--Создание GENRE
create TABLE IF NOT EXISTS genre (
	genre_id INTEGER NOT NULL AUTO_INCREMENT,
	NAME CHARACTER VARYING(50) NOT NULL,
	CONSTRAINT genre_pk PRIMARY KEY (genre_id)
);

--Создание FILM_GENRE
create TABLE IF NOT EXISTS film_genre (
	film_id INTEGER NOT NULL,
	genre_id INTEGER NOT NULL,
	CONSTRAINT film_genre_fk FOREIGN KEY (film_id) REFERENCES films(id) ON delete RESTRICT ON update RESTRICT,
	CONSTRAINT film_genre_fk_1 FOREIGN KEY (genre_id) REFERENCES genre(genre_id) ON delete RESTRICT ON update RESTRICT
);

--Создание LIKES
create TABLE IF NOT EXISTS likes (
	user_id INTEGER NOT NULL,
	film_id INTEGER NOT NULL,
	CONSTRAINT likes_pk PRIMARY KEY (user_id,film_id),
	CONSTRAINT likes_fk FOREIGN KEY (user_id) REFERENCES users(id) ON delete CASCADE ON update CASCADE,
	CONSTRAINT likes_fk_1 FOREIGN KEY (film_id) REFERENCES films(id) ON delete CASCADE ON update CASCADE
);

