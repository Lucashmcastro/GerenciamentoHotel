SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema Hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Hotel` DEFAULT CHARACTER SET utf8;
USE `Hotel`;

-- -----------------------------------------------------ok
-- Table `Hotel`.`HOSPEDE`
-- -----------------------------------------------------ok
DROP TABLE IF EXISTS `Hotel`.`HOSPEDE`;

CREATE TABLE IF NOT EXISTS `Hotel`.`HOSPEDE` ( 
  `CPFHospede` VARCHAR(11) NOT NULL,
  `NomeHospede` VARCHAR(255) NOT NULL,
  `TelefoneHospede` VARCHAR(20) NOT NULL,
  `EnderecoHospede` VARCHAR(255) NOT NULL,
  `SexoHospede` VARCHAR(20) NOT NULL,
  `DataNascHospede` DATE NOT NULL,
  PRIMARY KEY (`CPFHospede`));
  


-- -----------------------------------------------------ok
-- Table `Hotel`.`RESERVA`
-- -----------------------------------------------------ok
DROP TABLE IF EXISTS `Hotel`.`RESERVA` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`RESERVA` (
  `CodReserva` VARCHAR(11) NOT NULL,
  `CPFHospede` VARCHAR(11) NOT NULL,
  `CPFFuncionario` VARCHAR(11) NOT NULL,
  `CodQuarto` VARCHAR(10) NOT NULL,
  `DataEntrada`DATE NOT NULL,
  `DataSaida` DATE NOT NULL, 
  `StatusReserva` VARCHAR(50) NOT NULL,
  `DataHoraAddReserva`TIMESTAMP NOT NULL,
  PRIMARY KEY (`CodReserva`));

-- -----------------------------------------------------ok
-- Table `Hotel`.`FUNCIONARIO`
-- -----------------------------------------------------ok
DROP TABLE IF EXISTS `Hotel`.`FUNCIONARIO` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`FUNCIONARIO` (
  `CPFFuncionario` VARCHAR(11) NOT NULL,
  `NomeFuncionario` VARCHAR(255) NOT NULL,
  `TelefoneFuncionario` VARCHAR(25) NOT NULL,
  `EnderecoFuncionario` VARCHAR(255) NOT NULL,
  `SalarioFuncionario` DOUBLE (10,2) NOT NULL,
  `TipoFuncionario` CHAR(30) NOT NULL, 
  `SexoFuncionario` CHAR(20) NOT NULL,
  `LoginUsuario` VARCHAR(20) NULL, 
  `SenhaUsuario` VARCHAR(20) NULL, 
  `DataNascimentoFuncionario` DATE NOT NULL,
  `CPFSupervisor` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`CPFFuncionario`));


-- -----------------------------------------------------ok
-- Table `Hotel`.`QUARTO`
-- -----------------------------------------------------ok
DROP TABLE IF EXISTS `hotel`.`quarto`;

CREATE TABLE IF NOT EXISTS `Hotel`.`QUARTO` (
  `CodQuarto` VARCHAR(10) NOT NULL,
  `StatusQuarto` CHAR(15) NOT NULL,
  `TipoQuarto` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`CodQuarto`));


-- -----------------------------------------------------
-- Table `Hotel`.`SERVICO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`SERVICO` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`SERVICO` (
  `CodServico` INT NOT NULL AUTO_INCREMENT,
  `TipoServico` VARCHAR(50) NOT NULL,
  `Descricao` VARCHAR(255) NOT NULL,
  `valorServico` DOUBLE (10,2) NOT NULL,
  `CPFFuncionario` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`CodServico`));

-- -----------------------------------------------------
-- Table `Hotel`.`HOSPEDAGEMSERVICO`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`HOSPEDAGEMSERVICO` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`HOSPEDAGEMSERVICO` (
  `CodHospedagem` int NOT NULL auto_increment,
  `CodReserva` VARCHAR(10) NOT NULL,
  `CodServico` VARCHAR(10) NOT NULL,
  `valorTotalServico` DOUBLE (10,2) NOT NULL,
  PRIMARY KEY (`CodHospedagem`));

-- -----------------------------------------------------
-- Table `Hotel`.`HOSPEDAGEMDIARIA`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`HOSPEDAGEMDIARIA` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`HOSPEDAGEMDIARIA` (
  `CodHospedagem` int NOT NULL auto_increment,
  `CodReserva` VARCHAR(10) NOT NULL,
  `valorDiaria` DOUBLE (10,2) NOT NULL,
  PRIMARY KEY (`CodHospedagem`));


-- -----------------------------------------------------
-- Table `Hotel`.`DEPENDENTE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`DEPENDENTE` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`DEPENDENTE` (
  `CPFHospede` VARCHAR(11) NOT NULL,
  `NomeDependente` VARCHAR(255) NOT NULL,
  `ParentescoDependente` VARCHAR(50) NULL,
  `SexoDependente` CHAR(20) NOT NULL,
  `DataNascimentoDependente` DATE NOT NULL,
  PRIMARY KEY (`NomeDependente`, `CPFHospede`));
  
  
-- Código para definir Foreign Keys tabela RESERVA -- 

alter table reserva add foreign key (CPFHospede) references hospede(CPFHospede);
alter table reserva add foreign key (CPFFuncionario) references funcionario(CPFFuncionario);
alter table reserva add foreign key (CodQuarto) references quarto(CodQuarto);

-- Código para definir Foreign Keys tabela FUNCIONARIO --

alter table funcionario add foreign key (CPFSupervisor) references funcionario(CPFFuncionario);

-- Código para definir Foreign Keys tabela HOSPEDAGEMSERVIÇO --

alter table hospedagemservico add foreign key (CodReserva) references reserva(CodReserva);

-- Código para definir Foreign Keys tabela HOSPEDAGEMDIARIA --

alter table hospedagemdiaria add foreign key (CodReserva) references reserva(CodReserva);

-- Código para definir Foreign Keys tabela SERVICO --

alter table servico add foreign key (CPFFuncionario) references funcionario(CPFFuncionario);



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;