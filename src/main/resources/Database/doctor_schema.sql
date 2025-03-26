-- Create the sequence with a start value of 1000
CREATE SEQUENCE IF NOT EXISTS doctor_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the doctor table with the sequence linked correctly
CREATE TABLE IF NOT EXISTS doctor (
    id BIGINT PRIMARY KEY DEFAULT nextval('doctor_sequence'),
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) DEFAULT NULL,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    specialist VARCHAR(255) NOT NULL,
    birth_date VARCHAR(50) NOT NULL,
    signature TEXT
);

-- Ensure the sequence is properly owned by the ID column
ALTER SEQUENCE doctor_sequence OWNED BY doctor.id;
