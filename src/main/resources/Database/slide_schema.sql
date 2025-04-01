-- Create the sequence for generating slide IDs (starting with 1000)
CREATE SEQUENCE IF NOT EXISTS slide_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the slide table with required columns
CREATE TABLE IF NOT EXISTS slide (
    id INT DEFAULT nextval('slide_sequence') PRIMARY KEY,
    case_record_id INT NOT NULL,
    main_image TEXT,
    microscopic_dc TEXT,
    diagnosis TEXT,
    ai_insights TEXT,
    specimen_type VARCHAR(255) NOT NULL,
    collection_site VARCHAR(255) NOT NULL,
    clinical_data VARCHAR(255) NOT NULL,
    report_id VARCHAR(255),
    FOREIGN KEY (case_record_id) REFERENCES case_record(id) ON DELETE CASCADE
);