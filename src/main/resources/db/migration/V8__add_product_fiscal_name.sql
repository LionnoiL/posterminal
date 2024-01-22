ALTER TABLE products
ADD COLUMN product_fiscal_name VARCHAR (150) NULL;

UPDATE products SET product_fiscal_name = product_name;

ALTER TABLE products
ALTER COLUMN product_fiscal_name VARCHAR(150) NOT NULL;

