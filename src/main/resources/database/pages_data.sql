-- 4.
INSERT INTO pages (name, page_url, website_url, owner_user_1)
VALUES ('Alumni', 'http://localhost:4024/pages/company/alumni', 'http://localhost:4024/pages/company/alumni', 1)
ON CONFLICT DO NOTHING;
INSERT INTO company_pages (city_id, company_sector_id, page_id)
VALUES (58, 12, 1)
ON CONFLICT DO NOTHING;