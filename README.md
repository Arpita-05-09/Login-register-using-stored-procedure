# Login-register-using-stored-procedure

#Stored procedure :

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

DELIMITER //

CREATE PROCEDURE register_user(IN uname VARCHAR(50), IN upass VARCHAR(255))
BEGIN
    DECLARE user_exists INT DEFAULT 0;

    SELECT COUNT(*) INTO user_exists 
    FROM users 
    WHERE username = uname;

    IF user_exists > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'User already registered';
    ELSE
        INSERT INTO users (username, password)
        VALUES (uname, SHA2(upass, 256));
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE login_user(IN uname VARCHAR(50), IN upass VARCHAR(255))
BEGIN
    DECLARE matched INT DEFAULT 0;

    SELECT COUNT(*) INTO matched
    FROM users
    WHERE username = uname
      AND password = SHA2(upass, 256);

    IF matched = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Invalid username or password';
    ELSE
        SELECT id, username, password
        FROM users
        WHERE username = uname
          AND password = SHA2(upass, 256);
    END IF;
END //
DELIMITER ;
