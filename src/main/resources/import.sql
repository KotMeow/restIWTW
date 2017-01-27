CREATE TABLE myuser (username VARCHAR(32) NOT NULL PRIMARY KEY, password VARCHAR(150) NOT NULL);
CREATE TABLE myrole (username VARCHAR(32) NOT NULL, rolename VARCHAR(32) NOT NULL, PRIMARY KEY (username, rolename));

INSERT INTO myuser(username,password) VALUES('admin','jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=');
INSERT INTO myrole(username,rolename) VALUES('admin','ADMIN');