CREATE TABLE virtual_secret (

  -- client id
  virtual_secret_id   VARCHAR(100) NOT NULL,

  application_id      VARCHAR(100),
  client_id           VARCHAR(100),
  owner_user_id       VARCHAR(100),

  application_user_id VARCHAR(100),
  client_user_id      VARCHAR(100),
  -- client secret
  client_secret       VARCHAR(100) NOT NULL,
  date_created        DATETIME,

  security            VARCHAR(15)  NOT NULL,

  optimistic          INT(11) DEFAULT 0,

  INDEX (application_id),
  INDEX (client_id),
  INDEX (owner_user_id),
  INDEX (application_user_id),
  INDEX (client_user_id),
  INDEX (client_secret),
  INDEX (date_created),
  INDEX (security),
  INDEX (optimistic),
  UNIQUE (virtual_secret_id, client_secret),
  PRIMARY KEY (virtual_secret_id)
);