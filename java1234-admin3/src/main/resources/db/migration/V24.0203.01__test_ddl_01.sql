-- ========================================================================================================
-- 【資料表】: 測試使用者主表
-- ========================================================================================================
DROP TABLE IF EXISTS TEST_USER_MTR CASCADE;
CREATE TABLE IF NOT EXISTS TEST_USER_MTR (
    ID                BIGSERIAL,
    USER_ID           BIGSERIAL,
    USER_NAME         VARCHAR(64),
    USER_PASSWORD     VARCHAR(64),
    EMAIL             VARCHAR(64)

);
DROP INDEX IF EXISTS CMSIX_TEST_USER_MTR_01;
CREATE UNIQUE INDEX CMSIX_TEST_USER_MTR_01 ON TEST_USER_MTR (USER_ID);
-----------------------------------------------------------------------------------------------------------
COMMENT ON  TABLE TEST_USER_MTR                     IS '資料表!測試使用者(主表)';
COMMENT ON COLUMN TEST_USER_MTR.ID                  IS '資料!系統ID';
COMMENT ON COLUMN TEST_USER_MTR.USER_ID             IS '資料!使用者ID';
COMMENT ON COLUMN TEST_USER_MTR.USER_NAME           IS '資料!使用者名稱';
COMMENT ON COLUMN TEST_USER_MTR.USER_PASSWORD       IS '資料!使用者密碼';
COMMENT ON COLUMN TEST_USER_MTR.EMAIL               IS '資料!EMAIL';
-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
