ALTER TABLE pharmacies
ALTER COLUMN name TYPE varchar(255),
ALTER COLUMN medicine_link_template TYPE varchar(255);

ALTER TABLE medicines
ALTER COLUMN title TYPE varchar(255);