-- 1.
INSERT INTO fake_obs_datas (tc_identity_number, name, surname, grade, department)
VALUES ('11111111111', 'Alumni', 'Alumni', '4.', 'Computer Engineering')
ON CONFLICT DO NOTHING;

INSERT INTO fake_obs_datas (tc_identity_number, name, surname, grade, department)
VALUES ('22222222222', 'Test', 'Test', 'ALUMNI', 'Electric Electronic Engineering')
ON CONFLICT DO NOTHING;

INSERT INTO fake_obs_datas (tc_identity_number, name, surname, grade, department)
VALUES ('33333333333', 'Emirhan', 'Erol', '4.', 'Civil Engineering')
ON CONFLICT DO NOTHING;

INSERT INTO fake_obs_datas (tc_identity_number, name, surname, grade, department)
VALUES ('44444444444', 'Ahmet', 'Yıldırım', '3.', 'Mechanical Engineering')
ON CONFLICT DO NOTHING;

INSERT INTO fake_obs_datas (tc_identity_number, name, surname, grade, department)
VALUES ('55555555555', 'Özkan', 'Semiz', 'ALUMNI', 'Industrial Engineering')
ON CONFLICT DO NOTHING;

INSERT INTO fake_obs_datas (tc_identity_number, name, surname, grade, department)
VALUES ('66666666666', 'İsmail Hakkı', 'Çifci', '3.', 'History')
ON CONFLICT DO NOTHING;

INSERT INTO user_contact_infos (email, phone_number)
VALUES ('info@alumni.com', 5555555555)
ON CONFLICT DO NOTHING;

INSERT INTO users (email, password, name, surname, profile_url, user_contact_info_id, grade, department,
                   name_in_college, surname_in_college)
VALUES ('alumni@alumni.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a',
        'Alumni', 'Alumni', 'http://localhost:4024/users/profile/alumni', 1, '4.', 'Computer Engineering', 'Alumni',
        'Alumni')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url, grade, department, name_in_college, surname_in_college)
VALUES ('test@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test',
        'Test', 'http://localhost:4024/users/profile/test', 'ALUMNI', 'Electric Electronic Engineering', 'Test', 'Test')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url, grade, department, name_in_college, surname_in_college)
VALUES ('test1@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test',
        'Test', 'http://localhost:4024/users/profile/test1', 'ALUMNI', 'Electric Electronic Engineering', 'Test',
        'Test')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url, grade, department, name_in_college, surname_in_college)
VALUES ('test2@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test',
        'Test', 'http://localhost:4024/users/profile/test2', 'ALUMNI', 'Electric Electronic Engineering', 'Test',
        'Test')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url, grade, department, name_in_college, surname_in_college)
VALUES ('test3@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test',
        'Test', 'http://localhost:4024/users/profile/test3', '4.', 'Computer Engineering', 'Alumni', 'Alumni')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url, grade, department, name_in_college, surname_in_college)
VALUES ('test4@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test',
        'Test', 'http://localhost:4024/users/profile/test4', '4.', 'Computer Engineering', 'Alumni', 'Alumni')
ON CONFLICT DO NOTHING;
INSERT INTO users (email, password, name, surname, profile_url, grade, department, name_in_college, surname_in_college)
VALUES ('test5@test.com', '1a07dbacb02ca257966c74953a7f518f4470189807729069dbcea58c36e004ff45e4d94d2517779a', 'Test',
        'Test', 'http://localhost:4024/users/profile/test5', '4.', 'Computer Engineering', 'Alumni', 'Alumni')
ON CONFLICT DO NOTHING;
