-- One admin user, named admin1 with password 4dm1n and authority admin
INSERT INTO users(username,password,enabled,email) VALUES ('admin1','4dm1n',TRUE,'admin1@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

-- Usuario de antcammar4 como owner
INSERT INTO users(username,password,enabled,email) VALUES ('antcammar4','123456',TRUE,'antcammar4@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (4,'antcammar4','admin');
-- Usuario de serrojjim como owner
INSERT INTO users(username,password,enabled,email) VALUES ('serrojjim','123456',TRUE,'serrojjim@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (5,'serrojjim','admin');
-- Usuario de aleruijur como owner
INSERT INTO users(username,password,enabled,email) VALUES ('aleruijur','aleale',TRUE,'aleruijur@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (6,'aleruijur','admin');
-- Usuario de migniearj como owner
INSERT INTO users(username,password,enabled,email) VALUES ('migniearj','miguesnow',TRUE,'migniearj@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (7,'migniearj','admin');
-- Usuario de alvcorcas como owner
INSERT INTO users(username,password,enabled,email) VALUES ('alvcorcas','1eB@6!XYUYe6c33&',TRUE,'alvcorcas@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (8,'alvcorcas','admin');
-- Usuario de martorsan13 como owner
INSERT INTO users(username,password,enabled,email) VALUES ('martorsan13','mariano',TRUE,'martorsan13@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (9,'martorsan13','admin');
--Usuario para testear las vistas de user
INSERT INTO users(username,password,enabled,email) VALUES ('xnegis','12345',TRUE,'xnegis@gmail.com');
INSERT INTO authorities(id,username,authority) VALUES (10,'xnegis','user');

INSERT INTO messages (id,asunto,cuerpo,usernamesend,usernamereceive) VALUES (1,'Prueba1','Prueba1','serrojjim','martorsan13');

INSERT INTO "PUBLIC"."PILOT" VALUES
(1, 2000, 2, 'Rojas', 'Sergio', 'Espanola', '0'),
(2, 2100, 2, 'Rojas', 'Antonio', 'Espanola', '0'),
(3, 1700, 2, 'Rojas', 'Miguel', 'Espanola', '0'),
(4, 3000, 2, 'Rojas', 'Mariano', 'Espanola', '0'),
(5, 1900, 2, 'Rojas', 'Alvaro', 'Espanola', '0'),
(6, 2700, 2, 'Rojas', 'Alejandro', 'Espanola', '0'),
(7, 1738, 2, 'Toba', 'Kaito', 'Japan', '0'),
(8, 1760, 2, 'Dalla Porta', 'Lorenzo', 'Italy', '0'),
(9, 1330, 2, 'Canet', 'Aron', 'Spain', '0'),
(10, 1210, 2, 'Ramirez', 'Marcos', 'Spain', '0'),
(11, 1026, 2, 'Vietti', 'Celestino', 'Italy', '0'),
(12, 3056, 2, 'Arenas', 'Albert', 'Spain', '0'),
(13, 2768, 2, 'Fernandez', 'Raul', 'Spain', '0'),
(14, 3718, 2, 'Antonelli', 'Niccolo', 'Italy', '0'),
(15, 3240, 2, 'Fenati', 'Romano', 'Italy', '0'),
(16, 3148, 2, 'Kornfeil', 'Jakub', 'Czech Republic', '0'),
(17, 1659, 2, 'Ogura', 'Ai', 'Japan', '0'),
(18, 3386, 2, 'Lopez', 'Alonso', 'Spain', '0'),
(19, 3689, 2, 'Mcphee', 'John', 'Great Britain', '0'),
(20, 2007, 2, 'Migno', 'Andrea', 'Italy', '0'),
(21, 1647, 2, 'Rodrigo', 'Gabriel', 'Argentina', '0'),
(22, 2661, 2, 'Arbolino', 'Tony', 'Italy', '0'),
(23, 1502, 2, 'Perez', 'Vicente', 'Spain', '0'),
(24, 2395, 2, 'Oncu', 'Can', 'Turkey', '0'),
(25, 2815, 2, 'Masaki', 'Kazuki', 'Japan', '0'),
(26, 1135, 2, 'Yamanaka', 'Ryusei', 'Japan', '0'),
(27, 3095, 2, 'Salac', 'Filip', 'Czech Republic', '0'),
(28, 3255, 2, 'Rossi', 'Riccardo', 'Italy', '0'),
(29, 2820, 2, 'Yurchenko', 'Makar', 'Kazakhstan', '0'),
(30, 1074, 2, 'Booth-Amos', 'Tom', 'Mexico', '0'),
(31, 1947, 2, 'Suzuki', 'Tatsuki', 'Japan', '0'),
(32, 2672, 2, 'Foggia', 'Dennis', 'Italy', '0'),
(33, 2679, 2, 'Binder', 'Darryn', 'South Africa', '0'),
(34, 3568, 2, 'Sasaki', 'Ayumu', 'Japan', '0'),
(35, 1900, 2, 'Masia', 'Jaume', 'Spain', '0'),
(36, 2920, 1, 'Baldassarri', 'Lorenzo', 'Italy', '0'),
(37, 1081, 1, 'Luthi', 'Thomas', 'Switzerland', '0'),
(38, 1543, 1, 'Schrotter', 'Marcel', 'Germany', '0'),
(39, 3611, 1, 'Gardner', 'Remy', 'Australia', '0'),
(40, 1066, 1, 'Fernandez', 'Augusto', 'Spain', '0'),
(41, 2840, 1, 'Lowes', 'Sam', 'Great Britain', '0'),
(42, 3297, 1, 'Marquez', 'Alex', 'Spain', '0'),
(43, 2275, 1, 'Marini', 'Luca', 'Italy', '0'),
(44, 1587, 1, 'Bastianini', 'Enea', 'Italy', '0'),
(45, 2870, 1, 'Vierge', 'Xavi', 'Spain', '0'),
(46, 3187, 1, 'Di Giannantonio', 'Fabio', 'Italy', '0'),
(47, 1094, 1, 'Binder', 'Brad', 'South Africa', '0'),
(48, 1892, 1, 'Locatelli', 'Andrea', 'Italy', '0'),
(49, 3523, 1, 'Raffin', 'Jesko', 'Switzerland', '0'),
(50, 3380, 1, 'Martin', 'Jorge', 'Spain', '0'),
(51, 3076, 1, 'Bendsneyder', 'BO', 'Netherlands', '0'),
(52, 2730, 1, 'Pawi', 'Khairul Idham', 'Malaysia', '0'),
(53, 1952, 1, 'Aegerter', 'Dominique', 'Switzerland', '0'),
(54, 1148, 1, 'Corsi', 'Simone', 'Italy', '0'),
(55, 1084, 1, 'Manzi', 'Stefano', 'Italy', '0'),
(56, 2288, 1, 'Tulovic', 'Lukas', 'Germany', '0'),
(57, 2144, 1, 'Roberts', 'Joe', 'USA', '0'),
(58, 2007, 1, 'Oettl', 'Philipp', 'Germany', '0'),
(59, 2331, 1, 'Ekky Pratama', 'Dimas', 'Indonesia', '0'),
(60, 1444, 1, 'Cardelus', 'Xavi', 'Andorra', '0'),
(61, 3231, 1, 'Bezzecchi', 'Marco', 'Italy', '0'),
(62, 1902, 1, 'Nagashima', 'Tetsuta', 'Japan', '0'),
(63, 1504, 1, 'Dixon', 'Jake', 'Great Britain', '0'),
(64, 2996, 1, 'Chantra', 'Somkiat', 'Thailand', '0'),
(65, 3682, 1, 'Navarro', 'Jorge', 'Spain', '0'),
(66, 1153, 1, 'Bulega', 'Nicolo', 'Italy', '0'),
(67, 3696, 1, 'Lecuona', 'Iker', 'Spain', '0'),
(68, 2760, 0, 'Dovizioso', 'Andrea', 'Italy', '0'),
(69, 1032, 0, 'Marquez', 'Marc', 'Spain', '0'),
(70, 3647, 0, 'Crutchlow', 'Cal', 'Great Britain', '0'),
(71, 3959, 0, 'Rins', 'Alex', 'Spain', '0'),
(72, 3274, 0, 'Rossi', 'Valentino', 'Italy', '0'),
(73, 1786, 0, 'Petrucci', 'Danilo', 'Italy', '0'),
(74, 2580, 0, 'Vinales', 'Maverick', 'Spain', '0'),
(75, 3941, 0, 'Mir', 'Joan', 'Spain', '0'),
(76, 2998, 0, 'Nakagami', 'Takaaki', 'Japan', '0'),
(77, 3250, 0, 'Espargaro', 'Aleix', 'Spain', '0'),
(78, 1615, 0, 'Morbidelli', 'Franco', 'Italy', '0'),
(79, 1935, 0, 'Espargaro', 'Pol', 'Spain', '0'),
(80, 2480, 0, 'Lorenzo', 'Jorge', 'Spain', '0');       
INSERT INTO "PUBLIC"."PILOT" VALUES
(81, 2720, 0, 'Iannone', 'Andrea', 'Italy', '0'),
(82, 1814, 0, 'Zarco', 'Johann', 'France', '0'),
(83, 1173, 0, 'Quartararo', 'Fabio', 'France', '0'),
(84, 3756, 0, 'Oliveira', 'Miguel', 'Portugal', '0'),
(85, 2398, 0, 'Abraham', 'Karel', 'Czech Republic', '0'),
(86, 2635, 0, 'Rabat', 'Esteve', 'Spain', '0'),
(87, 1236, 0, 'Syahrin Abdullah', 'Hafizh', 'Malaysia', '0'),
(88, 1864, 0, 'Smith', 'Bradley', 'Great Britain', '0'),
(89, 3770, 0, 'Miller', 'Jack', 'Australia', '0'),
(90, 2139, 0, 'Bagnaia', 'Francesco', 'Italy', '0'); 





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
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (4,false,4,1);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (5,false,5,11);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (6,false,6,11);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (7,false,7,11);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (8,false,8,5);
INSERT INTO recruit(id,for_sale,pilot_id,team_id) VALUES (9,false,9,5);

INSERT INTO offer(id,price,status,recruit_id,team_id) VALUES (1,2000,2,1,1);





INSERT INTO "PUBLIC"."GRANPREMIO" VALUES
(1, TRUE, 'Losail International Circuit', DATE '2019-03-08',FALSE,FALSE, 'https://cdn1.snaplap.net/wp-content/uploads/2016/11/22114232/losail-night.jpg','QAT', 'Doha', NULL, NULL, NULL),
(2, TRUE, 'Autodromo Termas de Rio Hondo', DATE '2019-03-29', FALSE,FALSE, 'https://cdn-1.motorsport.com/images/mgl/6zMgllJY/s8/motogp-argentinian-gp-2017-autodromo-termas-de-rio-hondo.jpg','ARG', 'Santiago del Estero', NULL, NULL, NULL),
(3, TRUE, 'Circuit of The Americas', DATE '2019-04-12', FALSE,FALSE,'https://www.racefans.net/wp-content/uploads/2018/09/racefansdotnet-20180904-160658-1.jpg', 'USA', 'Austin', NULL, NULL, NULL),
(4, TRUE, 'Circuit de Jerez', DATE '2019-05-03', FALSE,FALSE,'https://multimedia.andalucia.org/fotos/image_201037.jpeg', 'ESP', 'Jerez', NULL, NULL, NULL),
(5, TRUE, 'Le Mans Bugatti Circuit', DATE '2019-05-17', FALSE,FALSE,'https://www.giornalemotori.com/wp-content/uploads//2014/05/NCA08GPF_300_2700.jpg', 'FRA', 'Le Mans', NULL, NULL, NULL),
(6, TRUE, 'Autodromo Internazionale del Mugello', DATE '2019-05-31', FALSE,FALSE,'https://media-cdn.tripadvisor.com/media/photo-s/0f/1b/0f/b9/autodromo-del-mugello.jpg', 'ITA', 'Scarperia Firenze', NULL, NULL, NULL),
(7, TRUE, 'Circuit de Catalunya', DATE '2019-06-14', FALSE,FALSE,'https://upload.wikimedia.org/wikipedia/commons/e/e7/Circuit_de_Barcelona-Catalunya%2C_April_19%2C_2018_SkySat_%28cropped%29.jpg', 'ESP', 'Barcelona', NULL, NULL, NULL),
(8, TRUE, 'TT Circuit Assen', DATE '2019-06-28', FALSE,FALSE,'https://i.pinimg.com/originals/83/fb/65/83fb656e66c5f42fd2ec935e7690d35c.jpg', 'NLD', 'Assen', NULL, NULL, NULL),
(9, TRUE, 'Sachsenring', DATE '2019-07-05', FALSE,FALSE,'https://www.zdf.de/assets/sachsenring-von-oben-100~1920x1080?cb=1498821325377', 'DEU', 'Oberlungwitz', NULL, NULL, NULL),
(10, TRUE, 'Automotodrom Brno', DATE '2019-08-02', FALSE,FALSE,'https://cdn1.snaplap.net/wp-content/uploads/2016/10/13093914/brno-aerial.jpg', 'CZE', 'Brno', NULL, NULL, NULL),
(11, TRUE, 'Red Bull Ring', DATE '2019-08-09', FALSE,FALSE, 'https://cdn.motorlat.com/administrator/uploads/12423_helico-austria.jpg','AUT', 'Spielberg', NULL, NULL, NULL),
(12, TRUE, 'Silverstone Circuit', DATE '2019-08-23', FALSE,FALSE,'https://static01.nyt.com/images/2019/07/13/sports/13sp-silverstone-inyt1/merlin_157516356_009c6be7-3977-4ad6-b4bc-52d2addf96e1-superJumbo.jpg', 'ENG', 'Silverstone', NULL, NULL, NULL),
(13, TRUE, 'Misano World Circuit', DATE '2019-09-13', FALSE,FALSE,'https://media-cdn.tripadvisor.com/media/photo-s/1a/b3/d1/d2/le-vie-di-fuga-di-misano.jpg', 'ITA', 'Misano Adriatico', NULL, NULL, NULL),
(14, TRUE, 'Motorland Aragon', DATE '2019-09-20', FALSE,FALSE,'https://www.tumotoweb.com/tmw5/wp-content/uploads/2014/12/Circuito-Motorland-Aragon.jpg', 'ESP', 'Alcaniz', NULL, NULL, NULL),
(15, TRUE, 'Chang International Circuit', DATE '2019-10-04', FALSE,FALSE,'https://i.pinimg.com/originals/41/53/4f/41534f21b9d6e091429b7a5092ba50f3.jpg', 'THA', 'Buriram', NULL, NULL, NULL),
(16, TRUE, 'Twin Ring Motegi', DATE '2019-10-18', FALSE,FALSE,'https://d2ahiw9kb7is19.cloudfront.net/-/media/06F0CB9B19D849EC9998D5B4BCBC916C.jpg?d=20171117T090048&w=750', 'JPN', 'Hiyama', NULL, NULL, NULL),
(17, TRUE, 'Phillip Island Circuit', DATE '2019-10-24', FALSE,FALSE,'https://www.austadiums.com/stadiums/photos/phillip-island-circuit.jpg', 'AUS', 'Phillip Island', NULL, NULL, NULL),
(18, TRUE, 'Sepang International Circuit', DATE '2019-11-01', FALSE,FALSE,'https://i.redd.it/0akkcu17mbb51.jpg', 'MYS', 'Sepang', NULL, NULL, NULL),
(19, TRUE, 'Circuit de la Comunitat Valenciana Ricardo Tormo', DATE '2019-11-15', FALSE,FALSE, 'https://www.mundomotero.com/wp-content/uploads/2012/09/circuito_comunitat_valenciana_reasfaltado_2012_n.jpg','ESP', 'Valencia', NULL, NULL, NULL);  


INSERT INTO tabla_consultas(actual_race,races_completed,categoria_actual,time_message,num_users,num_equipos,num_ligas,races_validated) VALUES(1,0,'MOTO3',null,10,0,2,0);

INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (1,1,1,2,2,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (2,1,2,2,1,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (3,1,3,2,2,1); 
INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (4,1,3,1,2,1); 
--INSERT INTO lineup(id,category,gp_id,recruit1_id,recruit2_id,team_id) VALUES (5,1,3,1,2,1); 
