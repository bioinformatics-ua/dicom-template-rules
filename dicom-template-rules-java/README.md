 * DB Schema
 * PRAGMA foreign_keys = ON;
 * CREATE TABLE rules (id integer primary key, rulename text not null);
 * CREATE TABLE matchFields (id INTEGER PRIMARY KEY, key text not null, value text not null, ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 * CREATE TABLE i18n (id INTEGER PRIMARY KEY, key text not null, value text not null,  ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 * CREATE TABLE map (id INTEGER PRIMARY KEY, key text not null, value text not null,  ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
