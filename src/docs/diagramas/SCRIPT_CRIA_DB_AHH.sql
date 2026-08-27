-- -----------------------------------------------------
-- Schema DB_AAH
-- -----------------------------------------------------
-- drop database `DB_AAH`;

CREATE SCHEMA IF NOT EXISTS `DB_AAH` DEFAULT CHARACTER SET utf8;
USE `DB_AAH`;

-- -----------------------------------------------------
-- Table T_AAH_PROPRIETARIO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_PROPRIETARIO` 
(
  `id_proprietario` INT NOT NULL AUTO_INCREMENT,
  `nm_proprietario` VARCHAR(160) NOT NULL,
  `dt_nascimento` DATE NOT NULL,
  `nr_cpf` CHAR(14) NOT NULL,
  `ds_email` VARCHAR(120) NOT NULL,
  `nr_telefone` CHAR(14) NOT NULL,
  `ds_nome_usuario` VARCHAR(50) NOT NULL UNIQUE,
  -- 23/06/2026 - Adição do campo para armazenar o nome de usuário utilizado para login pelo proprietário
  `ds_senha` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`id_proprietario`),
  UNIQUE INDEX `UN_PROPRIETARIO_NR_CPF` (`nr_cpf` ASC),
  UNIQUE INDEX `UN_PROPRIETARIO_DS_EMAIL` (`ds_email` ASC),
  CONSTRAINT `CK_PROPRIETARIO_DT_NASCIMENTO` CHECK (`dt_nascimento` >= '1930-01-01')
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
  `nr_ano` YEAR NOT NULL,
  `nr_ano_modelo` YEAR NOT NULL,
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
  CONSTRAINT `CK_VEICULO_NR_ANO` CHECK (`nr_ano` >= 1930 AND `nr_ano`),
  CONSTRAINT `CK_VEICULO_NR_ANO_MODELO` CHECK (`nr_ano_modelo` > 1900 AND `nr_ano_modelo` >= `nr_ano`)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_PRESTADOR
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_PRESTADOR` 
(
  `id_prestador` INT NOT NULL AUTO_INCREMENT,
  `nm_fantasia` VARCHAR(160) NOT NULL,
  `nr_cnpj` VARCHAR(20) NOT NULL,
  `nr_telefone` CHAR(14) NOT NULL,
  `ds_nome_usuario` VARCHAR(50) NOT NULL UNIQUE,
  -- 23/06/2026 - Adição do campo para armazenar o nome de usuário utilizado para login pelo proprietário
  `ds_senha` VARCHAR(80) NOT NULL,
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
-- Table T_AAH_BAIRRO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_BAIRRO` 
(
  `id_bairro` INT NOT NULL AUTO_INCREMENT,
  `id_cidade` INT NOT NULL,
  `nm_bairro` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_bairro`),
  INDEX `IDX_FK_CIDADE_BAIRRO` (`id_cidade` ASC),
  CONSTRAINT `FK_AAH_CIDADE_BAIRRO`
    FOREIGN KEY (`id_cidade`)
    REFERENCES `DB_AAH`.`T_AAH_CIDADE` (`id_cidade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_LOGRADOURO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_LOGRADOURO` 
(
  `id_logradouro` INT NOT NULL AUTO_INCREMENT,
  `id_bairro` INT NOT NULL,
  `nr_cep` CHAR(9) NOT NULL,
  `tp_logradouro` CHAR(3) NOT NULL DEFAULT 'RUA',
  `ds_logradouro` VARCHAR(180) NOT NULL,
  PRIMARY KEY (`id_logradouro`),
  INDEX `IDX_FK_BAIRRO_LOGRADOURO` (`id_bairro` ASC),
  CONSTRAINT `FK_BAIRRO_LOGRADOURO`
    FOREIGN KEY (`id_bairro`)
    REFERENCES `DB_AAH`.`T_AAH_BAIRRO` (`id_bairro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_LOGRADOURO_TP_LOGRADOURO` CHECK (`tp_logradouro` IN ('RUA', 'AVN', 'ALM', 'PRC', 'TRV', 'EST', 'VIE', 'ROD', 'BEC', 'LRG'))
-- Rua, Avenida (AVN), Alameda (AlM), Praça (PRC), Travessa (TRV), Estrada (EST), Viela (VIE), Rodovia (ROD), Beco (BEC), Largo (LRG)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_ENDERECO_PROPRIETARIO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_ENDERECO_PROPRIETARIO` 
(
  `id_endereco_proprietario` INT NOT NULL AUTO_INCREMENT,
  `id_proprietario` INT NOT NULL,
  `id_logradouro` INT NOT NULL,
  `nr_endereco` INT NOT NULL,
  `ob_endereco` VARCHAR(180) NULL,
  PRIMARY KEY (`id_endereco_proprietario`),
  INDEX `IDX_FK_LOGRADOURO_ENDERECO_PROPRIETARIO` (`id_logradouro` ASC),
  INDEX `IDX_FK_PROPRIETARIO_ENDERECO_PROPRIETARIO` (`id_proprietario` ASC),
  CONSTRAINT `FK_LOGRADOURO_ENDERECO_PROPRIETARIO`
    FOREIGN KEY (`id_logradouro`)
    REFERENCES `DB_AAH`.`T_AAH_LOGRADOURO` (`id_logradouro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROPRIETARIO_ENDERECO_PROPRIETARIO`
    FOREIGN KEY (`id_proprietario`)
    REFERENCES `DB_AAH`.`T_AAH_PROPRIETARIO` (`id_proprietario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_ENDERECO_PROPRIETARIO_NR_ENDERECO` CHECK (`nr_endereco` > 0 AND `nr_endereco` < 1000000)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_PROPRIETARIO_VEICULO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_PROPRIETARIO_VEICULO` 
(
  `id_propriedade` INT NOT NULL AUTO_INCREMENT,
  `id_proprietario` INT NOT NULL,
  `id_veiculo` INT NOT NULL,
  `dt_inicio` DATE NOT NULL,
  `dt_fim` DATE NULL,
  `ds_status` CHAR(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_propriedade`),
  INDEX `IDX_FK_PROPRIETARIO_PROPRIETARIO_VEICULO` (`id_proprietario` ASC),
  INDEX `IDX_FK_VEICULO_PROPRIETARIO_VEICULO` (`id_veiculo` ASC),
  CONSTRAINT `FK_PROPRIETARIO_PROPRIETARIO_VEICULO`
    FOREIGN KEY (`id_proprietario`)
    REFERENCES `DB_AAH`.`T_AAH_PROPRIETARIO` (`id_proprietario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_VEICULO_PROPRIETARIO_VEICULO`
    FOREIGN KEY (`id_veiculo`)
    REFERENCES `DB_AAH`.`T_AAH_VEICULO` (`id_veiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_PROPRIETARIO_VEICULO_DT_INICIO` CHECK (`dt_inicio` > '1899-12-31'),
  CONSTRAINT `CK_PROPRIETARIO_VEICULO_DT_FIM` CHECK (`dt_fim` > `dt_inicio`),
  CONSTRAINT `CK_PROPRIETARIO_VEICULO_DS_STATUS` CHECK (`ds_status` BETWEEN 0 AND 1) -- 0 = Inativo, 1 = Ativo
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_VEICULO_INTERCORRENCIAS
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_VEICULO_INTERCORRENCIAS` 
(
  `id_intercorrencia` INT NOT NULL AUTO_INCREMENT,
  `id_veiculo` INT NOT NULL,
  `ds_intercorrencia` TEXT NOT NULL,
  `dt_intercorrencia` DATE NOT NULL,
  `vl_hodometro` DECIMAL(8,1) NOT NULL,
  PRIMARY KEY (`id_intercorrencia`),
  INDEX `IDX_FK_VEICULO_VEICULO_INTERCORRENCIAS` (`id_veiculo` ASC),
  CONSTRAINT `FK_VEICULO_VEICULO_INTERCORRENCIAS`
    FOREIGN KEY (`id_veiculo`)
    REFERENCES `DB_AAH`.`T_AAH_VEICULO` (`id_veiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_VEICULO_INTERCORRENCIAS_DT_INTERCORRENCIA` CHECK (`dt_intercorrencia` > '1899-12-31')
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_ENDERECO_PRESTADOR
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_ENDERECO_PRESTADOR` 
(
  `id_endereco_prestador` INT NOT NULL AUTO_INCREMENT,
  `id_prestador` INT NOT NULL,
  `id_logradouro` INT NOT NULL,
  `nr_endereco` INT NOT NULL,
  `ob_endereco` VARCHAR(180) NULL,
  PRIMARY KEY (`id_endereco_prestador`),
  INDEX `IDX_FK_LOGRADOURO_ENDERECO_PRESTADOR` (`id_logradouro` ASC),
  INDEX `IDX_FK_PRESTADOR_ENDERECO_PRESTADOR` (`id_prestador` ASC),
  CONSTRAINT `FK_LOGRADOURO_ENDERECO_PRESTADOR`
    FOREIGN KEY (`id_logradouro`)
    REFERENCES `DB_AAH`.`T_AAH_LOGRADOURO` (`id_logradouro`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `sFK_PRESTADOR_ENDERECO_PRESTADOR`
    FOREIGN KEY (`id_prestador`)
    REFERENCES `DB_AAH`.`T_AAH_PRESTADOR` (`id_prestador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_ENDERECO_PRESTADOR_NR_ENDERECO` CHECK (`nr_endereco` > 0 AND `nr_endereco` < 1000000)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_AGENDAMENTO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_AGENDAMENTO` 
(
  `id_agendamento` INT NOT NULL AUTO_INCREMENT,
  `id_propriedade` INT NOT NULL,
  `id_prestador` INT NOT NULL,
  `dt_agendamento` DATETIME NOT NULL,
  `ds_servico_solicitado` TEXT NOT NULL,
  `dt_inclusao` DATETIME NOT NULL,
  `ds_status` CHAR(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_agendamento`),
  INDEX `IDX_FK_PROPRIETARIO_VECIULO_AGENDAMENTO` (`id_propriedade` ASC),
  INDEX `IDX_FK_PRESTADOR_AGENDAMENTO` (`id_prestador` ASC),
  CONSTRAINT `FK_PROPRIETARIO_VECIULO_AGENDAMENTO`
    FOREIGN KEY (`id_propriedade`)
    REFERENCES `DB_AAH`.`T_AAH_PROPRIETARIO_VEICULO` (`id_propriedade`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRESTADOR_AGENDAMENTO`
    FOREIGN KEY (`id_prestador`)
    REFERENCES `DB_AAH`.`T_AAH_PRESTADOR` (`id_prestador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_AGENDAMENTO_DT_INCLUSAO` CHECK (`dt_inclusao` > '1899-12-31'),
  -- CONSTRAINT `CK_AGENDAMENTO_DT_AGENDAMENTO` CHECK (`dt_agendamento` >= CURDATE()),
  CONSTRAINT `CK_AGENDAMENTO_DS_STATUS` CHECK (`ds_status` BETWEEN 0 AND 2) -- 0 = Aguarda avaliação, 1 = Aceito, 2 = Não aceito
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_ATENDIMENTO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_ATENDIMENTO` 
(
  `id_atendimento` INT NOT NULL AUTO_INCREMENT,
  `id_agendamento` INT NOT NULL,
  `vl_hodometro_veiculo` DECIMAL(8,1) NOT NULL,
  `dt_inicio` DATE NOT NULL,
  `dt_fim` DATE NULL,
  `ds_atendimento` TEXT NOT NULL,
  PRIMARY KEY (`id_atendimento`),
  INDEX `IDX_FK_AGENDAMENTO_ATENDIMENTO` (`id_agendamento` ASC),
  CONSTRAINT `FK_AGENDAMENTO_ATENDIMENTO`
    FOREIGN KEY (`id_agendamento`)
    REFERENCES `DB_AAH`.`T_AAH_AGENDAMENTO` (`id_agendamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_ATENDIMENTO_DT_INICIO` CHECK (`dt_inicio` > '1899-12-31'),
  CONSTRAINT `CK_ATENDIMENTO_DT_FIM` CHECK (`dt_fim` >= `dt_inicio`),
  CONSTRAINT `UN_ATENDIMENTO_FK_AGENDAMENTO` UNIQUE (`id_agendamento` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_STATUS
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_STATUS` 
(
  `id_status` INT NOT NULL AUTO_INCREMENT,
  `st_status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id_status`),
  UNIQUE INDEX `UN_STATUS_ST_STATUS` (`st_status` ASC),
  CONSTRAINT `CK_STATUS_ST_STATUS` CHECK (`st_status` BETWEEN 'A' AND 'F')
  -- A=Aguardando Início, B=Em Andamento/Em Atendimento, C=Em Espera, D=Encerrado, E=Cancelado 
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_STATUS_ATENDIMENTO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_STATUS_ATENDIMENTO` 
(
  `dt_atualizacao` DATETIME NOT NULL,
  `id_atendimento` INT NOT NULL,
  `id_status` INT NOT NULL,
  `ds_observacao` TEXT NULL,
  PRIMARY KEY (`dt_atualizacao`, `id_atendimento`, `id_status`),
  INDEX `IDX_FK_ATENDIMENTO_STATUS_ATENDIMENTO` (`id_atendimento` ASC),
  CONSTRAINT `FK_ATENDIMENTO_STATUS_ATENDIMENTO`
    FOREIGN KEY (`id_atendimento`)
    REFERENCES `DB_AAH`.`T_AAH_ATENDIMENTO` (`id_atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_STATUS_STATUS_ATENDIMENTO`
    FOREIGN KEY (`id_status`)
    REFERENCES `DB_AAH`.`T_AAH_STATUS` (`id_status`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_STATUS_ATENDIMENTO_DT_ATUALIZACAO` CHECK (`dt_atualizacao` > '1899-12-31')
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_AAH_AVALIACAO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_AAH_AVALIACAO` 
(
  `id_avaliacao` INT NOT NULL AUTO_INCREMENT,
  `id_atendimento` INT NOT NULL,
  `id_proprietario` INT NOT NULL,
  `vl_avaliacao` TINYINT(1) NOT NULL DEFAULT 5,
  `ds_avaliacao` TEXT NULL,
  `dt_avaliacao` DATETIME NOT NULL,
  PRIMARY KEY (`id_avaliacao`),
  INDEX `IDX_FK_ATENDIMENTO_AVALIACAO` (`id_atendimento` ASC),
  INDEX `IDX_FK_PROPRIETARIO_AVALIACAO` (`id_proprietario` ASC),
  CONSTRAINT `FK_ATENDIMENTO_AVALIACAO`
    FOREIGN KEY (`id_atendimento`)
    REFERENCES `DB_AAH`.`T_AAH_ATENDIMENTO` (`id_atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROPRIETARIO_AVALIACAO`
    FOREIGN KEY (`id_proprietario`)
    REFERENCES `DB_AAH`.`T_AAH_PROPRIETARIO` (`id_proprietario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_AVALIACAO_VL_AVALIACAO` CHECK (`vl_avaliacao` BETWEEN 1 AND 5),
  CONSTRAINT `CK_AVALICAO_DT_AVALIACAO` CHECK (`dt_avaliacao`> '1899-12-31')
  -- CONSTRAINT `CK_AVALIACAO_DT_AVALIACAO` CHECK (`dt_avaliacao` <= CURDATE())
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_SERVICO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_SERVICO` 
(
  `id_servico` INT NOT NULL AUTO_INCREMENT,
  `nm_servico` VARCHAR(150) NOT NULL,
  `ds_servico` TEXT NOT NULL,
  PRIMARY KEY (`id_servico`)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table T_SERVICO_ATENDIMENTO
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DB_AAH`.`T_SERVICO_ATENDIMENTO` 
(
  `id_atendimento` INT NOT NULL,
  `id_servico` INT NOT NULL,
  `vl_servico` DECIMAL(9,2) NOT NULL,
  `vl_horas_gastas` SMALLINT NOT NULL,
  PRIMARY KEY (`id_atendimento`, `id_servico`),
  INDEX `IDX_FK_ATENDIMENTO_SERVICO_ATENDIMENTO` (`id_atendimento` ASC),
  INDEX `IDX_FK_SERVICO_SERVICO_ATENDIMENTO` (`id_servico` ASC),
  CONSTRAINT `FK_SERVICO_SERVICO_ATENDIMENTO`
    FOREIGN KEY (`id_servico`)
    REFERENCES `DB_AAH`.`T_SERVICO` (`id_servico`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ATENDIMENTO_SERVICO_ATENDIMENTO`
    FOREIGN KEY (`id_atendimento`)
    REFERENCES `DB_AAH`.`T_AAH_ATENDIMENTO` (`id_atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `CK_SERVICO_ATENDIMENTO_VL_SERVICO` CHECK (`vl_servico` > 0 AND `vl_servico` <= 9999999.99),
  CONSTRAINT `CK_SERVICO_ATENDIMENTO_VL_HORAS_GASTAS` CHECK (`vl_horas_gastas` > 0 AND `vl_horas_gastas` < 10000)
)
ENGINE = InnoDB;
