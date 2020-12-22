ALTER TABLE TUSERS ADD COLUMN accountnonexpired boolean;

UPDATE TUSERS SET accountnonexpired = TRUE;