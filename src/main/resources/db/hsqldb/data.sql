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


INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (1,1,true,false,1,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (7,1,true,false,1,2);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (2,2,false,false,2,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (3,3,false,true,3,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (4,4,false,false,4,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (5,5,false,false,5,1);
INSERT INTO results(id,position,pole,lap,pilot_id,granpremio_id) VALUES (6,6,false,false,6,1);


--Cada liga con su escudería sistema que tiene todos los pilotos que nadie posee
INSERT INTO league(id,name,league_code,league_date) VALUES (1,'Liga 1','QWEASDFRGT','2019/02/01');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (8,'Sistema',0,0,1, 'admin1');
INSERT INTO league(id,name,league_code,league_date) VALUES (2,'Liga 2','OLIKJUHMNJ','2019/06/12');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (9,'Sistema',0,0,2, 'admin1');
INSERT INTO league(id,name,league_code,league_date) VALUES (3,'Liga 3','UHJNBGFVTR','2019/09/21');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (10,'Sistema',0,0,3, 'admin1');
INSERT INTO team(id,name,points,money,league_id, username) VALUES (12,'OlaKeTal',0,0,3, 'serrojjim');


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

INSERT INTO tabla_consultas(actual_race,races_completed,categoria_actual,time_message,num_users,num_equipos,num_ligas,races_validated) VALUES(1,0,'MOTO3',null,10,0,2,0);

INSERT INTO "PUBLIC"."PILOT" VALUES

(7, 1497, 2, '75', 'Arenas', 'Albert', 'Spain'),
(8, 2409, 2, '17', 'McPhee', 'John', 'Great Britain'),
(9, 3566, 2, '79', 'Ogura', 'Ai', 'Japan'),
(10, 1582, 2, '5', STRINGDECODE('Masi\u00e1'), 'Jaume', 'Spain'),
(11, 2502, 2, '24', 'Suzuki', 'Tatsuki', 'Japan'),
(12, 3686, 2, '2', 'Rodrigo', 'Gabriel', 'Argentina'),
(13, 1776, 2, '52', 'Alcoba', 'Jeremy', 'Spain'),
(14, 3189, 2, '12', 'Salac', 'Filip', 'Czech Republic'),
(15, 2752, 2, '7', 'Foggia', 'Dennis', 'Italy'),
(16, 3395, 2, '25', STRINGDECODE('Fern\u00e1ndez'), STRINGDECODE('Ra\u00fal'), 'Spain'),
(17, 3227, 2, '11', 'Garcia', 'Sergio', 'Spain'),
(18, 3424, 2, '53', STRINGDECODE('\u00d6nc\u00fc'), 'Deniz', 'Andorra'),
(19, 2200, 2, '21', STRINGDECODE('L\u00f3pez'), 'Alonso', 'Spain'),
(20, 3212, 2, '27', 'Toba', 'Kaito', 'Japan'),
(21, 2094, 2, '14', 'Arbolino', 'Tony', 'Italy'),
(22, 1698, 2, '16', 'Migno', 'Andrea', 'Italy'),
(23, 2861, 2, '55', 'Fenati', 'Romano', 'Italy'),
(24, 3913, 2, '92', 'Kunii', 'Yuki', 'Japan'),
(25, 2568, 2, '71', 'Sasaki', 'Ayumu', 'Japan'),
(26, 3017, 2, '6', 'Yamanaka', 'Ryusei', 'Andorra'),
(27, 3582, 2, '99', 'Tatay', 'Carlos', 'Spain'),
(28, 2579, 2, '82', 'Nepa', 'Stefano', 'Italy'),
(29, 1564, 2, '9', 'Pizzoli', 'Davide', 'Andorra'),
(30, 1826, 2, '54', 'Rossi', 'Riccardo', 'Italy'),
(31, 2770, 2, '50', 'Dupasquier', 'Jason', 'Andorra'),
(32, 1895, 2, '89', 'Idham', 'Khairul', 'Malaysia'),
(33, 1540, 2, '73', 'Kofler', 'Maximilian', 'Austria'),
(34, 3033, 2, '13', 'Vietti', 'Celestino', 'Italy'),
(35, 1433, 2, '60', 'Geiger', 'Dirk', 'Germany'),
(36, 2338, 2, '40', 'Binder', 'Darryn', 'South Africa'),
(37, 2425, 2, '20', STRINGDECODE('Garc\u00eda'), STRINGDECODE('Jos\u00e9'), 'Andorra'),
(38, 3143, 1, '45', 'Nagashima', 'Tetsuta', 'Japan'),
(39, 2982, 1, '7', 'Baldassarri', 'Lorenzo', 'Italy'),
(40, 3106, 1, '33', 'Bastianini', 'Enea', 'Italy'),
(41, 2534, 1, '16', 'Roberts', 'Joe', 'USA'),
(42, 3002, 1, '87', 'Gardner', 'Remy', 'Australia'),
(43, 2220, 1, '9', 'Navarro', 'Jorge', 'Spain'),
(44, 3218, 1, '23', STRINGDECODE('Schr\u00f6tter'), 'Marcel', 'Germany'),
(45, 1066, 1, '44', 'Canet', STRINGDECODE('Ar\u00f2n'), 'Spain'),
(46, 1847, 1, '97', 'Vierge', 'Xavi', 'Spain'),
(47, 2038, 1, '12', 'Luthi', 'Thomas', 'Switzerland'),
(48, 1624, 1, '64', 'Bendsneyder', 'Bo', 'Netherlands'),
(49, 3758, 1, '72', 'Bezzecchi', 'Marco', 'Italy'),
(50, 1697, 1, '21', 'Di', 'Fabio', 'Italy'),
(51, 2785, 1, '96', 'Dixon', 'Jake', 'Great Britain'),
(52, 2086, 1, '62', 'Manzi', 'Stefano', 'Italy'),
(53, 1653, 1, '57', 'Pons', 'Edgar', 'Spain'),
(54, 3541, 1, '40', STRINGDECODE('Garz\u00f3'), STRINGDECODE('H\u00e9ctor'), 'Spain'),
(55, 2841, 1, '11', 'Bulega', STRINGDECODE('Nicol\u00f2'), 'Italy'),
(56, 3468, 1, '55', 'Syahrin', 'Hafizh', 'Malaysia'),
(57, 3302, 1, '88', STRINGDECODE('Mart\u00edn'), 'Jorge', 'Spain'),
(58, 1407, 1, '24', 'Corsi', 'Simone', 'Italy'),
(59, 2871, 1, '2', 'Raffin', 'Jesko', 'Switzerland'),
(60, 3789, 1, '19', 'Dalla', 'Lorenzo', 'Italy'),
(61, 2483, 1, '35', 'Chantra', 'Somkiat', 'Thailand'),
(62, 2221, 1, '10', 'Marini', 'Luca', 'Italy'),
(63, 1688, 1, '99', 'Kasmayudin', 'Kasma', 'Malaysia'),
(64, 1952, 1, '37', STRINGDECODE('Fern\u00e1ndez'), 'Augusto', 'Spain'),
(65, 1983, 1, '42', STRINGDECODE('Ram\u00edrez'), 'Marcos', 'Spain'),
(66, 3162, 1, '22', 'Lowes', 'Sam', 'Great Britain'),
(67, 1935, 2, '23', 'Antonelli', STRINGDECODE('Niccol\u00f2'), 'Italy'),
(68, 2630, 2, '70', 'Baltus', 'Barry', 'Andorra'),
(69, 3046, 0, '20', 'Quartararo', 'Fabio', 'France'),
(70, 1823, 0, '12', STRINGDECODE('Vi\u00f1ales'), 'Maverick', 'Spain'),
(71, 2734, 0, '4', 'Dovizioso', 'Andrea', 'Italy'),
(72, 2210, 0, '43', 'Miller', 'Jack', 'Australia'),
(73, 2263, 0, '21', 'Morbidelli', 'Franco', 'Italy');   
INSERT INTO "PUBLIC"."PILOT" VALUES
(74, 1928, 0, '44', STRINGDECODE('Espargar\u00f3'), 'Pol', 'Spain'),
(75, 2784, 0, '63', 'Bagnaia', 'Francesco', 'Italy'),
(76, 3137, 0, '88', 'Oliveira', 'Miguel', 'Portugal'),
(77, 2457, 0, '9', 'Petrucci', 'Danilo', 'Italy'),
(78, 1210, 0, '30', 'Nakagami', 'Takaaki', 'Japan'),
(79, 2460, 0, '5', 'Zarco', 'Johann', 'France'),
(80, 3729, 0, '73', STRINGDECODE('M\u00e1rquez'), STRINGDECODE('\u00c1lex'), 'Spain'),
(81, 2638, 0, '33', 'Binder', 'Brad', 'South Africa'),
(82, 3559, 0, '53', 'Rabat', 'Tito', 'Spain'),
(83, 1765, 0, '38', 'Smith', 'Bradley', 'Great Britain'),
(84, 3761, 0, '93', STRINGDECODE('M\u00e1rquez'), 'Marc', 'Spain'),
(85, 2742, 0, '27', 'Lecuona', 'Iker', 'Spain'),
(86, 3071, 0, '46', 'Rossi', 'Valentino', 'Italy'),
(87, 2725, 0, '41', STRINGDECODE('Espargar\u00f3'), 'Aleix', 'Spain'),
(88, 2534, 0, '36', 'Mir', 'Joan', 'Spain'),
(89, 2336, 0, '35', 'Crutchlow', 'Cal', 'Great Britain'),
(90, 2866, 0, '42', 'Rins', STRINGDECODE('\u00c1lex'), 'Spain'),
(91, 2380, 1, '77', 'Aegerter', 'Dominique', 'Switzerland'),
(92, 1658, 0, '6', 'Bradl', 'Stefan', 'Germany'),
(93, 3897, 0, '51', 'Pirro', 'Michele', 'Italy');    
