CREATE TABLE IF NOT EXISTS kitchen (
  id      BIGINT       NOT NULL AUTO_INCREMENT,
  name    VARCHAR(100) NOT NULL,
  
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;