-- 4.
INSERT INTO pages (name, page_url, website_url, owner_user_id)
VALUES ('Alumni Company', 'http://localhost:4024/api/pages/company/alumni', 'http://localhost:4024/api/pages/company/alumni', 1)
ON CONFLICT DO NOTHING;
INSERT INTO company_pages (city_id, company_sector_id, page_id)
VALUES (58, 12, 1)
ON CONFLICT DO NOTHING;

INSERT INTO pages (name, page_url, website_url, owner_user_id)
VALUES ('Alumni Community', 'http://localhost:4024/api/pages/community/alumni', 'http://localhost:4024/api/pages/community/alumni', 1)
ON CONFLICT DO NOTHING;
INSERT INTO community_pages (community_sector_id, page_id)
VALUES (1, 2)
ON CONFLICT DO NOTHING;

UPDATE users SET company_page_id = 1, community_page_id = 1 WHERE id = 1;