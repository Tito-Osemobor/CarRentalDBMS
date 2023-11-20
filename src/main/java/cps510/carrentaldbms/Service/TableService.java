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
                    "ORDER BY PRIC"
        ));
        List<List<Map<String, Object>>> results = new ArrayList<>();
        queries.forEach(query -> {
            List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(query);
            results.add(queryResult);
        });
        return results;
    }
}
