CREATE TABLE IF NOT EXISTS rawData (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    source_id INTEGER NOT NULL,
    baseDate DATE ,
    first_day_min_temp DOUBLE ,
    first_day_max_temp DOUBLE ,
    second_day_min_temp DOUBLE ,
    second_day_max_temp DOUBLE ,
    third_day_min_temp DOUBLE ,
    third_day_max_temp DOUBLE ,
    fourth_day_min_temp DOUBLE ,
    fourth_day_max_temp DOUBLE ,
    fifth_day_min_temp DOUBLE ,
    fifth_day_max_temp DOUBLE ,
    six_day_min_temp DOUBLE ,
    six_day_max_temp DOUBLE ,
    seven_day_min_temp DOUBLE ,
    seven_day_max_temp DOUBLE ,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `sources` (
  `id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(45) NOT NULL,
  `baseUrl` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

INSERT IGNORE INTO `sources` (`id`, `name`) VALUES ('0', 'GISMETEO');
INSERT IGNORE INTO `sources` (`id`, `name`) VALUES ('1', 'METEOPROG');
INSERT IGNORE INTO .`sources` (`id`, `name`) VALUES ('2', 'SINOPTIK');

CREATE TABLE IF NOT EXISTS `rawdata_analysis` (
  `id` INT NOT NULL,
  `source` VARCHAR(45) NOT NULL,
  `baseline_date` DATE NULL,
  `backward_deepness` INT NULL,
  `max_temp_diff` DOUBLE NULL,
  `min_temp_diff` DOUBLE NULL,
  `rawdata_analysiscol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
