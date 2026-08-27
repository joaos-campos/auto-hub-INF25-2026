-- -----------------------------------------------------
-- Schema DB_AAH
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `DB_AAH` DEFAULT CHARACTER SET utf8 ;
USE `DB_AAH` ;

-- -----------------------------------------------------
-- Table T_AAH_PROPRIETARIO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_PROPRIETARIO` 
(
  `id_proprietario` INT NOT NULL AUTO_INCREMENT,
  `nm_proprietario` VARCHAR(100) NOT NULL,
  `dt_nascimento` DATE NOT NULL,
  `nr_cpf` CHAR(14) NOT NULL,
  `ds_email` VARCHAR(120) NOT NULL,
  `nr_telefone` CHAR(14) NOT NULL,
  `ds_senha` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`id_proprietario`),
  UNIQUE INDEX `UN_PROPRIETARIO_NR_CPF` (`nr_cpf` ASC),
  UNIQUE INDEX `UN_PROPRIETARIO_DS_EMAIL` (`ds_email` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_MARCA
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_MARCA` 
(
  `id_marca` INT NOT NULL AUTO_INCREMENT,
  `nm_marca` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_marca`),
  UNIQUE INDEX `UN_MARCA_DS_MARCA` (`nm_marca` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_VEICULO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_VEICULO` 
(
  `id_veiculo` INT NOT NULL AUTO_INCREMENT,
  `id_marca` INT NOT NULL,
  `ds_modelo` VARCHAR(80) NOT NULL,
  `nr_ano` INT NOT NULL,
  `ds_placa` CHAR(7) NOT NULL,
  `ds_cor` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_veiculo`),
  INDEX `IDX_FK_MARCA_VEICULO` (`id_marca` ASC),
  CONSTRAINT `FK_MARCA_VEICULO`
    FOREIGN KEY (`id_marca`)
    REFERENCES `DB_AAH`.`T_AAH_MARCA` (`id_marca`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  UNIQUE INDEX `UN_VEICULO_DS_PLACA` (`ds_placa` ASC),
  CONSTRAINT `CK_VEICULO_NR_ANO` CHECK (`nr_ano` > 1900 AND `nr_ano` < (YEAR(CURDATE())+1))
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_PRESTADOR
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_PRESTADOR` 
(
  `id_prestador` INT NOT NULL AUTO_INCREMENT,
  `nm_fantasia` VARCHAR(150) NOT NULL,
  `nr_cnpj` VARCHAR(20) NOT NULL,
  `nr_telefone` CHAR(14) NOT NULL,
  `ds_senha` VARCHAR(40) NOT NULL,
  `ds_email` VARCHAR(120) NOT NULL,
  PRIMARY KEY (`id_prestador`),
  UNIQUE INDEX `UN_PRESTADOR_NR_CNPJ` (`nr_cnpj` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_ESTADO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_ESTADO` 
(
  `id_estado` INT NOT NULL AUTO_INCREMENT,
  `nm_estado` VARCHAR(60) NOT NULL,
  `sg_estado` CHAR(2) NOT NULL,
  PRIMARY KEY (`id_estado`),
  UNIQUE INDEX `UN_ESTADO_NM_ESTADO` (`nm_estado` ASC),
  UNIQUE INDEX `UN_ESTADO_SG_ESTADO` (`sg_estado` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_CIDADE
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_CIDADE` 
(
  `id_cidade` INT NOT NULL AUTO_INCREMENT,
  `id_estado` INT NOT NULL,
  `cd_cidade_ibge` CHAR(7) NOT NULL,
  `nm_cidade` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_cidade`),
  INDEX `IDX_FK_T_ESTADO_T_CIDADE` (`id_estado` ASC),
  CONSTRAINT `FK_T_ESTADO_T_CIDADE`
    FOREIGN KEY (`id_estado`)
    REFERENCES `DB_AAH`.`T_AAH_ESTADO` (`id_estado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  UNIQUE INDEX `UN_CIDADE_CD_CIDADE_IBGE` (`cd_cidade_ibge` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_BAIRRO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_BAIRRO` 
(
  `id_bairro` INT NOT NULL AUTO_INCREMENT,
  `id_cidade` INT NOT NULL,
  `nm_bairro` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_bairro`),
  INDEX `IDX_FK_T_CIDADE_T_BAIRRO` (`id_cidade` ASC),
  CONSTRAINT `fk_T_AAH_BAIRRO_T_AAH_CIDADE1`
    FOREIGN KEY (`id_cidade`)
    REFERENCES `mydb`.`T_AAH_CIDADE` (`id_cidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_LOGRADOURO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_LOGRADOURO` (
  `id_logradouro` INT NOT NULL AUTO_INCREMENT,
  `id_bairro` INT NOT NULL,
  `nr_cep` CHAR(9) NOT NULL,
  `tp_logradouro` CHAR(3) NOT NULL,
  `ds_logradouro` VARCHAR(180) NOT NULL,
  PRIMARY KEY (`id_logradouro`),
  INDEX `fk_T_AAH_LOGRADOURO_T_AAH_BAIRRO1_idx` (`id_bairro` ASC),
  CONSTRAINT `fk_T_AAH_LOGRADOURO_T_AAH_BAIRRO1`
    FOREIGN KEY (`id_bairro`)
    REFERENCES `mydb`.`T_AAH_BAIRRO` (`id_bairro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_ENDERECO_PROPRIETARIO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_ENDERECO_PROPRIETARIO` (
  `id_endereco_proprietario` INT NOT NULL AUTO_INCREMENT,
  `id_proprietario` INT NOT NULL,
  `id_logradouro` INT NOT NULL,
  `nr_endereco` INT NOT NULL,
  `ob_endereco` VARCHAR(180) NULL,
  PRIMARY KEY (`id_endereco_proprietario`),
  INDEX `fk_T_AAH_ENDERECO_T_AAH_LOGRADOURO1_idx` (`id_logradouro` ASC),
  INDEX `fk_T_AAH_ENDERECO_PROPRIETARIO_T_AAH_PROPRIETARIO1_idx` (`id_proprietario` ASC),
  CONSTRAINT `fk_T_AAH_ENDERECO_T_AAH_LOGRADOURO1`
    FOREIGN KEY (`id_logradouro`)
    REFERENCES `mydb`.`T_AAH_LOGRADOURO` (`id_logradouro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_AAH_ENDERECO_PROPRIETARIO_T_AAH_PROPRIETARIO1`
    FOREIGN KEY (`id_proprietario`)
    REFERENCES `mydb`.`T_AAH_PROPRIETARIO` (`id_proprietario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_PROPRIETARIO_VEICULO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_PROPRIETARIO_VEICULO` (
  `id_propriedade` INT NOT NULL AUTO_INCREMENT,
  `id_proprietario` INT NOT NULL,
  `id_veiculo` INT NOT NULL,
  `dt_inicio` DATE NOT NULL,
  `dt_fim` DATE NULL,
  `st_status` TINYINT NOT NULL,
  PRIMARY KEY (`id_propriedade`),
  INDEX `fk_T_PROPRIETARIO_VEICULO_T_AAH_VEICULO1_idx` (`id_veiculo` ASC),
  CONSTRAINT `fk_T_PROPRIETARIO_VEICULO_T_AAH_PROPRIETARIO1`
    FOREIGN KEY (`id_proprietario`)
    REFERENCES `mydb`.`T_AAH_PROPRIETARIO` (`id_proprietario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_PROPRIETARIO_VEICULO_T_AAH_VEICULO1`
    FOREIGN KEY (`id_veiculo`)
    REFERENCES `mydb`.`T_AAH_VEICULO` (`id_veiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_AGENDAMENTO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_AGENDAMENTO` (
  `id_agendamento` INT NOT NULL AUTO_INCREMENT,
  `id_propriedade` INT NOT NULL,
  `id_prestador` INT NOT NULL,
  `dt_agendamento` DATETIME NOT NULL,
  `dt_inclusao` DATETIME NOT NULL,
  `ds_status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id_agendamento`),
  INDEX `fk_T_AAH_AGENDAMENTO_T_PROPRIETARIO_VEICULO1_idx` (`id_propriedade` ASC),
  INDEX `fk_T_AAH_AGENDAMENTO_T_AAH_PRESTADOR1_idx` (`id_prestador` ASC),
  CONSTRAINT `fk_T_AAH_AGENDAMENTO_T_PROPRIETARIO_VEICULO1`
    FOREIGN KEY (`id_propriedade`)
    REFERENCES `mydb`.`T_AAH_PROPRIETARIO_VEICULO` (`id_propriedade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_AAH_AGENDAMENTO_T_AAH_PRESTADOR1`
    FOREIGN KEY (`id_prestador`)
    REFERENCES `mydb`.`T_AAH_PRESTADOR` (`id_prestador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_VEICULO_INTERCORRENCIAS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_VEICULO_INTERCORRENCIAS` (
  `id_intercorrencia` INT NOT NULL AUTO_INCREMENT,
  `id_veiculo` INT NOT NULL,
  `ds_intercorrencia` TEXT NOT NULL,
  `dt_intercorrencia` DATE NOT NULL,
  `vl_hodometro` DECIMAL(8,1) NOT NULL,
  PRIMARY KEY (`id_intercorrencia`),
  INDEX `fk_T_VEICULO_INTERCORRENCIAS_T_AAH_VEICULO1_idx` (`id_veiculo` ASC),
  CONSTRAINT `fk_T_VEICULO_INTERCORRENCIAS_T_AAH_VEICULO1`
    FOREIGN KEY (`id_veiculo`)
    REFERENCES `mydb`.`T_AAH_VEICULO` (`id_veiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_ATENDIMENTO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_ATENDIMENTO` (
  `id_atendimento` INT NOT NULL AUTO_INCREMENT,
  `id_agendamento` INT UNSIGNED NOT NULL,
  `vl_hodometro_veiculo` DECIMAL(8,1) NOT NULL,
  `dt_inicio` DATE NOT NULL,
  `dt_fim` DATE NULL,
  `ds_atendimento` TEXT NOT NULL,
  PRIMARY KEY (`id_atendimento`),
  INDEX `fk_T_AAH_ATENDIMENTO_T_AAH_AGENDAMENTO1_idx` (`id_agendamento` ASC),
  CONSTRAINT `fk_T_AAH_ATENDIMENTO_T_AAH_AGENDAMENTO1`
    FOREIGN KEY (`id_agendamento`)
    REFERENCES `mydb`.`T_AAH_AGENDAMENTO` (`id_agendamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_SERVICO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_SERVICO` (
  `id_servico` INT NOT NULL AUTO_INCREMENT,
  `id_atendimento` INT NOT NULL,
  `ds_servico` TEXT NOT NULL,
  `vl_servico` DECIMAL(9,2) NOT NULL,
  `vl_horas_gastas` SMALLINT NOT NULL,
  PRIMARY KEY (`id_servico`),
  INDEX `fk_T_SERVICO_T_AAH_ATENDIMENTO1_idx` (`id_atendimento` ASC),
  CONSTRAINT `fk_T_SERVICO_T_AAH_ATENDIMENTO1`
    FOREIGN KEY (`id_atendimento`)
    REFERENCES `mydb`.`T_AAH_ATENDIMENTO` (`id_atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_AVALIACAO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_AVALIACAO` (
  `id_avaliacao` INT NOT NULL AUTO_INCREMENT,
  `id_atendimento` INT NOT NULL,
  `id_proprietario` INT NOT NULL,
  `vl_avaliacao` TINYINT(1) NOT NULL,
  `ds_avaliacao` TEXT NULL,
  `dt_avaliacao` DATETIME NOT NULL,
  PRIMARY KEY (`id_avaliacao`),
  INDEX `fk_T_AAH_AVALIACAO_T_AAH_ATENDIMENTO1_idx` (`id_atendimento` ASC),
  INDEX `fk_T_AAH_AVALIACAO_T_AAH_PROPRIETARIO1_idx` (`id_proprietario` ASC),
  CONSTRAINT `fk_T_AAH_AVALIACAO_T_AAH_ATENDIMENTO1`
    FOREIGN KEY (`id_atendimento`)
    REFERENCES `mydb`.`T_AAH_ATENDIMENTO` (`id_atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_AAH_AVALIACAO_T_AAH_PROPRIETARIO1`
    FOREIGN KEY (`id_proprietario`)
    REFERENCES `mydb`.`T_AAH_PROPRIETARIO` (`id_proprietario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_ENDERECO_PRESTADOR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_ENDERECO_PRESTADOR` (
  `id_endereco_prestador` INT NOT NULL AUTO_INCREMENT,
  `id_prestador` INT NOT NULL,
  `id_logradouro` INT NOT NULL,
  `nr_endereco` INT NOT NULL,
  `ob_endereco` VARCHAR(180) NULL,
  PRIMARY KEY (`id_endereco_prestador`),
  INDEX `fk_T_AAH_ENDERECO_T_AAH_LOGRADOURO1_idx` (`id_logradouro` ASC),
  INDEX `fk_T_AAH_ENDERECO_PRESTADOR_T_AAH_PRESTADOR1_idx` (`id_prestador` ASC),
  CONSTRAINT `fk_T_AAH_ENDERECO_T_AAH_LOGRADOURO10`
    FOREIGN KEY (`id_logradouro`)
    REFERENCES `mydb`.`T_AAH_LOGRADOURO` (`id_logradouro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_AAH_ENDERECO_PRESTADOR_T_AAH_PRESTADOR1`
    FOREIGN KEY (`id_prestador`)
    REFERENCES `mydb`.`T_AAH_PRESTADOR` (`id_prestador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_AAH_STATUS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_AAH_STATUS` (
  `id_status` INT NOT NULL AUTO_INCREMENT,
  `st_status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id_status`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`T_STATUS_ATENDIMENTO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`T_STATUS_ATENDIMENTO` (
  `dt_atualizacao` DATETIME NOT NULL,
  `id_atendimento` INT NOT NULL,
  `id_status` INT NOT NULL,
  `ds_observacao` TEXT NULL,
  PRIMARY KEY (`dt_atualizacao`, `id_atendimento`, `id_status`),
  INDEX `fk_T_STATUS_ATENDIMENTO_T_AAH_ATENDIMENTO1_idx` (`id_atendimento` ASC),
  CONSTRAINT `PF_STATUS_A_STATUS_ATENDIMENTO`
    FOREIGN KEY (`id_status`)
    REFERENCES `mydb`.`T_AAH_STATUS` (`id_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PF_ATENDIMENTO_A_STATUS_ATENDIMENTO`
    FOREIGN KEY (`id_atendimento`)
    REFERENCES `mydb`.`T_AAH_ATENDIMENTO` (`id_atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
