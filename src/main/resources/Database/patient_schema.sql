-- Create the sequence for generating user IDs (starting with 1000)
CREATE SEQUENCE IF NOT EXISTS patient_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the patient table with required columns
CREATE TABLE IF NOT EXISTS patient (
    id INT DEFAULT nextval('patient_sequence') PRIMARY KEY,
    name VARCHAR(255),
    age VARCHAR(10),
    gender VARCHAR(50),
    address TEXT,
    email VARCHAR(255),
    state VARCHAR(255),
    status VARCHAR(255),
    type VARCHAR(255),
    date_of_birth VARCHAR(50),
    phone_number VARCHAR(50),
    image_base64 TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
