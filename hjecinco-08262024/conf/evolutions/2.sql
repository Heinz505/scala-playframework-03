-- Create Books table
CREATE TABLE books (
  isbn VARCHAR(20) PRIMARY KEY,
  title VARCHAR NOT NULL,
  publication_year INT NOT NULL,
  author_id UUID NOT NULL REFERENCES authors(id),
  price NUMERIC(10, 2) NOT NULL
);
