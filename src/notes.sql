-- Описать NUMBERS
CREATE TABLE IF NOT EXISTS "numbers" (
    "num_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "number" TEXT NOT NULL
);
CREATE UNIQUE INDEX unq_number ON numbers(number); -- херачим, чтобы в таблице не было два одинаковых номера дела.


-- вставка в NUMBERS для примера:
INSERT OR IGNORE INTO numbers (number) VALUES ('523/4304/15-ц');
INSERT OR IGNORE INTO numbers (number) VALUES ('520/1902/16-ц');
INSERT OR IGNORE INTO numbers (number) VALUES ('522/5075/16-к');
INSERT OR IGNORE INTO numbers (number) VALUES ('520/5805/13-ц');



-- Описать HEARINGS
CREATE TABLE IF NOT EXISTS "hearings" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "date" TEXT,
    "num_id" TEXT,
    "involved" TEXT,
    "description" TEXT,
    "judge" TEXT,
    "form" TEXT,
    "address" TEXT
);


-- insertion to HEARINGS for example
INSERT INTO hearings (date, num_id, involved, description, judge, form, address) VALUES ('27.09.2016 00:00',1,'some people','some crime','Осіїк','civil','Balkivska, 33');


--SEARCH BY CASE NUMBER---
SELECT * FROM hearings
WHERE  num_id=(
SELECT num_id FROM numbers
WHERE number='522/5075/16-к'
);
--------------------------


--GET ALL IDs---------
SELECT * FROM numbers;
----------------------

--READ CURRENT LIST OF CASES--
SELECT h.date, n.number, h.involved, h.description, h.judge, h.form, h.address
FROM hearings h
JOIN numbers n ON h.num_id=n.num_id;
----------------------

--SAVE METHOD--------_
UPDATE hearings
SET date = ?, involved = ?, description = ?, judge = ?, form = ?, address = ?
WHERE num_id = (SELECT num_id FROM numbers WHERE number = ?);
--V2
BEGIN;
DELETE FROM hearings;

--blablabla...--
COMMIT;

------------
