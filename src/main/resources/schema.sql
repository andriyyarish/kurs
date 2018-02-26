CREATE TABLE IF NOT EXISTS rawData (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    sourceName VARCHAR(128) NOT NULL,
    baseDate DATE ,
    firstDayMinTemp DOUBLE ,
    firstDayMaxTemp DOUBLE ,
    secondDayMinTemp DOUBLE ,
    secondDayMaxTemp DOUBLE ,
    thirdDayMinTemp DOUBLE ,
    thirdDayMaxTemp DOUBLE ,
    fourthDayMinTemp DOUBLE ,
    fourthDayMaxTemp DOUBLE ,
    fifthDayMinTemp DOUBLE ,
    fifthDayMaxTemp DOUBLE ,
    sixDayMinTemp DOUBLE ,
    sixDayMaxTemp DOUBLE ,
    sevenDayMinTemp DOUBLE ,
    sevenDayMaxTemp DOUBLE ,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `sources` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `baseUrl` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));