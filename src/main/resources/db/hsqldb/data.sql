-- One admin user, named admin1 with password 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with password 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with password v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

-- Usuario de antcammar4 como owner
INSERT INTO users(username,password,enabled) VALUES ('antcammar4','123456',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'antcammar4','admin');
-- Usuario de serrojjim como owner
INSERT INTO users(username,password,enabled) VALUES ('serrojjim','123456',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'serrojjim','admin');
-- Usuario de aleruijur como owner
INSERT INTO users(username,password,enabled) VALUES ('aleruijur','aleale',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'aleruijur','admin');
-- Usuario de migniearj como owner
INSERT INTO users(username,password,enabled) VALUES ('migniearj','miguesnow',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'migniearj','admin');
-- Usuario de alvcorcas como owner
INSERT INTO users(username,password,enabled) VALUES ('alvcorcas','1eB@6!XYUYe6c33&',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'alvcorcas','admin');
-- Usuario de martorsan13 como owner
INSERT INTO users(username,password,enabled) VALUES ('martorsan13','mariano',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'martorsan13','admin');
--Usuario para testear las vistas de user
INSERT INTO users(username,password,enabled) VALUES ('xnegis','12345',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'xnegis','user');



INSERT INTO messages (id,asunto,cuerpo,usernamesend,usernamereceive) VALUES (1,'Prueba1','Prueba1','serrojjim','martorsan13');
INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Sergio', 'Rojas', 'Calle monzón 24', 'Sevilla', '633732554', 'serrojjim');
INSERT INTO owners VALUES(12,'Antonio','Campuzano','41430 Blas Infante','Sevilla','627665452','antcammar4');
INSERT INTO owners VALUES (13, 'Alejandro', 'Ruiz', '41703 Parque Giralda', 'Sevilla', '637676578', 'aleruijur');
INSERT INTO owners VALUES(14,'Miguel Ángel','Nieva','41013  Calle Castillo de Constantina','Sevilla','644214406','migniearj');
INSERT INTO owners VALUES(15,'Alvaro','Cortes','41010  Calle San Jacinto','Sevilla','678012345','alvcorcas');
INSERT INTO owners VALUES(16,'Mariano Manuel','Torrado','41012  Calle Monzón','Sevilla','685933349','martorsan13');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);



--Mascota de serrojjim
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Dama', '2015-11-25', 2, 11);
--Mascota de antcammar4
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Negis', '2012-06-08', 3, 12);
--Mascota de aleruijur
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Nube', '2011-09-13', 5, 13);
--Mascota de migniearj
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Snow', '2018-06-06', 5, 14);
--Mascota de alvcorcas
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Chipi', '2015-02-04', 1, 15);
--Mascota de martorsan13
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (19, 'Keko', '2013-05-24', 2, 16);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO pilot(id,name,lastname,nationality,dorsal,category,base_value) VALUES (1,'Sergio','Rojas','Espanola',93,1,2000);
INSERT INTO pilot(id,name,lastname,nationality,dorsal,category,base_value) VALUES (2,'Antonio','Rojas','Espanola',43,0,2100);
INSERT INTO pilot(id,name,lastname,nationality,dorsal,category,base_value) VALUES (3,'Miguel','Rojas','Espanola',234,1,1700);
INSERT INTO pilot(id,name,lastname,nationality,dorsal,category,base_value) VALUES (4,'Mariano','Rojas','Espanola',45,1,3000);
INSERT INTO pilot(id,name,lastname,nationality,dorsal,category,base_value) VALUES (5,'Alvaro','Rojas','Espanola',73,1,1900);
INSERT INTO pilot(id,name,lastname,nationality,dorsal,category,base_value) VALUES (6,'Alejandro','Rojas','Espanola',83,2,2700);


INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (1,'Grand Prix Of Qatar','2020-03-08','Losail','QAT',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (2,'GP Spain','2020-07-19','Jerez','ESP',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (3,'GP Andalusia','2020-07-26','Jerez','ESP',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (4,'Grand Czech Republic','2020-08-09','BRNO','CZE',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (5,'Grand Prix Of Austria','2020-08-16', 'Red Bull Ring','AUT',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (6,'Grand Prix Of Estiria','2020-08-23', 'Red Bull Ring','AUT',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (7,'Grand Prix San Marino','2020-09-13', 'Misano','SMR',false,true);
INSERT INTO granpremio(id,site,date0,circuit,racecode,has_been_run,calendar) VALUES (8,'NoDebeDeSeleccionarse','2015-09-13', 'NoDebeDeSeleccionarse','NDS',false,true);


INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (1,1,true,false,1,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (7,1,true,false,1,2);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (2,2,false,false,2,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (3,3,false,true,3,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (4,4,false,false,4,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (5,5,false,false,5,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (6,6,false,false,6,1);


--Cada liga con su escudería sistema que tiene todos los pilotos que nadie posee
INSERT INTO league(id,name,league_code,league_date) VALUES (1,'Liga1','QWEASDFRGT','2019/02/01');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (8,'Sistema',0,0,1, 'admin1');
INSERT INTO league(id,name,league_code,league_date) VALUES (2,'Liga1','OLIKJUHMNJ','2019/06/12');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (9,'Sistema',0,0,2, 'admin1');
INSERT INTO league(id,name,league_code,league_date) VALUES (3,'Liga1','UHJNBGFVTR','2019/09/21');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (10,'Sistema',0,0,3, 'admin1');


INSERT INTO team(id,name,points,money,league_id, username) VALUES (1,'MigueTeam',120,40000,2,'antcammar4');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (2,'campu',284,5242,1, 'antcammar4');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (3,'MigueTeam',22,111,1, 'migniearj');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (4,'sergio',516,651651,1,'serrojjim');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (5,'Miguesnow',7572,84600,2, 'migniearj');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (6,'MigueSnOw',577,686,1, 'martorsan13');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (7,'Miguelito',2757,4686,1, 'aleruijur');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (11,'polloRebozado',120,40000,2,'alvcorcas');

INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (1,true,1,1);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (2,false,2,1);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (3,false,3,1);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (4,false,4,11);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (5,false,5,11);

INSERT INTO offer(id,price,status,recruit_id,team_id) VALUES (1,2000,2,1,1);

INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (1,1,1,2,2,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (2,1,2,2,1,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (3,1,3,2,2,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (4,1,3,1,2,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (5,1,3,1,2,1); 

INSERT INTO tabla_consultas(actual_race,races_completed,categoria_actual) VALUES(1,0,'MOTO3')