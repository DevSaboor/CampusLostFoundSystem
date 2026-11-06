-- =====================================================================
-- Campus Lost and Found - Queries
-- Run AFTER schema.sql and sample_data.sql.
-- Each query lists what it does and which app screen it matches.
-- =====================================================================

USE campus_lost_found;

-- ---------------------------------------------------------------------
-- 1. SEARCH: all ACTIVE LOST items with their category and reporter
--    Matches: Search Items screen (filter = LOST)
-- ---------------------------------------------------------------------
SELECT i.item_id,
       i.title,
       c.category_name,
       i.location,
       i.item_date,
       u.name AS reported_by
FROM items i
JOIN categories c ON c.category_id = i.category_id
JOIN users u      ON u.user_id     = i.user_id
WHERE i.type = 'LOST'
  AND i.status = 'ACTIVE'
ORDER BY i.item_date DESC;

-- ---------------------------------------------------------------------
-- 2. SEARCH: keyword search across title and description
--    Matches: Search Items screen when a user types a keyword
--    (change 'book' to any word)
-- ---------------------------------------------------------------------
SELECT i.item_id, i.title, i.type, i.status, i.location
FROM items i
WHERE i.title LIKE '%book%'
   OR i.description LIKE '%book%'
ORDER BY i.item_date DESC;

-- ---------------------------------------------------------------------
-- 3. SEARCH: filter by category and status together
--    Matches: Search Items screen with category and status filters
-- ---------------------------------------------------------------------
SELECT i.item_id, i.title, i.type, i.location, i.item_date
FROM items i
JOIN categories c ON c.category_id = i.category_id
WHERE c.category_name = 'Electronics'
  AND i.status = 'ACTIVE';

-- ---------------------------------------------------------------------
-- 4. RETRIEVE: a user's own reports (My Reports screen)
--    Change user_id = 2 to any user (Ali Hassan is 2)
-- ---------------------------------------------------------------------
SELECT i.item_id,
       i.title,
       c.category_name,
       i.type,
       i.status,
       i.created_at
FROM items i
JOIN categories c ON c.category_id = i.category_id
WHERE i.user_id = 2
ORDER BY i.created_at DESC;

-- ---------------------------------------------------------------------
-- 5. ADMIN: pending claims waiting for review (Manage Claims screen)
--    Shows the item being claimed and the person claiming it
-- ---------------------------------------------------------------------
SELECT cl.claim_id,
       i.title       AS item_title,
       u.name        AS claimant,
       u.email       AS claimant_email,
       cl.claim_description,
       cl.created_at
FROM claims cl
JOIN items i ON i.item_id = cl.item_id
JOIN users u ON u.user_id = cl.user_id
WHERE cl.status = 'PENDING'
ORDER BY cl.created_at;

-- ---------------------------------------------------------------------
-- 6. RETRIEVE: full claim history for one item
--    Shows every claim (any status) on the item, including who made it
-- ---------------------------------------------------------------------
SELECT cl.claim_id,
       u.name AS claimant,
       cl.status,
       cl.claim_description,
       cl.created_at
FROM claims cl
JOIN users u ON u.user_id = cl.user_id
WHERE cl.item_id = 5
ORDER BY cl.created_at;

-- ---------------------------------------------------------------------
-- 7. REPORT: number of items per category and type
--    Good for the report's "operations" section (GROUP BY with JOIN)
-- ---------------------------------------------------------------------
SELECT c.category_name,
       SUM(i.type = 'LOST')  AS lost_count,
       SUM(i.type = 'FOUND') AS found_count,
       COUNT(i.item_id)      AS total
FROM categories c
LEFT JOIN items i ON i.category_id = c.category_id
GROUP BY c.category_id, c.category_name
ORDER BY total DESC, c.category_name;

-- ---------------------------------------------------------------------
-- 8. REPORT: how many items each user has reported (LEFT JOIN)
--    Users with no reports still appear, with a count of 0
-- ---------------------------------------------------------------------
SELECT u.name,
       u.role,
       COUNT(i.item_id) AS items_reported
FROM users u
LEFT JOIN items i ON i.user_id = u.user_id
GROUP BY u.user_id, u.name, u.role
ORDER BY items_reported DESC, u.name;

-- ---------------------------------------------------------------------
-- 9. REPORT: claim outcomes (how many were approved, rejected, pending)
-- ---------------------------------------------------------------------
SELECT status, COUNT(*) AS total
FROM claims
GROUP BY status
ORDER BY total DESC;

-- ---------------------------------------------------------------------
-- 10. INTEGRITY CHECK: CLAIMED items must have an APPROVED claim
--     Expected result: 0 rows. Good to show in the testing section.
-- ---------------------------------------------------------------------
SELECT i.item_id, i.title
FROM items i
WHERE i.status = 'CLAIMED'
  AND NOT EXISTS (
        SELECT 1
        FROM claims cl
        WHERE cl.item_id = i.item_id
          AND cl.status = 'APPROVED'
      );

-- ---------------------------------------------------------------------
-- 11. INTEGRITY CHECK: items with an APPROVED claim must be CLAIMED
--     Expected result: 0 rows.
-- ---------------------------------------------------------------------
SELECT DISTINCT i.item_id, i.title, i.status
FROM items i
JOIN claims cl ON cl.item_id = i.item_id
WHERE cl.status = 'APPROVED'
  AND i.status <> 'CLAIMED';
