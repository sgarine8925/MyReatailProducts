CREATE KEYSPACE myretail WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE myretail.product(id int PRIMARY KEY, price decimal, currency text);

INSERT INTO myretail.product (id, price, currency) VALUES(13860428, 56.78, 'USD');