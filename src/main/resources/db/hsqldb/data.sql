-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(username, authority) VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(username, authority) VALUES ('owner1','owner');

INSERT INTO users(username,password,enabled) VALUES ('prueba','prueba',TRUE);
INSERT INTO authorities(username, authority) VALUES ('prueba','owner');

INSERT INTO users(username,password,enabled) VALUES ('pruebaNoPets','pruebaNoPets',TRUE);
INSERT INTO authorities(username, authority) VALUES ('pruebaNoPets','owner');

INSERT INTO users(username,password,enabled) VALUES ('prueba1','practica',TRUE);
INSERT INTO authorities(username, authority) VALUES ('prueba1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(username, authority) VALUES ('vet1','veterinarian');

INSERT INTO vets(id, first_name, last_name) VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name, last_name) VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name, last_name) VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name, last_name) VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name, last_name) VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name, last_name)  VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties(id, name) VALUES (1, 'radiology');
INSERT INTO specialties(id, name) VALUES (2, 'surgery');
INSERT INTO specialties(id, name) VALUES (3, 'dentistry');

INSERT INTO vet_specialties(vet_id, specialty_id) VALUES (2, 1);
INSERT INTO vet_specialties(vet_id, specialty_id) VALUES (3, 2);
INSERT INTO vet_specialties(vet_id, specialty_id) VALUES (3, 3);
INSERT INTO vet_specialties(vet_id, specialty_id) VALUES (4, 2);
INSERT INTO vet_specialties(vet_id, specialty_id) VALUES (5, 1);

INSERT INTO types(id, name) VALUES (1, 'cat');
INSERT INTO types(id, name) VALUES (2, 'dog');
INSERT INTO types(id, name) VALUES (3, 'lizard');
INSERT INTO types(id, name) VALUES (4, 'snake');
INSERT INTO types(id, name) VALUES (5, 'bird');
INSERT INTO types(id, name) VALUES (6, 'hamster');
INSERT INTO genders(id, name) VALUES (1, 'Masculino');
INSERT INTO genders(id, name) VALUES (2, 'Femenino');

INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (11, 'Prueba', 'pruebesita', 'indigente', 'las 3000', '666666666', 'prueba');
INSERT INTO owners(id, first_name, last_name, address, city, telephone, username) VALUES (12, 'PruebaNoPets', 'NoPets', 'sinPerrosStreet', 'SuCasa', '666666668', 'pruebaNoPets');

INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (1, 'Leo', '2010-09-07', 1, 1,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (2, 'Basil', '2012-08-06', 6, 2,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (6, 'George', '2010-01-20', 4, 5,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (8, 'Max', '2012-09-04', 1, 6,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (13, 'Sly', '2012-06-08', 1, 10,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (14, 'pruebaMasc', '2012-06-08', 1, 11,1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (15, 'pruebaFem', '2012-06-08', 1, 11,2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,gender_id) VALUES (16, 'hamstar', '2012-06-08', 6, 11,2);


INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');


INSERT INTO citas(id,pet1_id,pet2_id,cita_date,place) VALUES (1,1,2, '2010-09-07','calle');


INSERT INTO product(id,name, description, price,stock,url_image) VALUES (1, 'Champu Para Perros','Champu para perros esencia de aloe',9.60,30,'https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg');
INSERT INTO product(id,name, description, price,stock,url_image) VALUES (2, 'Arnés Challenger Roca','Diseñado para trabajos verticales y en altura',138.25,20,'https://www.ttrinternational.com/tienda/wp-content/uploads/2017/07/20612-arnes-roca-challenger.jpg');
INSERT INTO product(id,name, description, price,stock,url_image) VALUES (3, 'Benevo Para Gatos y Perros','Lata de comida vegana y saludable para gatos y perros',4.29,200,'https://images1.tiendanimal.es/g/7166-benevo-duo-alimento-humedo-vegano-vegetariano-perro-gato.jp.jpg');
INSERT INTO product(id,name, description, price,stock,url_image) VALUES (4, 'Funda Para Cama De Perro','Características de la Funda para cama de perro o animal domestico:Alto: 7 cm ± 1 cm.',19.0,45,'https://ventadecolchones.com/7890-large_default/funda-para-cama-de-perro.jpg');
INSERT INTO product(id,name, description, price,stock,url_image) VALUES (5, 'Kong Puppy S','Diseñado para introducir comida en el interior y la mascota se entretenga sacándola',4.49,60,'https://static.miscota.com/media/1/photos/products/146981/146981_1_g.jpeg');

INSERT INTO matingoffers(id,pet_id,description) VALUES (1,1, 'Chico y peludo');
INSERT INTO matingoffers(id,pet_id,description) VALUES (2,2,'Le gustan las pipas');
INSERT INTO matingoffers(id,pet_id,description) VALUES (3,3,'Es muy seria');
INSERT INTO matingoffers(id,pet_id,description) VALUES (4,9, 'Huele a tabaco y berenjena');
INSERT INTO matingoffers(id,pet_id,description) VALUES (5,4, 'Bombástica');
INSERT INTO matingoffers(id,pet_id,description) VALUES (6,14, 'Muy manso');

INSERT INTO bookings(id,product_id,num_productos,user_id,fecha) VALUES (1,1,4,'prueba1','2013-01-01');
INSERT INTO bookings(id,product_id,num_productos,user_id,fecha) VALUES (2,2,5,'prueba1','2013-01-03');
INSERT INTO bookings(id,product_id,num_productos,user_id,fecha) VALUES (3,3,10,'prueba1','2013-01-02');

INSERT INTO comments(id,product_id,email,descripcion,user_id,fecha) VALUES (1,1,'pepefer@gmail.com','descripcion','prueba1','2010-09-07');