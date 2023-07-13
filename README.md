# Test task Java Backend developer

# Postman

For the convenience of testing the application 
path: TestTaskTelros/Test Task Telros.postman_collection.json

The file contains a collection for Postman

# Auth
After user authorization Bearer JWT token is returned.
To check the access of roles, you need to copy the token and use it in other Postman requests.

# PostgreSQL

Connect to local database : application.properties

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO users (id, birthday, email, image, number_phone, password, patronymic, surname, username, full_name)
VALUES   (1, '15.01.1992', 'admin@gmail.com', 'image-', '89116752345' ,'$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu','Ivanovich' , 'Ivanov','admin', 'Ivan'),
(2, '17.07.1994', 'liza@gmail.com', 'image-', '89215726566', '$2a$10$2M7ltMXyOCaTZ7bjLCWtfeIbf5lVBaSMYYHOCqGAPJ7cKff7pH7Nq', 'Kirillovna' , 'Kabak','liza', 'Elizaveta');

INSERT INTO users_roles (role_id, user_id)
VALUES (1, 1),
(2, 2);








