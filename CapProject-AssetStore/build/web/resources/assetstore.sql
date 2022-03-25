-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2022 at 12:58 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `assetstore`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `accountID` int(11) NOT NULL,
  `username` varchar(15) NOT NULL,
  `accountType` varchar(20) NOT NULL,
  `salt` varchar(50) NOT NULL,
  `hash` mediumtext CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`accountID`, `username`, `accountType`, `salt`, `hash`) VALUES
(9, 'Admin01', 'admin', 'M2CKOUDT4H', '9388ACFEBA499F5C200E8295AC9E9B611176D06C8CCE511B1589CEC4C5FE0654');

-- --------------------------------------------------------

--
-- Table structure for table `accountprofile`
--

CREATE TABLE `accountprofile` (
  `userID` int(11) NOT NULL,
  `accountID` int(11) NOT NULL,
  `profileID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accountprofile`
--

INSERT INTO `accountprofile` (`userID`, `accountID`, `profileID`) VALUES
(2, 9, 8);

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `postID` int(11) NOT NULL,
  `imageLink` varchar(255) NOT NULL,
  `caption` varchar(125) NOT NULL,
  `description` varchar(255) NOT NULL,
  `likes` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productID` int(11) NOT NULL,
  `asset` varchar(255) NOT NULL,
  `description` varchar(155) NOT NULL,
  `price` double NOT NULL,
  `purchased` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE `profile` (
  `profileID` int(11) NOT NULL,
  `profileName` varchar(15) NOT NULL,
  `email` varchar(15) NOT NULL,
  `profileCaption` varchar(100) DEFAULT NULL,
  `followerCount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`profileID`, `profileName`, `email`, `profileCaption`, `followerCount`) VALUES
(8, 'Admin01', 'admin@gmail.com', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `profilepost`
--

CREATE TABLE `profilepost` (
  `profileID` int(11) NOT NULL,
  `postID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `profileproduct`
--

CREATE TABLE `profileproduct` (
  `profileID` int(11) NOT NULL,
  `productID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`accountID`);

--
-- Indexes for table `accountprofile`
--
ALTER TABLE `accountprofile`
  ADD PRIMARY KEY (`userID`),
  ADD KEY `accountprofile_FK_1` (`profileID`),
  ADD KEY `accountprofile_FK_2` (`accountID`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`postID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`profileID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `accountID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `accountprofile`
--
ALTER TABLE `accountprofile`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `postID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
  MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accountprofile`
--
ALTER TABLE `accountprofile`
  ADD CONSTRAINT `accountprofile_FK_1` FOREIGN KEY (`profileID`) REFERENCES `profile` (`profileID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `accountprofile_FK_2` FOREIGN KEY (`accountID`) REFERENCES `account` (`accountID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
