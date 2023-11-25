-- Проверка возраст > 16
ALTER TABLE student
ADD CONSTRAINT age_constraint CHECK (age>16);

-- Проверка уникальности имени
ALTER TABLE student
ADD CONSTRAINT name_unique UNIQUE (name);

-- Ограничение Имя != null
ALTER TABLE student
ALTER name SET NOT NULL;

-- Проверка уникальности пары Имя-цвет факультета
ALTER TABLE faculty
ADD CONSTRAINT name_colour_unique UNIQUE (name, colour);

-- Установка возраста "по умолчанию"
ALTER TABLE student
ALTER age SET DEFAULT 20;




