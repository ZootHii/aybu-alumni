-- 2.
INSERT INTO company_sectors (name) VALUES ('Energy') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Materials') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Industrials') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Consumer Discretionary') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Consumer Staples') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Health Care') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Financials') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Information Technology') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Telecommunication Services') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Utilities') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Real Estate') ON CONFLICT DO NOTHING;
INSERT INTO company_sectors (name) VALUES ('Software') ON CONFLICT DO NOTHING;

INSERT INTO community_sectors (name) VALUES ('Science') ON CONFLICT DO NOTHING;
INSERT INTO community_sectors (name) VALUES ('Aquatics') ON CONFLICT DO NOTHING;
INSERT INTO community_sectors (name) VALUES ('Sports') ON CONFLICT DO NOTHING;
INSERT INTO community_sectors (name) VALUES ('Nature') ON CONFLICT DO NOTHING;