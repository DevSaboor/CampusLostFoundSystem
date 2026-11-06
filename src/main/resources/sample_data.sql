-- =====================================================================
-- Campus Lost and Found - Sample Data
-- Run AFTER schema.sql. Safe to re-run: it clears the data tables first.
-- Categories are NOT touched (they come from schema.sql).
--
-- Category IDs in the database (from schema.sql):
--   1 Electronics | 2 Documents | 3 Accessories | 4 Clothing | 5 Books | 6 Other
-- =====================================================================

USE campus_lost_found;

-- Clear previous sample data (child tables first)
DELETE FROM claims;
DELETE FROM items;
DELETE FROM users;

-- ---------------------------------------------------------------------
-- USERS
-- user 1 is the admin account; the rest are normal users.
-- Passwords are plain text, matching how the app currently works.
-- ---------------------------------------------------------------------
INSERT INTO users (user_id, name, email, password, phone, role) VALUES
(1, 'Admin Officer', 'admin@campus.edu', 'admin123', '0300-1000001', 'ADMIN'),
(2, 'Ali Hassan',    'ali@campus.edu',   'ali123',   '0321-2000002', 'USER'),
(3, 'Zara Ahmed',    'zara@campus.edu',  'zara123',  '0333-3000003', 'USER'),
(4, 'Omar Siddiqui', 'omar@campus.edu',  'omar123',  '0345-4000004', 'USER'),
(5, 'Hina Khan',     'hina@campus.edu',  'hina123',  '0312-5000005', 'USER');

-- ---------------------------------------------------------------------
-- ITEMS
-- type:   LOST or FOUND
-- status: ACTIVE (open) or CLAIMED (an approved claim exists)
-- user_id is the person who reported the item.
-- ---------------------------------------------------------------------
INSERT INTO items (item_id, user_id, category_id, title, description, location, item_date, type, status) VALUES
(1, 2, 1, 'Black wireless earbuds',
   'Black case with a small scratch on the left earbud',
   'Library 2nd floor', '2026-10-20', 'LOST', 'ACTIVE'),

(2, 3, 2, 'Student ID card',
   'Name partly visible, roll number 22CS017 printed on the back',
   'Cafeteria entrance', '2026-10-22', 'FOUND', 'ACTIVE'),

(3, 4, 5, 'Database System Concepts textbook',
   'Blue sticky note inside the front cover',
   'Block C lecture hall', '2026-10-18', 'LOST', 'ACTIVE'),

(4, 5, 6, 'Blue steel water bottle',
   'Steel bottle with a faded sticker',
   'Sports ground', '2026-10-25', 'FOUND', 'ACTIVE'),

(5, 2, 3, 'Brown leather wristwatch',
   'Brown leather strap, silver case',
   'Main gate', '2026-10-15', 'FOUND', 'CLAIMED'),

(6, 5, 4, 'Grey hoodie',
   'Plain grey hoodie, size M',
   'Gym changing room', '2026-10-24', 'FOUND', 'ACTIVE'),

(7, 4, 1, 'Scientific calculator',
   'Casio calculator with a cracked corner',
   'Exam hall 2', '2026-10-28', 'LOST', 'ACTIVE'),

(8, 3, 5, 'Calculus lecture notes',
   'Spiral notebook, first 20 pages filled with notes',
   'Room 204', '2026-10-26', 'FOUND', 'ACTIVE');

-- ---------------------------------------------------------------------
-- CLAIMS
-- claim status: PENDING, APPROVED, or REJECTED
-- Rule: an item marked CLAIMED (item 5) has exactly one APPROVED claim.
-- ---------------------------------------------------------------------
INSERT INTO claims (claim_id, item_id, user_id, claim_description, status, created_at) VALUES
(1, 5, 3, 'Engraved initials ZA inside the back cover of the watch', 'APPROVED', '2026-10-26 10:15:00'),
(2, 2, 4, 'This is my student ID, roll number 21CS045 on my card',   'PENDING',  '2026-10-27 09:30:00'),
(3, 4, 2, 'My bottle, name written on the bottom in marker',         'REJECTED', '2026-10-27 14:05:00');
