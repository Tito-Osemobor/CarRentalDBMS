package cps510.carrentaldbms;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * This is a service layer class. Used to interact with our database!
 */
@Service
public class TableService {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TableService(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /*
     * @param NULL
     * @Description - Gets all the names of Tables in our database
     */
    public List<String> getAllTableNames() {
        // Getting all tables were the owner is 'OOSEMOBO'
        String query = "SELECT table_name FROM all_tables WHERE owner = 'OOSEMOBO'";
        return jdbcTemplate.queryForList(query, String.class);
    }

    /*
     * @param tableName - Name of the table you are creating
     * @param sql - Contains the SQL code that creates the table in question
     * @Description - Creates a new table in our database
     */
    public String createNewTable(String tableName, String sql) {
        jdbcTemplate.execute(sql);
        return tableName.toUpperCase() + " successfully created!";
    }

    /*
     * @param tableName - Name of the table you are dropping
     * @Description - Drops the table from our database
     */
    public String dropTable(String tableName) {
        String dropTableSQL = "DROP TABLE " + tableName;
        jdbcTemplate.execute(dropTableSQL);
        return tableName.toUpperCase() + " successfully dropped!";
    }

    /*
     * @param tableName - Name of the table trying to obtain data about
     * @Description - Gets all records from a particular table
     */
    public List<Map<String, Object>> tableContent(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.queryForList(sql);
    }

    /*
     * @param NULL
     * @Description - Returns the results of all the advances queries created for Assignment 4
     */
    public List<List<Map<String, Object>>> queryResults() {
        ArrayList<String> queries = new ArrayList<>(Arrays.asList(
                """
                        SELECT
                        c.MAKE,
                        c.MODEL,
                        c.YEAR,
                        c.PRICE
                        FROM CAR c
                        ORDER BY PRICE""",
                """
                        SELECT
                        c.EMAIL,
                        c.PHONE_NUMBER,
                        c.AGREEMENT_ID,
                        c. CREDENTIAL
                        FROM CUSTOMER c
                        where c.AGREEMENT_ID is not null""",
                """
                        SELECT c.CAR_TYPE, cr.MAKE, cr.MODEL
                        FROM category c
                        JOIN car_category ON c.category_id = car_category.category_id
                        JOIN car cr ON cr.car_id = car_category.car_id
                        WHERE car_category.car_id = 9""",
                """
                        SELECT cc.category_id, c.CAR_TYPE, COUNT(*) AS category_count
                        FROM car_category cc
                        JOIN category c  ON cc.category_id = c.category_id
                        GROUP BY cc.category_id, c.CAR_TYPE
                        """,
                """
                        SELECT DISTINCT make, COUNT(make) as model_count
                        FROM CAR
                        GROUP BY make""",
                """
                        SELECT
                        e.employee_id,
                        COUNT(ra.AGREEMENT_ID) AS rental_agreement_count
                        FROM
                        employee e
                        LEFT JOIN
                        rental_agreement ra  ON e.employee_id = ra.employee_id
                        GROUP BY
                        e.employee_id""",
                """
                        SELECT m.CAR_ID, c.MAKE, c.MODEL
                        FROM MAINTENANCE m
                        JOIN CAR c on m.CAR_ID = c.CAR_ID""",
                """
                        SELECT a.AGREEMENT_ID, c.MAKE || ' ' || c.MODEL as CAR, a.CUSTOMER_ID, e.NAME, a.RETURN_DATE
                        FROM RENTAL_AGREEMENT a
                        JOIN CAR c on a.CAR_ID = c.CAR_ID
                        JOIN EMPLOYEE e on a.EMPLOYEE_ID = e.EMPLOYEE_ID
                        WHERE a. RETURN_DATE >= CURRENT_DATE""",
                """
                        SELECT a.CUSTOMER_ID, cc.EMAIL as CUSTOMER_EMAIL, c.MAKE || ' ' || c.MODEL as CAR
                        FROM RENTAL_AGREEMENT a
                        INNER JOIN OOSEMOBO.CAR c on c.CAR_ID = a.CAR_ID
                        INNER JOIN OOSEMOBO.CUSTOMER cc on a. AGREEMENT_ID = cc. AGREEMENT_ID""",
                """
                        SELECT e.EMPLOYEE_ID as EMPLOYEE_ID, e.NAME as EMPLOYEE_NAME, COUNT(*) as PUT_UNDER_MAINTENANCE
                        FROM MAINTENANCE M
                        JOIN OOSEMOBO.EMPLOYEE e on e.EMPLOYEE_ID = m. EMPLOYEE_ID
                        GROUP BY e. EMPLOYEE_ID, e.NAME""",
                """
                        SELECT E.NAME AS Employee_Name, SUM(R.TOTAL_PRICE) AS Total_Sales
                        FROM EMPLOYEE E
                        LEFT JOIN RENTAL_AGREEMENT R ON E.EMPLOYEE_ID = R. EMPLOYEE_ID
                        GROUP BY E. NAME""",
                """
                        SELECT C.CUSTOMER_ID, C.EMAIL AS Customer_Email, COUNT(R.AGREEMENT_ID) AS Active_Rentals
                        FROM CUSTOMER C
                        INNER JOIN RENTAL_AGREEMENT R ON C.CUSTOMER_ID = R. CUSTOMER_ID
                        WHERE R. RETURN_DATE >= SYSDATE
                        GROUP BY C.CUSTOMER_ID, C.EMAIL
                        HAVING COUNT(R. AGREEMENT_ID) > 1""",
                """
                        SELECT C.CUSTOMER_ID, C.EMAIL AS Customer_Email
                        FROM CUSTOMER C
                        WHERE NOT EXISTS (
                        SELECT 1
                        FROM RENTAL_AGREEMENT R
                        WHERE C.CUSTOMER_ID = R.CUSTOMER_ID AND R. RETURN_DATE >= SYSDATE)""",
                """
                        SELECT CAR.MAKE AS Car_Make, CAR. MODEL AS Car_Model
                        FROM CAR
                        INNER JOIN CAR_CATEGORY ON CAR.CAR_ID = CAR_CATEGORY.CAR_ID
                        INNER JOIN CATEGORY ON CAR_CATEGORY. CATEGORY_ID = CATEGORY. CATEGORY_ID
                        GROUP BY CAR.MAKE, CAR. MODEL
                        HAVING COUNT(DISTINCT CATEGORY. CAR_TYPE) > 1""",
                """
                        SELECT CAR.MAKE AS Car_Make, CAR.MODEL AS Car_Model, COUNT(R.AGREEMENT_ID) AS Rental_Count
                        \tFROM CAR
                        \t\tLEFT JOIN RENTAL_AGREEMENT R ON CAR.CAR_ID = R.CAR_ID
                        GROUP BY CAR.MAKE, CAR.MODEL
                        HAVING COUNT(R.AGREEMENT_ID) = (
                        \tSELECT MAX(RentalCount)
                        FROM (SELECT CAR_ID, COUNT(AGREEMENT_ID) AS RentalCount FROM RENTAL_AGREEMENT GROUP BY CAR_ID)
                        )""",
                """
                        SELECT CUSTOMER_ID AS ID, EMAIL AS Contact, 'Customer' AS Credential FROM CUSTOMER
                        UNION
                        SELECT EMPLOYEE_ID AS ID, NAME AS Contact, 'Employee' AS Role FROM EMPLOYEE"""

        ));
        List<List<Map<String, Object>>> results = new ArrayList<>();
        queries.forEach(query -> {
            List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(query);
            results.add(queryResult);
        });
        return results;
    }

    /*
     * @param tableName - Name of the table adding record to
     * @param record - Content of the new record we are adding
     * @Description - Gets all records from a particular table
     */
    public String addRecord(String tableName, Map<String, Object> record) {
        String columns = String.join(", ", record.keySet());
        String values = record.keySet().stream()
                .map(key -> "?")
                .collect(Collectors.joining(", "));

        String insertSql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        Object[] params = record.values().toArray();

        jdbcTemplate.update(insertSql, params);
        return "Record added successfully!";
    }

    /*
     * @param tableName - Name of the table trying to obtain data about
     * @Description - Gets all names of the columns in that table
     */
    public List<String> tableColumns(String tableName) {
        String sql = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?";
        return jdbcTemplate.queryForList(sql, String.class, tableName);
    }

    /*
     * @param tableName - Name of the table trying to obtain data about
     * @param record - Content of the record we are trying to delete
     * @Description - Using the content of the record a comparison is done on all the columns ensuring a perfect match
     */
    public String deleteRecord(String tableName,
                               Map<String, Object> record) {
        StringBuilder queryBuilder = new StringBuilder("DELETE FROM ");
        queryBuilder.append(tableName).append(" WHERE ");

        int columnIndex = 0;
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            if (columnIndex > 0) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(entry.getKey());
            if (entry.getValue() instanceof java.sql.Timestamp || entry.getValue() instanceof java.util.Date) {
                queryBuilder.append(" = TO_TIMESTAMP_TZ(?, 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZO')");
            } else {
                queryBuilder.append(" = ?");
            }
            columnIndex++;
        }
        jdbcTemplate.update(queryBuilder.toString(), record.values().toArray());
        return "Record deleted successfully!";
    }

