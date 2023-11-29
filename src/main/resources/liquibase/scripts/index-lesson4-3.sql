-- liquibase formatted sql

-- changeset jrembo:1

CREATE INDEX IF NOT EXISTS student_name_index ON student (name);

CREATE INDEX IF NOT EXISTS faculty_name_colour ON faculty (name, colour);