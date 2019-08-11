CREATE TABLE IF NOT EXISTS demo (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  int_enum INT,
  string_enum varchar(20),
  json_obj varchar(255),
  json_arr varchar(255),
  json_simple varchar(255),
  yaml_obj varchar(255)
);
