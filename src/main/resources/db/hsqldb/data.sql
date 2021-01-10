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