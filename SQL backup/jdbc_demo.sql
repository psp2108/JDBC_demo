DROP DATABASE IF EXISTS jdbc_demo;

CREATE DATABASE jdbc_demo;

USE jdbc_demo;

-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 12, 2021 at 10:34 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jdbc_demo`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `cal_sum` (IN `val1` INT, IN `val2` INT, OUT `ans` INT)  begin

set ans = val1 + val2;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `display_students_with_departments` ()  begin

select 
stud_id, name, phone, final_cgpi, dept_name from 
student 
inner join 
department 
on 
student.dept = department.dept_id;

end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `do_nothing` ()  BEGIN
	select "This is a do nothhing procedure";
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_student_department` (IN `var_name` VARCHAR(100), IN `var_phone` VARCHAR(10), IN `var_final_cgpi` FLOAT, IN `var_dept` VARCHAR(100))  BEGIN
	
    declare var_dept_id int;
    select dept_id into var_dept_id from department where dept_name = var_dept;
    
    if ISNULL(var_dept_id) THEN
    	insert into department (dept_name) values (var_dept);
        select dept_id into var_dept_id from department where dept_name = var_dept;
    end if;
    
    INSERT INTO student (name, phone, final_cgpi, dept) 
    VALUES (var_name,var_phone,var_final_cgpi,var_dept_id);
        
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `just_echo` (IN `data` VARCHAR(50))  begin

select concat("Text passed is: ", data);

end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `blob_and_clob`
--

CREATE TABLE `blob_and_clob` (
  `id` int(11) NOT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `binary_file` blob DEFAULT NULL,
  `text_file` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `dept_id` int(11) NOT NULL,
  `Dept_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`dept_id`, `Dept_name`) VALUES
(100, 'Computer'),
(101, 'IT'),
(102, 'Mechanical');

-- --------------------------------------------------------

--
-- Table structure for table `jdbc_bank`
--

CREATE TABLE `jdbc_bank` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `Balance` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jdbc_bank`
--

INSERT INTO `jdbc_bank` (`id`, `name`, `Balance`) VALUES
(5000, 'Vijai Sujeet', 23123),
(5001, 'Vasudha Mangal', 25000),
(5002, 'Siddhartha Jha', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `stud_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `final_cgpi` float NOT NULL,
  `dept` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`stud_id`, `name`, `phone`, `final_cgpi`, `dept`) VALUES
(9001, 'Hardeep Suksma', '9844580501', 8.1, 100),
(9002, 'Barsati Sandipa', '8896512045', 10, 100),
(9003, 'Vijai Sritharan', '7854123602', 7.45, 101),
(9004, 'Aditi Musunur', '8956410257', 7, 101),
(9005, 'Jayadev Mitali', '9956455325', 7.86, 101);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blob_and_clob`
--
ALTER TABLE `blob_and_clob`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`dept_id`),
  ADD UNIQUE KEY `Dept_name` (`Dept_name`);

--
-- Indexes for table `jdbc_bank`
--
ALTER TABLE `jdbc_bank`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`stud_id`),
  ADD KEY `dept` (`dept`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blob_and_clob`
--
ALTER TABLE `blob_and_clob`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `department`
--
ALTER TABLE `department`
  MODIFY `dept_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT for table `jdbc_bank`
--
ALTER TABLE `jdbc_bank`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5003;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `stud_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9006;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`dept`) REFERENCES `department` (`dept_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
