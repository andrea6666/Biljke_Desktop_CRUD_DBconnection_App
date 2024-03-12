-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 
-- Generation Time: Feb 28, 2024 at 12:11 AM
-- Server version: 8.0.31
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biljke`
--

-- --------------------------------------------------------

--
-- Table structure for table `table_biljke`
--

DROP TABLE IF EXISTS `table_biljke`;
CREATE TABLE IF NOT EXISTS `table_biljke` (
  `RB` int NOT NULL AUTO_INCREMENT,
  `ime_biljke` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `datum_poslednjeg_zalivanja` date NOT NULL,
  `datum_sledeceg_zalivanja` date NOT NULL,
  `br_dana_na_koji_se_zaliva` int NOT NULL,
  `uputstvo` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `slika` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`RB`),
  UNIQUE KEY `ime_biljke` (`ime_biljke`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `table_biljke`
--

