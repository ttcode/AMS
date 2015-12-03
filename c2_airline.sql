-- phpMyAdmin SQL Dump
-- version 3.3.7deb6
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Dim 16 Juin 2013 à 21:40
-- Version du serveur: 5.1.49
-- Version de PHP: 5.3.3-7+squeeze3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `c2_airline`
--

-- --------------------------------------------------------

--
-- Structure de la table `account_type`
--

CREATE TABLE IF NOT EXISTS `account_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `account_type`
--

INSERT INTO `account_type` (`id`, `type_name`) VALUES
(1, 'Employee'),
(2, 'Customer');

-- --------------------------------------------------------

--
-- Structure de la table `aircraft`
--

CREATE TABLE IF NOT EXISTS `aircraft` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `aircraft_registration` varchar(50) NOT NULL,
  `flight_hour` float NOT NULL,
  `flight_number` int(11) DEFAULT NULL,
  `aircraft_status` char(1) NOT NULL DEFAULT '1',
  `position` char(4) NOT NULL,
  `assigned_flight` int(11) DEFAULT NULL,
  `maintenance_comment` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `aircraft_registration` (`aircraft_registration`),
  KEY `flight_number` (`flight_number`),
  KEY `position` (`position`),
  KEY `model_id` (`model_id`),
  KEY `assigned_flight` (`assigned_flight`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `aircraft`
--

INSERT INTO `aircraft` (`id`, `model_id`, `aircraft_registration`, `flight_hour`, `flight_number`, `aircraft_status`, `position`, `assigned_flight`, `maintenance_comment`) VALUES
(1, 1, 'TJA002', 50, 123, '1', 'VTBS', 1, ''),
(2, 2, 'TJA001', 8, 123, '1', 'VTBS', NULL, ''),
(3, 3, 'TJA005', 22.3, 12, '1', 'LFPG', 2, 'Ok');

-- --------------------------------------------------------

--
-- Structure de la table `aircraft_model`
--

CREATE TABLE IF NOT EXISTS `aircraft_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aircraft_model` varchar(255) NOT NULL,
  `aircraft_type` int(11) NOT NULL,
  `required_pilot` int(11) NOT NULL,
  `required_crew` int(11) NOT NULL,
  `max_passenger` int(11) NOT NULL,
  `max_cargo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `aircraft_type` (`aircraft_type`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=45 ;

--
-- Contenu de la table `aircraft_model`
--

INSERT INTO `aircraft_model` (`id`, `aircraft_model`, `aircraft_type`, `required_pilot`, `required_crew`, `max_passenger`, `max_cargo`) VALUES
(1, 'Boeing 747-400', 1, 2, 10, 416, 178),
(2, 'Boeing 747-100', 3, 3, 10, 366, 162),
(3, 'Boeing 747-200B', 3, 3, 8, 366, 174),
(4, 'Boeing 747-400ER', 1, 2, 8, 416, 164),
(5, 'Boeing 747-300', 3, 3, 8, 366, 178),
(27, 'Boeing 737-400', 1, 2, 5, 80, 100);

-- --------------------------------------------------------

--
-- Structure de la table `aircraft_type`
--

CREATE TABLE IF NOT EXISTS `aircraft_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_name` (`type_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Contenu de la table `aircraft_type`
--

INSERT INTO `aircraft_type` (`id`, `type_name`) VALUES
(2, 'Cargo'),
(3, 'Combi'),
(4, 'Military'),
(1, 'Passenger');

-- --------------------------------------------------------

--
-- Structure de la table `airport`
--

CREATE TABLE IF NOT EXISTS `airport` (
  `icao` char(4) NOT NULL,
  `name` varchar(255) NOT NULL,
  `is_hub` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`icao`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `airport`
--

INSERT INTO `airport` (`icao`, `name`, `is_hub`) VALUES
('LFPG', 'Paris Charles de Gaulle Airport ', 0),
('VHHH', 'Hong Kong International Airport', 0),
('VTBS', 'Bangkok International Suvarnabhumi Airport ', 0),
('ZBAA', 'Beijing Capital International Airport ', 0),
('ZSPD', 'Shanghai Pudong International Airport', 1);

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `registration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hub` char(4) NOT NULL,
  `job_id` int(11) NOT NULL,
  `salary` float NOT NULL,
  `team_id` int(11) DEFAULT NULL,
  `position` char(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `hub` (`hub`),
  KEY `job_id` (`job_id`),
  KEY `position` (`position`),
  KEY `team_id` (`team_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `employee`
--

INSERT INTO `employee` (`id`, `name`, `address`, `phone`, `registration_date`, `hub`, `job_id`, `salary`, `team_id`, `position`) VALUES
(1, 'Admin', '01 Admin Street, Admin City, Admin Land', '0000000000', '2013-05-09 17:18:15', 'ZSPD', 1, 10000, NULL, 'ZSPD'),
(7, 'Loyd Ea', 'Tongji University', '13524864364', '2013-05-15 01:15:16', 'ZSPD', 3, 10000, NULL, 'ZSPD'),
(17, 'pilot1', 'pilot1', 'pilot1', '2013-06-12 17:11:47', 'ZSPD', 4, 3000, 12, 'LFPG'),
(18, 'pilot2', 'pilot2', 'pilot2', '2013-06-12 17:12:18', 'ZSPD', 4, 3000, NULL, 'ZSPD'),
(19, 'cabin11', 'cabin11', 'cabin11', '2013-06-12 17:13:44', 'ZSPD', 5, 3000, 12, 'LFPG'),
(20, 'cabin12', 'cabin12', 'cabin12', '2013-06-12 17:14:04', 'ZSPD', 5, 3000, 12, 'LFPG'),
(21, 'cabin21', 'cabin21', 'cabin21', '2013-06-12 17:14:26', 'ZSPD', 5, 3000, NULL, 'ZSPD'),
(22, 'cabin22', 'cabin22', 'cabin22', '2013-06-12 17:14:50', 'ZSPD', 5, 3000, NULL, 'ZSPD'),
(24, 'pilot', 'pilot', 'pilot', '2013-06-15 17:51:19', 'ZSPD', 4, 3000, NULL, 'ZSPD'),
(27, 'am', 'am', 'am', '2013-06-17 01:53:16', 'ZSPD', 2, 3000, NULL, 'ZSPD'),
(28, 'hr', 'hr', 'hr', '2013-06-17 01:53:44', 'ZSPD', 3, 3000, NULL, 'ZSPD'),
(29, 'fm', 'fm', 'fm', '2013-06-17 01:54:12', 'ZSPD', 6, 3000, NULL, 'ZSPD'),
(30, 'cabin', 'cabin', 'cabin', '2013-06-17 02:08:32', 'ZSPD', 5, 3000, NULL, 'ZSPD');

-- --------------------------------------------------------

--
-- Structure de la table `flightplan`
--

CREATE TABLE IF NOT EXISTS `flightplan` (
  `flight_number` int(11) NOT NULL,
  `flight_type` int(11) NOT NULL,
  `departure_airport` char(4) NOT NULL,
  `arrival_airport` char(4) NOT NULL,
  `departure_time` char(4) NOT NULL,
  `flight_duration` char(4) NOT NULL,
  `flight_route` text NOT NULL,
  `repeat_rule` text,
  PRIMARY KEY (`flight_number`),
  KEY `flight_type` (`flight_type`),
  KEY `departure_airport` (`departure_airport`),
  KEY `arrival_airport` (`arrival_airport`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `flightplan`
--

INSERT INTO `flightplan` (`flight_number`, `flight_type`, `departure_airport`, `arrival_airport`, `departure_time`, `flight_duration`, `flight_route`, `repeat_rule`) VALUES
(1, 2, 'LFPG', 'LFPG', '1215', '1225', '0 stop', '2'),
(2, 2, 'LFPG', 'ZSPD', '0125', '1135', '0 stop', '1'),
(3, 1, 'ZSPD', 'ZBAA', '0930', '0530', '0 stop', '4'),
(4, 1, 'ZBAA', 'ZSPD', '1600', '0530', '0 stop', '3');

-- --------------------------------------------------------

--
-- Structure de la table `flight_type`
--

CREATE TABLE IF NOT EXISTS `flight_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `flight_type`
--

INSERT INTO `flight_type` (`id`, `type_name`) VALUES
(1, 'Domestic'),
(2, 'International');

-- --------------------------------------------------------

--
-- Structure de la table `job`
--

CREATE TABLE IF NOT EXISTS `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) NOT NULL,
  `min_salary` float NOT NULL,
  `max_salary` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_name` (`job_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `job`
--

INSERT INTO `job` (`id`, `job_name`, `min_salary`, `max_salary`) VALUES
(1, 'System Administrator', 3000, 10000),
(2, 'Airline Manager', 3000, 10000),
(3, 'Human Resource', 3000, 10000),
(4, 'Pilot', 3000, 10000),
(5, 'Cabin Crew', 3000, 10000),
(6, 'Fleet Manager', 3000, 10000);

-- --------------------------------------------------------

--
-- Structure de la table `link_permission`
--

CREATE TABLE IF NOT EXISTS `link_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `menu_order` int(11) NOT NULL,
  `authorization` varchar(255) NOT NULL,
  `bar_link` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=58 ;

--
-- Contenu de la table `link_permission`
--

INSERT INTO `link_permission` (`id`, `name`, `url`, `menu_order`, `authorization`, `bar_link`) VALUES
(1, 'Homepage', 'home', 1, '*', '1'),
(2, 'Logout', 'logout', 20, '*', '1'),
(3, 'Add Employee', 'addEmployee', 0, '1;3', '1;5;3'),
(5, 'Employee Management', 'manageEmployee', 2, '1;3', '1;5'),
(6, 'Model Management', 'modelManagement', 4, '1;6', '1;6'),
(7, 'Add Employee Check', 'addEmployeeFormCheck', 0, '1;3', ''),
(8, 'Edit Employee', 'modifyEmployee', 0, '1;3', '1;3'),
(9, 'Create Aircraft Model', 'createModel', 0, '1;6', '1;6;9'),
(10, 'Create Aircraft Model Process', 'createModelProcess', 0, '1;6', ''),
(11, 'Edit Employee Check', 'modifyEmployeeFormCheck', 0, '1;3', ''),
(12, 'Delete Aircraft Model Process', 'deleteModelProcess', 0, '1;6', ''),
(13, 'Update Aircraft Model', 'updateModel', 0, '1;6', '1;6'),
(14, 'Update Aircraft Model Process', 'updateModelProcess', 0, '1;6', ''),
(15, 'Remove Employee', 'removeEmployee', 0, '1;3', ''),
(16, 'Team Management', 'manageTeam', 3, '1;3;6', '1;16'),
(17, 'Add Team', 'addTeam', 0, '1;3;6', '1;16;17'),
(18, 'Add Team Check', 'addTeamFormCheck', 0, '1;3;6', ''),
(19, 'Add Team Member', 'addTeamMember', 0, '1;3;6', ''),
(20, 'Airport Management', 'airportManagement', 5, '1;2', '1;20'),
(21, 'Add Airport', 'addAirport', 0, '1;2', '1;20;21'),
(22, 'Add Team Member Form Check', 'addTeamMemberFormCheck', 0, '1;3;6', ''),
(23, 'Add Airport Process', 'addAirportProcess', 0, '1;2', ''),
(24, 'Edit Team', 'modifyTeam', 0, '1;3;6', '1;16'),
(25, 'Delete Airport Process', 'deleteAirportProcess', 0, '1;2', ''),
(26, 'Edit Team Form Check', 'modifyTeamFormCheck', 0, '1;3;6', ''),
(27, 'Remove Team Member', 'removeTeamMember', 0, '1;3;6', ''),
(28, 'Remove Team', 'removeTeam', 0, '1;3;6', ''),
(29, 'Edit Airport', 'editAirport', 0, '1;2', ''),
(30, 'Edit Airport Process', 'editAirportProcess', 0, '1;2', ''),
(31, 'Flight Assigment', 'flightAssignment', 6, '4', '1;31'),
(32, 'PIREP', 'pirep', 7, '4', '1;32'),
(33, 'Maintenance Request', 'maintenanceRequest', 8, '4', '1;33'),
(34, 'Submit PIREP Process', 'sendPirepProcess', 0, '4', ''),
(35, 'Maintenance Request Process', 'maintenanceRequestProcess', 0, '4', ''),
(36, 'Add News', 'addNews', 0, '1', '1;36'),
(37, 'Add Advice', 'addAdvice', 0, '1', '1;37'),
(40, 'Aircraft Management', 'AircraftManagement', 9, '1;6', '1;40'),
(41, 'Add News Form Check', 'addNewsFormCheck', 0, '1', ''),
(42, 'Add Advice Form Check', 'addAdviceFormCheck', 0, '1', ''),
(43, 'Remove News', 'removeNews', 0, '1', ''),
(44, 'Create Aircraft', 'CreateAircraft', 0, '1;6', '1;40;44'),
(45, 'Create Aircraft Process', 'CreateAircraftProcess', 0, '1;6', ''),
(46, 'Delete Aircraft Process', 'DeleteAircraftProcess', 0, '1;6', ''),
(47, 'Update Aircraft', 'UpdateAircraft', 0, '1;6', '1;40;47'),
(48, 'Update Aircraft Process', 'UpdateAircraftProcess', 0, '1;6', ''),
(49, 'Airlines Management', 'manageFlight', 10, '1;2', '1;49'),
(50, 'Add Flight', 'addFlight', 0, '1;2', '1;49;50'),
(51, 'Add Flight Process', 'addFlightFormCheck', 0, '1;2', ''),
(52, 'Delete Flight Process', 'deleteFlight', 0, '1;2', ''),
(53, 'Edit Flight', 'updateFlight', 0, '1;2', '1;49;53'),
(54, 'Edit Flight Process', 'updateFlightFormCheck', 0, '1;2', ''),
(55, 'Aircraft Maintenance', 'Maintenance', 11, '1', '1;55'),
(56, 'Load Maintenance Data', 'MaintenanceData', 0, '1', ''),
(57, 'Maintenance Process', 'MaintenanceProcess', 0, '1;', '');

-- --------------------------------------------------------

--
-- Structure de la table `log`
--

CREATE TABLE IF NOT EXISTS `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `log_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `log_timestamp` (`log_timestamp`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `log`
--

INSERT INTO `log` (`id`, `account_id`, `log_timestamp`) VALUES
(1, 0, '2013-05-03 22:15:25'),
(2, 10, '2013-05-03 22:15:34');

-- --------------------------------------------------------

--
-- Structure de la table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `motd` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `news`
--

INSERT INTO `news` (`id`, `date`, `title`, `content`, `motd`) VALUES
(1, '2013-06-16 11:28:28', 'Advice', 'Work more to go home sooner !', 1),
(2, '2013-06-16 11:32:46', 'New Intranet', 'Dear all,<br />\r\n<br />\r\nHere is the new version of the intranet !<br />\r\n<br />\r\nLorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.<br />\r\nUt enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br />\r\nDuis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.<br />\r\nExcepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.<br />\r\n<br />\r\nHave a nice day,<br />\r\nAdmin Ranger.<br />', 0),
(4, '2013-06-16 12:26:33', 'Advice', 'Time is gold, so... work like crazy !', 1),
(5, '2013-06-16 22:11:19', 'Test', 'Dear all,<br /><br />This is a test.<br /><br />Have a nice day,<br />Admin Ranger.', 0),
(6, '2013-06-16 22:12:34', 'New Aicrafts !!', 'Dear all,<br /><br />We just brought 2 Airbus 360 on taobao !<br /><br />Cheers,<br />Admin Ranger.', 0),
(8, '2013-06-17 00:59:14', 'Advice', 'Yahallo~!!', 1),
(10, '2013-06-17 01:49:07', 'Advice', 'Work less to go home faster !', 1);

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `roles`
--

INSERT INTO `roles` (`id`, `role_name`) VALUES
(1, 'Administrator'),
(2, 'Normal User');

-- --------------------------------------------------------

--
-- Structure de la table `system_account`
--

CREATE TABLE IF NOT EXISTS `system_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_type` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `login` (`login`),
  KEY `role` (`role`),
  KEY `account_type` (`account_type`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=31 ;

--
-- Contenu de la table `system_account`
--

INSERT INTO `system_account` (`id`, `account_type`, `login`, `salt`, `password`, `role`, `last_login`) VALUES
(1, 1, 'admin', '880c484e9204868251bcf91c5364e12224ed8fd3babe0df62ca663df3df6a7eb222d92faa98011d27b9526da5737495a9055202e34710b0e590ead02507b2c18', 'be21e9f2a9ec1d7b871539a806b090f4b7a1e8c1625b2247bb5982d473deebc7c616a9ac2d6a8aeafaebf0a6e96244d751a83464d2290f944c03a47ad9e12106', 1, '2013-05-04 19:43:39'),
(7, 1, 'Loyd', '526e53afcd7158223b28bf9fa5ec495749f2a5ebd87a353af907208d65e29b716c62223e6e9efbdcb43a3ec36d4213aebf8b2306d1e2b80315a2a5cd87f78d5e', '06334452d5ef092eb0a15e453468368d63e384f4f70a35afd44de71a76fc6f8a977f87386f1855672c1d7d086f634dca4104a050be7e96ded1ee393d2a8f895e', 1, NULL),
(17, 1, 'pilot1', '91f2f15db17aa7738652c69438b54e61f9af732490a1a5bd1280ebc00630b56a655ed6292c0bbf568a0fd04593172d4fd2eb2b398f1e3939ce1c846b40e6e099', 'e33629bd720892d93b71d5976d92e7840a87da3cc45db02c4820388b92a60b1b28b001d9c603b70625baf4547a055b2f3218e34929165fbcad8adff55c37b5c6', 2, NULL),
(18, 1, 'pilot2', '07f60aefe4403ff65e1db8036f6c96b14080d624d5b8adb503713ebfd25575c371ad57c18bb91d31d54f14f681b061f596e96e9cc564bbac1cd2f967636ea032', 'ba08b72da3ed0dc811150c25c82503c1a812a161d06dc2d6dc8188b9f8fdcf7230dd2bf46aedc76d30592942a1360039f79d9f1a2479e0b38efcb57b1051acda', 2, NULL),
(19, 1, 'cabin11', 'a8de32a56216d5b374e88b45f530e3f4b7df3974660b06b1fbf7a011cb6e4d1cf2c53f5e2a49e1d878860c6e95252a5ec510503653f52da4cecf15a0b24f599c', 'e3c6547908516147751520f26f4cd60756196765d3125ba9dc1cd80bd69ef75fdb6d77bc57c875a8b92159ad41c825b0d49eedc2118adadbaca4bba978483a47', 2, NULL),
(20, 1, 'cabin12', '34efa04c9bd65dc23bda6b315ace33a80e819dfb764e0001cdc21dfaee0c704fb65b855501f80af6fd6bbdfb80998d6936752ceb3503d0874f86e0f361fd876e', 'cee8ecaae3381ddd8ef79007105057fce36b16276e60e8aefb9545d90170373a5adbf5a2e035e68160bc64c46f41ce19b173b3b43b25a322b29a02f701fa0c40', 2, NULL),
(21, 1, 'cabin21', 'e360e79dae666d680d76863f249433cf40b3749257d4bb3d1ddf37b0ecc850637151af82e6601976ad494a49e08f1256e60c192f6cd4edb0a7e57db8043599b5', 'ff64208edd0c9429255c60fbcf9d5740ca6ae330ad138ce766f251692da3c3c10260c8db36d1855978c25d01488dfdf2c27cd9535f70572de912139cf0378b35', 2, NULL),
(22, 1, 'cabin22', 'a8ae1203c2563c50574b817fc0b5bab68f3b491c777dfab511f21b4365be078a71976810eab5afafda6efcd1a6badb5cde647c0be00f7af7a100977c21f2c790', 'fb2b83723676e2d514f6cb08d0e2e031b8ef1a9fdeca15bb589513fb0b220830bcf45f3bd8638037883ca94166fa452f693e7a34bbfa0bfe2d42cd6f8f6bba1e', 2, NULL),
(24, 1, 'pilot', 'b2441eebfd136de65eee4591e019a64c8194de90502df4933b55bf1ec59da105e7c0d885100d20c1feda0bd7fd4a51352ab6d11e6517c051fc384cd8f6a26a62', 'd8f1a74ff93d10e080902c028b5f6aa9bea3a711418b4059ad03cace0e82e00ede78f7d077029eab9275145be0ee43985bae42ed1d3afa5d6a4361567ce1844a', 2, NULL),
(25, 1, 'T', '2d9aeca57af648d28fbb6211d74d242fc1f645ecdbf09d9cbcce511f61c7f1d5f5d9f45477340f707452cff34370fb2fa276b3b36dccbb807393f594d9524fc8', '5fe8a0a5bf63a440856373c23fe5e1e3a0a249849900ea32851d5ffbe289aad398bfc6f9a146b1b3dbac7683b3ea42b155e69b996ddefae301fea13bdcbfbb41', 2, NULL),
(27, 1, 'am', '427373d21942f83a3ce05ca257faf89a841bf82bc14ec0d3a9dcc3cec3167a78e54bbab04087ce7054273a00077e1955b7a588752e7265ba2977359c699cbf71', '56392f1316a473ec6d4d0b865e98755b0b4ac3016ca74d45e9cb054e2c3d6c0a65d013c8818651a8bf12ae1b94b2236dcc6f7d022c811aad5ce6676878cc741f', 2, NULL),
(28, 1, 'hr', 'bdc58d712d0dcf725c85792a19e04c87b68f5f1a984b50c98a258dc0514bedfa980cdedc01f0670ebecd86660e8c916c3ae5d1aca1fb3bccb2f7e9141ecb6297', 'b20c184468cb731e65c14f533440c6a571fb74a2c5a62b6e64eaa865f212797e4c0331e97f34a9de26f90b9758fc531be05fd4ac9d03cbd014c499f6f719d943', 2, NULL),
(29, 1, 'fm', '6726236f19cc3517e069c94afe88d879e982773726b9c0602800f53c0a46ac912b7a166cbb22ef1cdf206dffd24183f7f883265a52bc08d9c7931467841f7119', '37ab5135f5f162902fd1d9c1e3d2d332137230588b480f13574d488223fb17dc7e3c6d9322ef31e6b90b62793bb644ac4455ccf2fafa98e98b3431945ca46708', 2, NULL),
(30, 1, 'cabin', '454d09e19a73f0c773b274d5962540ea2b8f61429a9616cd95162796f22829606738ae4641844ec60f93751df524d3405605fa2178031520eba204db9648be09', '05b32a085c0a4762bec8d00b94c1bb8f99086469f90c28b02667b0c928c6430c3d64d22a9301c25b33ba5297ce8738b889ba7973c34910f37b2ea5b23d6192e4', 2, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `team`
--

CREATE TABLE IF NOT EXISTS `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(255) NOT NULL,
  `aircraft_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `team_name` (`team_name`),
  KEY `aircraft_id` (`aircraft_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Contenu de la table `team`
--

INSERT INTO `team` (`id`, `team_name`, `aircraft_id`) VALUES
(12, 'Dragon Flight', 3);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `aircraft`
--
ALTER TABLE `aircraft`
  ADD CONSTRAINT `aircraft_ibfk_1` FOREIGN KEY (`position`) REFERENCES `airport` (`icao`) ON UPDATE CASCADE,
  ADD CONSTRAINT `aircraft_ibfk_2` FOREIGN KEY (`model_id`) REFERENCES `aircraft_model` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `aircraft_ibfk_3` FOREIGN KEY (`assigned_flight`) REFERENCES `flightplan` (`flight_number`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `aircraft_model`
--
ALTER TABLE `aircraft_model`
  ADD CONSTRAINT `aircraft_model_ibfk_1` FOREIGN KEY (`aircraft_type`) REFERENCES `aircraft_type` (`id`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_11` FOREIGN KEY (`id`) REFERENCES `system_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_ibfk_10` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_ibfk_7` FOREIGN KEY (`hub`) REFERENCES `airport` (`icao`) ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_ibfk_8` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_ibfk_9` FOREIGN KEY (`position`) REFERENCES `airport` (`icao`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `flightplan`
--
ALTER TABLE `flightplan`
  ADD CONSTRAINT `flightplan_ibfk_1` FOREIGN KEY (`departure_airport`) REFERENCES `airport` (`icao`) ON UPDATE CASCADE,
  ADD CONSTRAINT `flightplan_ibfk_2` FOREIGN KEY (`arrival_airport`) REFERENCES `airport` (`icao`) ON UPDATE CASCADE,
  ADD CONSTRAINT `flightplan_ibfk_3` FOREIGN KEY (`flight_type`) REFERENCES `flight_type` (`id`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `system_account`
--
ALTER TABLE `system_account`
  ADD CONSTRAINT `system_account_ibfk_1` FOREIGN KEY (`role`) REFERENCES `roles` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `system_account_ibfk_2` FOREIGN KEY (`account_type`) REFERENCES `account_type` (`id`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `team`
--
ALTER TABLE `team`
  ADD CONSTRAINT `team_ibfk_1` FOREIGN KEY (`aircraft_id`) REFERENCES `aircraft` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

GRANT USAGE ON *.* TO 'c2_airline'@'localhost' IDENTIFIED BY PASSWORD '*BAFBE38686A3ADD198D39C7F49522CE6078D537D';

GRANT SELECT, INSERT, UPDATE, DELETE ON `c2\_airline`.* TO 'c2_airline'@'localhost';

