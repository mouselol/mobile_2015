DROP TABLE IF EXISTS "task";
CREATE TABLE "task" ("id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "task" TEXT, "date" TEXT, "status" INTEGER DEFAULT 0);
INSERT INTO "task" VALUES(1,'сделать зарядку','2015-01-01 00:00:00.000',0);
INSERT INTO "task" VALUES(2,'убить двух зайцев','2015-02-01 00:00:00.000',0);
