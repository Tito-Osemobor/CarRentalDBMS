package cps510.carrentaldbms.Service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class TableService {
    private final JdbcTemplate jdbcTemplate;

    public TableService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAllTableNames() {
        String query = "SELECT table_name FROM all_tables WHERE owner = 'OOSEMOBO'";
        return jdbcTemplate.queryForList(query, String.class);
    }

    public String createNewTable(String tableName, String sql) {
        jdbcTemplate.execute(sql);
        return tableName.toUpperCase() + " successfully created!";
    }

    public String dropTable(String tableName) {
        String dropTableSQL = "DROP TABLE " + tableName;
        jdbcTemplate.execute(dropTableSQL);
        return tableName.toUpperCase() + " successfully dropped!";
    }

    public List<Map<String, Object>> tableContent(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.queryForList(sql);
    }

    public List<List<Map<String, Object>>> queryResults() {
        ArrayList<String> queries = new ArrayList<>(Arrays.asList(
            "SELECT\n" +
                    "c.MAKE,\n" +
                    "c.MODEL,\n" +
                    "c.YEAR,\n" +
                    "c.PRICE\n" +
                    "FROM CAR c\n" +
                    "ORDER BY PRICE",
                "SELECT\n" +
                        "c.EMAIL,\n" +
                        "c.PHONE_NUMBER,\n" +
                        "c.AGREEMENT_ID,\n" +
                        "c. CREDENTIAL\n" +
                        "FROM CUSTOMER c\n" +
                        "where c.AGREEMENT_ID is not null",
                "SELECT c.CAR_TYPE, cr.MAKE, cr.MODEL\n" +
                        "FROM category c\n" +
                        "JOIN car_category  ON c.category_id = car_category.category_id\n" +
                        "JOIN car cr 1 ON cr.car_id = car_category.car_id\n" +
                        "WHERE car_category.car_id = 9",
                "SELECT cc.category_id, c.CAR_TYPE, COUNT(*) AS category_count\n" +
                        "FROM car_category cc\n" +
                        "JOIN category c  ON cc.category_id = c.category_id\n" +
                        "GROUP BY cc.category_id, c.CAR_TYPE\n",
                "SELECT DISTINCT make, COUNT(make) as model_count\n" +
                        "FROM CAR\n" +
                        "GROUP BY make",
                "SELECT\n" +
                        "e.employee_id,\n" +
                        "COUNT(ra.AGREEMENT_ID) AS rental_agreement_count\n" +
                        "FROM\n" +
                        "employee e\n" +
                        "LEFT JOIN\n" +
                        "rental_agreement ra  ON e.employee_id = ra.employee_id\n" +
                        "GROUP BY\n" +
                        "e.employee_id",
                "SELECT m.CAR_ID, c.MAKE, c.MODEL\n" +
                        "FROM MAINTENANCE m\n" +
                        "JOIN CAR c on m.CAR_ID = c.CAR_ID",
                "SELECT a.AGREEMENT_ID, c.MAKE || ' ' || c.MODEL as CAR, a.CUSTOMER_ID, e.NAME, a.RETURN_DATE\n" +
                        "FROM RENTAL_AGREEMENT a\n" +
                        "JOIN CAR c on a.CAR_ID = c.CAR_ID\n" +
                        "JOIN EMPLOYEE e on a.EMPLOYEE_ID = e.EMPLOYEE_ID\n" +
                        "WHERE a. RETURN_DATE >= CURRENT_DATE",



                "SELECT a.CUSTOMER_ID, cc.EMAIL as CUSTOMER_EMAIL, c.MAKE || ' ' || c.MODEL as CAR\n" +
                        "FROM RENTAL_AGREEMENT a\n" +
                        "INNER JOIN OOSEMOBO.CAR c on c.CAR_ID = a.CAR_ID\n" +
                        "INNER JOIN OOSEMOBO.CUSTOMER cc on a. AGREEMENT_ID = cc. AGREEMENT_ID",
                "SELECT e.EMPLOYEE_ID as EMPLOYEE_ID, e.NAME as EMPLOYEE_NAME, COUNT(*) as PUT_UNDER_MAINTENANCE\n" +
                        "FROM MAINTENANCE M\n" +
                        "JOIN OOSEMOBO.EMPLOYEE e on e.EMPLOYEE_ID = m. EMPLOYEE_ID\n" +
                        "GROUP BY e. EMPLOYEE_ID, e.NAME",
                "SELECT E.NAME AS Employee_Name, SUM(R.TOTAL_PRICE) AS Total_Sales\n" +
                        "FROM EMPLOYEE E\n" +
                        "LEFT JOIN RENTAL_AGREEMENT R ON E.EMPLOYEE_ID = R. EMPLOYEE_ID\n" +
                        "GROUP BY E. NAME",
                "SELECT C.CUSTOMER_ID, C.EMAIL AS Customer_Email, COUNT(R.AGREEMENT_ID) AS Active_Rentals\n" +
                        "FROM CUSTOMER C\n" +
                        "INNER JOIN RENTAL_AGREEMENT R ON C.CUSTOMER_ID = R. CUSTOMER_ID\n" +
                        "WHERE R. RETURN_DATE >= SYSDATE\n" +
                        "GROUP BY C.CUSTOMER_ID, C.EMAIL\n" +
                        "HAVING COUNT(R. AGREEMENT_ID) > 1",
                "SELECT C.CUSTOMER_ID, C.EMAIL AS Customer_Email\n" +
                        "FROM CUSTOMER C\n" +
                        "WHERE NOT EXISTS (\n" +
                        "SELECT 1\n" +
                        "FROM RENTAL_AGREEMENT R\n" +
                        "WHERE C.CUSTOMER_ID = R.CUSTOMER_ID AND R. RETURN_DATE >= SYSDATE)",
                "SELECT CAR.MAKE AS Car_Make, CAR. MODEL AS Car_Model\n" +
                        "FROM CAR\n" +
                        "INNER JOIN CAR_CATEGORY ON CAR.CAR_ID = CAR_CATEGORY.CAR_ID\n" +
                        "INNER JOIN CATEGORY ON CAR_CATEGORY. CATEGORY_ID = CATEGORY. CATEGORY_ID\n" +
                        "GROUP BY CAR.MAKE, CAR. MODEL\n" +
                        "HAVING COUNT(DISTINCT CATEGORY. CAR_TYPE) > 1",
                "SELECT CAR.MAKE AS Car_Make, CAR.MODEL AS Car_Model, COUNT(R.AGREEMENT_ID) AS Rental_Count\n" +
                        "\tFROM CAR\n" +
                        "\t\tLEFT JOIN RENTAL_AGREEMENT R ON CAR.CAR_ID = R.CAR_ID\n" +
                        "GROUP BY CAR.MAKE, CAR.MODEL\n" +
                        "HAVING COUNT(R.AGREEMENT_ID) = (\n" +
                        "\tSELECT MAX(RentalCount)\n" +
                        "FROM (SELECT CAR_ID, COUNT(AGREEMENT_ID) AS RentalCount FROM RENTAL_AGREEMENT GROUP BY CAR_ID)\n" +
                        ")",
                "SELECT CUSTOMER_ID AS ID, EMAIL AS Contact, 'Customer' AS Credential FROM CUSTOMER\n" +
                        "UNION\n" +
                        "SELECT EMPLOYEE_ID AS ID, NAME AS Contact, 'Employee' AS Role FROM EMPLOYEE"


        ));
        List<List<Map<String, Object>>> results = new ArrayList<>();
        queries.forEach(query -> {
            List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(query);
            results.add(queryResult);
        });
        return results;
    }
}
