CREATE TABLE searches (
    search_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    src_airport_id INT,
    des_airport_id INT,
    search_time TIMESTAMP,
    result VARCHAR(50)
);