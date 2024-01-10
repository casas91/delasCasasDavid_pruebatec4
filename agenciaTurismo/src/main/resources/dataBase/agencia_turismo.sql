-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-01-2024 a las 20:14:07
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agencia_turismo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book_flight`
--

CREATE TABLE `book_flight` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `flight_code` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `peopleq` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `id_flight` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `book_flight`
--

INSERT INTO `book_flight` (`id`, `date`, `destination`, `flight_code`, `origin`, `peopleq`, `price`, `seat_type`, `id_flight`) VALUES
(2, '2024-02-10', 'Miami', 'BAMI1235', 'Barcelona', 1, 650, 'Economy', 1),
(3, '2024-02-10', 'Madrid', 'MIMA1420', 'Miami', 2, 8640, 'Business', 2),
(4, '2024-01-31', 'Cartagena', 'CAME0321', 'Medellin', 2, 1560, 'Economy', 20),
(5, '2024-01-31', 'Cartagena', 'CAME0321', 'Medellin', 2, 1560, 'Economy', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book_flight_passengers`
--

CREATE TABLE `book_flight_passengers` (
  `book_flight_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `book_flight_passengers`
--

INSERT INTO `book_flight_passengers` (`book_flight_id`, `user_id`) VALUES
(2, 9),
(3, 10),
(3, 11),
(4, 12),
(4, 13),
(5, 14),
(5, 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book_hotel`
--

CREATE TABLE `book_hotel` (
  `id` bigint(20) NOT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `nights` int(11) NOT NULL,
  `peopleq` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotel_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `book_hotel`
--

INSERT INTO `book_hotel` (`id`, `date_from`, `date_to`, `hotel_code`, `nights`, `peopleq`, `price`, `room_type`, `hotel_id`) VALUES
(1, '2024-02-11', '2024-03-15', 'AR0002', 33, 2, 20790, 'double', 1),
(2, '2024-02-11', '2024-03-02', 'RC001', 20, 1, 10860, 'single', 2),
(3, '2024-03-01', '2024-03-15', 'MT003', 14, 2, 9828, 'double', 8),
(4, '2024-04-17', '2024-05-03', 'IR004', 16, 3, 14992, 'triple', 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `book_hotel_hosts`
--

CREATE TABLE `book_hotel_hosts` (
  `book_hotel_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `book_hotel_hosts`
--

INSERT INTO `book_hotel_hosts` (`book_hotel_id`, `user_id`) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 5),
(4, 6),
(4, 7),
(4, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

CREATE TABLE `flight` (
  `id` bigint(20) NOT NULL,
  `date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `flight_number` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`id`, `date`, `destination`, `flight_number`, `origin`, `price`, `seat_type`) VALUES
(1, '2024-02-10', 'Miami', 'BAMI1235', 'Barcelona', 650, 'Economy'),
(2, '2024-02-10', 'Madrid', 'MIMA1420', 'Miami', 4320, 'Business'),
(3, '2024-02-10', 'Madrid', 'MIMA1420', 'Miami', 2573, 'Economy'),
(4, '2024-02-10', 'BuenosAires', 'BABU5536', 'Barcelona', 732, 'Economy'),
(5, '2024-02-12', 'Barcelona', 'BUBA3369', 'BuenosAires', 1253, 'Business'),
(6, '2024-03-01', 'Barcelona', 'IGBA3369', 'Iguazu', 540, 'Economy'),
(7, '2024-01-23', 'Cartagena', 'BOCA4213', 'Bogota', 800, 'Economy'),
(8, '2024-01-23', 'Medellin', 'CAME0321', 'Cartagena', 780, 'Economy'),
(9, '2024-02-15', 'Iguazu', 'BOIG6567', 'Bogota', 570, 'Business'),
(10, '2024-03-01', 'BuenosAires', 'BOBA6567', 'Bogota', 398, 'Economy'),
(11, '2024-02-10', 'Madrid', 'BOMA4442', 'Bogota', 1100, 'Economy'),
(12, '2024-04-17', 'Miami', 'MEMI9986', 'Medellin', 1164, 'Business'),
(13, '2024-02-15', 'Barcelona', 'BAMI1235', 'Miami', 650, 'Economy'),
(14, '2024-02-20', 'Miami', 'MIMA1420', 'Madrid', 4320, 'Business'),
(15, '2024-02-21', 'Miami', 'MIMA1420', 'Madrid', 2573, 'Economy'),
(16, '2024-02-17', 'Barcelona', 'BABU5536', 'BuenosAires', 732, 'Economy'),
(17, '2024-02-23', 'BuenosAires', 'BABU5536', 'Barcelona', 1253, 'Business'),
(18, '2024-03-16', 'Iguazu', 'IGBA3369', 'Barcelona', 540, 'Economy'),
(19, '2024-02-05', 'Bogota', 'BOCA4213', 'Cartagena', 800, 'Economy'),
(20, '2024-01-31', 'Cartagena', 'CAME0321', 'Medellin', 780, 'Economy'),
(21, '2024-02-28', 'Bogota', 'BOIG6567', 'Iguazu', 570, 'Business'),
(22, '2024-03-14', 'Bogota', 'BOBA6567', 'BuenosAires', 398, 'Economy'),
(23, '2024-02-24', 'Bogota', 'BOMA4442', 'Madrid', 1100, 'Economy'),
(24, '2024-05-02', 'Medellin', 'MEMI9986', 'Miami', 1164, 'Business');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `disponibility_date_from` date DEFAULT NULL,
  `disponibility_date_to` date DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `is_booked` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `room_price` double DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`id`, `disponibility_date_from`, `disponibility_date_to`, `hotel_code`, `is_booked`, `name`, `place`, `room_price`, `room_type`) VALUES
(1, '2024-02-10', '2024-03-20', 'AR0002', b'1', 'Atlantis', 'Miami', 630, 'double'),
(2, '2024-02-10', '2024-03-19', 'RC001', b'1', 'Ritz-Carlton', 'BuenosAires', 543, 'single'),
(3, '2024-02-12', '2024-04-17', 'RC002', b'0', 'Ritz-Carlton2', 'Medellin', 720, 'double'),
(4, '2024-04-17', '2024-05-23', 'GH002', b'0', 'GrandHyatt', 'Madrid', 579, 'double'),
(5, '2024-05-17', '2024-06-23', 'GH001', b'0', 'GrandHyatt2', 'BuenosAires', 415, 'single'),
(6, '2024-02-17', '2024-03-23', 'HL001', b'0', 'Hilton', 'Barcelona', 390, 'single'),
(7, '2024-03-18', '2024-04-23', 'HL002', b'0', 'Hilton2', 'Barcelona', 584, 'double'),
(8, '2024-02-15', '2024-03-27', 'MT003', b'1', 'Marriot', 'Barcelona', 702, 'double'),
(9, '2024-03-01', '2024-04-17', 'SH004', b'0', 'Sheraton', 'Madrid', 860, 'triple'),
(10, '2024-02-10', '2024-03-20', 'SH003', b'0', 'Sheraton2', 'Iguazu', 520, 'single'),
(11, '2024-04-17', '2024-06-12', 'IR004', b'1', 'InterContinental', 'Cartagena', 937, 'triple');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `age` int(11) NOT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `passport` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `age`, `last_name`, `name`, `passport`) VALUES
(1, 20, 'Casas', 'David', '123454A'),
(2, 20, 'Cerezo', 'Marta', '123464A'),
(3, 35, 'Perez', 'Juan', '123484A'),
(4, 21, 'Mena', 'Ana', '127484A'),
(5, 26, 'Garcia', 'Joaquin', '1274884A'),
(6, 45, 'Lopez', 'Carlos', '176484A'),
(7, 48, 'Sevilla', 'Ernesto', '1964884A'),
(8, 51, 'Hache', 'Eva', '1734884A'),
(9, 25, 'Alamillos', 'Juan', '12354A'),
(10, 32, 'Pacheco', 'Carlos', '1235464A'),
(11, 41, 'Carmin', 'Juan', '1214464A'),
(12, 50, 'Armas', 'Lucia', '1237464A'),
(13, 54, 'Rodas', 'Ana', '964464A'),
(14, 38, 'Garcia', 'Carla', '1244464A'),
(15, 39, 'Casillas', 'Victor', '9676264A');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `book_flight`
--
ALTER TABLE `book_flight`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6x4k5gtip2jvyst478vxdj91a` (`id_flight`);

--
-- Indices de la tabla `book_flight_passengers`
--
ALTER TABLE `book_flight_passengers`
  ADD KEY `FK4nxcwp2ky7vct2exaijowwkc6` (`user_id`),
  ADD KEY `FKgf4ffeq5x98kmcgstfdp4exgk` (`book_flight_id`);

--
-- Indices de la tabla `book_hotel`
--
ALTER TABLE `book_hotel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmdalu2ngu5lpja2llhgd5wray` (`hotel_id`);

--
-- Indices de la tabla `book_hotel_hosts`
--
ALTER TABLE `book_hotel_hosts`
  ADD KEY `FKprt7wga7vhv2q5a1wm4jgwabi` (`user_id`),
  ADD KEY `FKbfjpjsypeag3mwrtorcnkhrnj` (`book_hotel_id`);

--
-- Indices de la tabla `flight`
--
ALTER TABLE `flight`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `book_flight`
--
ALTER TABLE `book_flight`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `book_hotel`
--
ALTER TABLE `book_hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `flight`
--
ALTER TABLE `flight`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `hotel`
--
ALTER TABLE `hotel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `book_flight`
--
ALTER TABLE `book_flight`
  ADD CONSTRAINT `FK6x4k5gtip2jvyst478vxdj91a` FOREIGN KEY (`id_flight`) REFERENCES `flight` (`id`);

--
-- Filtros para la tabla `book_flight_passengers`
--
ALTER TABLE `book_flight_passengers`
  ADD CONSTRAINT `FK4nxcwp2ky7vct2exaijowwkc6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKgf4ffeq5x98kmcgstfdp4exgk` FOREIGN KEY (`book_flight_id`) REFERENCES `book_flight` (`id`);

--
-- Filtros para la tabla `book_hotel`
--
ALTER TABLE `book_hotel`
  ADD CONSTRAINT `FKmdalu2ngu5lpja2llhgd5wray` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`);

--
-- Filtros para la tabla `book_hotel_hosts`
--
ALTER TABLE `book_hotel_hosts`
  ADD CONSTRAINT `FKbfjpjsypeag3mwrtorcnkhrnj` FOREIGN KEY (`book_hotel_id`) REFERENCES `book_hotel` (`id`),
  ADD CONSTRAINT `FKprt7wga7vhv2q5a1wm4jgwabi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
