-- Create the sequence for generating annotation IDs (starting with 1000)
CREATE SEQUENCE IF NOT EXISTS annotation_sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Create the annotation table with required columns
CREATE TABLE annotation (
    id INT DEFAULT nextval('annotation_sequence') PRIMARY KEY,
    slides_id BIGINT NOT NULL,
    date VARCHAR(255) NOT NULL,
    label VARCHAR(255) NOT NULL,
    time VARCHAR(255) NOT NULL,
    year VARCHAR(255) NOT NULL,
    image_base64 TEXT,
    coordinates TEXT,
    FOREIGN KEY (slides_id) REFERENCES slide(id) ON DELETE CASCADE
);
