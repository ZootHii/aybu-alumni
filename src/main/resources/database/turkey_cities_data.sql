-- 3.
INSERT INTO turkey_cities (name) VALUES ('ADANA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ADIYAMAN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('AFYONKARAHİSAR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('AĞRI') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('AMASYA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ANKARA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ANTALYA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ARTVİN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('AYDIN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BALIKESİR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BİLECİK') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BİNGÖL') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BİTLİS') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BOLU') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BURDUR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BURSA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ÇANAKKALE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ÇANKIRI') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ÇORUM') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('DENİZLİ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('DİYARBAKIR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('EDİRNE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ELAZIĞ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ERZİNCAN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ERZURUM') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ESKİŞEHİR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('GAZİANTEP') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('GİRESUN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('GÜMÜŞHANE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('HAKKARİ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('HATAY') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ISPARTA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('MERSİN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('İSTANBUL') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('İZMİR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KARS') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KASTAMONU') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KAYSERİ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KIRKLARELİ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KIRŞEHİR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KOCAELİ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KONYA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KÜTAHYA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('MALATYA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('MANİSA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KAHRAMANMARAŞ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('MARDİN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('MUĞLA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('MUŞ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('NEVŞEHİR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('NİĞDE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ORDU') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('RİZE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('SAKARYA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('SAMSUN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('SİİRT') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('SİNOP') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('SİVAS') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('TEKİRDAĞ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('TOKAT') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('TRABZON') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('TUNCELİ') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ŞANLIURFA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('UŞAK') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('VAN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('YOZGAT') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ZONGULDAK') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('AKSARAY') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BAYBURT') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KARAMAN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KIRIKKALE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BATMAN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ŞIRNAK') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('BARTIN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('ARDAHAN') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('IĞDIR') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('YALOVA') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KARABÜK') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('KİLİS') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('OSMANİYE') ON CONFLICT DO NOTHING;
INSERT INTO turkey_cities (name) VALUES ('DÜZCE') ON CONFLICT DO NOTHING;



INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (1, 2, true)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (1, 3, true)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (1, 4, false)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (4, 2, true)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (4, 3, false)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (4, 5, false)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (5, 2, false)
ON CONFLICT DO NOTHING;

INSERT INTO friendships (sender_id, receiver_id, is_accepted)
VALUES (2, 3, false)
ON CONFLICT DO NOTHING;
