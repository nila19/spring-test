DROP TABLE IF EXISTS TRANSACTIONS;

CREATE TABLE TRANSACTIONS
(
    TRANSACTION_ID LONG AUTO_INCREMENT PRIMARY KEY,
    FROM_ACCT      VARCHAR(250) NOT NULL,
    TO_ACCT        VARCHAR(250) NOT NULL,
    AMOUNT         DOUBLE
);

INSERT INTO TRANSACTIONS (TRANSACTION_ID, FROM_ACCT, TO_ACCT, AMOUNT)
VALUES (100, 'Acct 1', 'Acct 2', 200.50),
       (101, 'Acct 2', 'Acct 3', 201.50),
       (102, 'Acct 1', 'Acct 3', 202.50),
       (103, 'Acct 3', 'Acct 4', 203.50);

COMMIT;

SELECT *
FROM TRANSACTIONS T;