    /*
     * @param tableName - Name of the table from which you are updating a record from
     * @param selectedRecord - Content of the record before being updated
     * @param updatedRecord - Content of the record after being updated
     * @Description - Using the content of the record a comparison is done on all the columns ensuring a perfect match and then set using
     *                the new values of the updated record
     */
    public String updateRecord(String tableName,
                               Map<String, Object> selectedRecord,
                               Map<String, Object> updatedRecord) {
        if (tableName == null || selectedRecord == null || updatedRecord == null || selectedRecord.isEmpty() || updatedRecord.isEmpty()) {
            return "Invalid input parameters.";
        }

        StringBuilder queryBuilder = new StringBuilder("UPDATE ");
        queryBuilder.append(tableName).append(" SET ");

        int columnIndex = 0;
        for (Map.Entry<String, Object> entry : updatedRecord.entrySet()) {
            if (columnIndex > 0) {
                queryBuilder.append(", ");
            }
            queryBuilder.append(entry.getKey()).append(" = :").append("new_").append(entry.getKey());
            columnIndex++;
        }

        queryBuilder.append(" WHERE ");

        columnIndex = 0;
        for (Map.Entry<String, Object> entry : selectedRecord.entrySet()) {
            if (columnIndex > 0) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(entry.getKey()).append(" = :").append("existing_").append(entry.getKey());
            columnIndex++;
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        for (Map.Entry<String, Object> entry : updatedRecord.entrySet()) {
            parameters.addValue("new_" + entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Object> entry : selectedRecord.entrySet()) {
            parameters.addValue("existing_" + entry.getKey(), entry.getValue());
        }

        int rowsAffected = namedParameterJdbcTemplate.update(queryBuilder.toString(), parameters);

        return rowsAffected > 0 ? "Record updated successfully!" : "No records updated.";
    }
}
