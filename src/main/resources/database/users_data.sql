-- 1.

INSERT INTO user_contact_infos (email, phone_number)
VALUES ('info@alumni.com', 5555555555)
ON CONFLICT DO NOTHING;

INSERT INTO users (email, password, name, surname, profile_url, user_contact_info_id)
VALUES ('alumni@alumni.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Alumni', 'Alumni', 'http://localhost:4024/users/profile/alumni', 1)
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url)
VALUES ('test@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test', 'Test', 'http://localhost:4024/users/profile/test')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url)
VALUES ('test1@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test', 'Test', 'http://localhost:4024/users/profile/test1')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url)
VALUES ('test2@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test', 'Test', 'http://localhost:4024/users/profile/test2')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url)
VALUES ('test3@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test', 'Test', 'http://localhost:4024/users/profile/test3')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url)
VALUES ('test4@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test', 'Test', 'http://localhost:4024/users/profile/test4')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url)
VALUES ('test5@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test', 'Test', 'http://localhost:4024/users/profile/test5')
ON CONFLICT DO NOTHING;
