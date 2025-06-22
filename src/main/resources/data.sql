CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tracking_numbers (
    id SERIAL PRIMARY KEY,  -- Use UUID and PostgreSQL's uuid_generate_v4() function
    tracking_number VARCHAR(255) NOT NULL,
    customer_id UUID NOT NULL,
    origin_country_id VARCHAR(2) NOT NULL,
    destination_country_id VARCHAR(2) NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL,
    correlation_id VARCHAR(255) NOT NULL
);