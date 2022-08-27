CREATE TABLE airports (
    id INT PRIMARY KEY,
    iata CHAR(3) NOT NULL,
    icao CHAR(4) NOT NULL,
    airport_name VARCHAR(128),
    airport_location VARCHAR(128),
    street_number VARCHAR(8),
    street VARCHAR(64),
    city VARCHAR(64),
    county VARCHAR(32),
    country_state VARCHAR(32),
    state_code VARCHAR(5) NOT NULL,
    country_iso VARCHAR(8),
    country VARCHAR(32),
    postal_code VARCHAR(32),
    phone VARCHAR(32),
    latitude REAL,
    longitude REAL,
    uct INT,
    website VARCHAR(128)
);