-- --- !Ups
ALTER TABLE "POST" ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- --- !Downs
ALTER TABLE "POST" DROP COLUMN created_at;
