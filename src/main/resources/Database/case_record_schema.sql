-- Create the sequence for generating user IDs (starting with 1000)
CREATE SEQUENCE IF NOT EXISTS case_record_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the case_record table with required columns
CREATE TABLE IF NOT EXISTS case_record (
    id INT DEFAULT nextval('case_record_sequence') PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    date VARCHAR(50) NOT NULL,
    time VARCHAR(50) NOT NULL,
    year VARCHAR(10) NOT NULL,
    status VARCHAR(255),
    type VARCHAR(255) NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);
