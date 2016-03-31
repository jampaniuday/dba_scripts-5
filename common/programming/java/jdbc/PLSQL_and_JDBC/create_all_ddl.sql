/*
 * -----------------------------------------------------------------------------
 * CREATE TABLE "emp"
 * -----------------------------------------------------------------------------
 */
DROP TABLE emp
/

CREATE TABLE emp (
    emp_id           NUMBER
  , dept_id          NUMBER
  , name             VARCHAR2(30)
  , date_of_birth    DATE
  , date_of_hire     DATE
  , monthly_salary   NUMBER(15,2)
  , position         VARCHAR2(100)
  , extension        NUMBER
  , office_location  VARCHAR2(100)
)
/

 
INSERT INTO emp VALUES (
    1001
  , 105
  , 'Jeff Hunter'
  , '31-DEC-1967'
  , '07-JUL-1994'
  , 8700.00
  ,'Sr. Software Engineer'
  , 6007
  , 'Butler, PA'
)
/

COMMIT;


/*
 * -----------------------------------------------------------------------------
 * CREATE TABLE "dept"
 * -----------------------------------------------------------------------------
 */
DROP TABLE dept
/

CREATE TABLE dept (
    dept_id       NUMBER
  , name          VARCHAR2(100)
  , location      VARCHAR2(100)
)
/


INSERT INTO DEPT VALUES (100 , 'ACCOUNTING'          , 'BUTLER, PA');
INSERT INTO DEPT VALUES (101 , 'RESEARCH'            , 'DALLAS, TX');
INSERT INTO DEPT VALUES (102 , 'SALES'               , 'CHICAGO, IL');
INSERT INTO DEPT VALUES (103 , 'OPERATIONS'          , 'BOSTON, MA');
INSERT INTO DEPT VALUES (104 , 'IT'                  , 'PITTSBURGH, PA');
INSERT INTO DEPT VALUES (105 , 'ENGINEERING'         , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (106 , 'QA'                  , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (107 , 'PROCESSING'          , 'NEW YORK, NY');
INSERT INTO DEPT VALUES (108 , 'CUSTOMER SUPPORT'    , 'TRANSFER, PA');
INSERT INTO DEPT VALUES (109 , 'HQ'                  , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (110 , 'PRODUCTION SUPPORT'  , 'MONTEREY, CA');
INSERT INTO DEPT VALUES (111 , 'DOCUMENTATION'       , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (112 , 'HELP DESK'           , 'GREENVILLE, PA');
INSERT INTO DEPT VALUES (113 , 'AFTER HOURS SUPPORT' , 'SAN JOSE, CA');
INSERT INTO DEPT VALUES (114 , 'APPLICATION SUPPORT' , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (115 , 'MARKETING'           , 'SEASIDE, CA');
INSERT INTO DEPT VALUES (116 , 'NETWORKING'          , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (117 , 'DIRECTORS OFFICE'    , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (118 , 'ASSISTANTS'          , 'WEXFORD, PA');
INSERT INTO DEPT VALUES (119 , 'COMMUNICATIONS'      , 'SEATTLE, WA');
INSERT INTO DEPT VALUES (120 , 'REGIONAL SUPPORT'    , 'PORTLAND, OR');
COMMIT;


/*
 * -----------------------------------------------------------------------------
 * CREATE PROCEDURE "set_employee_salary"
 * -----------------------------------------------------------------------------
 */
CREATE OR REPLACE PROCEDURE set_employee_salary (
      p_emp_id  IN emp.emp_id%TYPE
    , p_factor  IN NUMBER
) AS

    v_monthly_salary  emp.monthly_salary%TYPE;

BEGIN

    -- Query employees current salary given the supplied emp_id.
    SELECT NVL(monthly_salary, -999)
        INTO   v_monthly_salary
        FROM   emp
        WHERE  emp_id = p_emp_id;

    -- If the record exists for the given employee, update their salary
    IF (v_monthly_salary != -999) THEN
        UPDATE emp
            SET    monthly_salary = monthly_salary * p_factor;

        COMMIT;
        
    END IF;
  
END;
/


/*
 * -----------------------------------------------------------------------------
 * CREATE FUNCTION "get_employee_salary"
 * -----------------------------------------------------------------------------
 */
CREATE OR REPLACE FUNCTION get_employee_salary (
    p_emp_id  IN emp.emp_id%TYPE
) RETURN emp.monthly_salary%TYPE
AS

    v_monthly_salary  emp.monthly_salary%TYPE;

BEGIN

    -- Query employees current salary given the supplied emp_id.
    SELECT NVL(monthly_salary, -999)
        INTO   v_monthly_salary
        FROM   emp
        WHERE  emp_id = p_emp_id;

    RETURN v_monthly_salary;
  
END;
/


/*
 * -----------------------------------------------------------------------------
 * CREATE PACKAGE "ref_cursor_package"
 * -----------------------------------------------------------------------------
 */

CREATE OR REPLACE PACKAGE ref_cursor_package
AS

    TYPE t_ref_cursor IS REF CURSOR;
    FUNCTION get_dept_ref_cursor(p_dept_id INTEGER) RETURN t_ref_cursor;

END;
/

CREATE OR REPLACE PACKAGE BODY ref_cursor_package
AS

    FUNCTION get_dept_ref_cursor (p_dept_id INTEGER)
        RETURN t_ref_cursor IS

        dept_ref_cursor t_ref_cursor;

    BEGIN

        -- +-----------------------------------------------------------+
        -- | Obtain a REF CURSOR. The following code is used to open   |
        -- | a curosr and retrieve the dept_id, name, and location     |
        -- | columns from the DEPT table. The reference to this        |
        -- | cursor (the REF CURSOR) is then returned by the function. |
        -- | This REF CURSOR may then be used to read the column       |
        -- | values from either other PL/SQL code, JDBC, or other      |
        -- | applications.                                             |
        -- +-----------------------------------------------------------+
        
        OPEN dept_ref_cursor FOR
            SELECT dept_id, name, location
            FROM   dept
            WHERE  dept_id > p_dept_id
            ORDER BY dept_id;

        RETURN dept_ref_cursor;

    END get_dept_ref_cursor;

END ref_cursor_package;
/