package cps510.carrentaldbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * This method services as the API controller.
 * This is where our endpoints are defined
 */

@RestController
@RequestMapping("/carrentaldbms/tables")
@CrossOrigin(origins = "*")
public class TableController {
    @Autowired
    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // This "/list" endpoint returns a list of names of Tables in our database
    @GetMapping("/list")
    public ResponseEntity<List<String>> getTableNames() {
        return ResponseEntity.ok(tableService.getAllTableNames());
    }

    // This "/{tableName}" endpoint returns records in a table specified
    @GetMapping("/{tableName}")
    public ResponseEntity<?> getTableContent(@PathVariable String tableName) {
        return ResponseEntity.ok(tableService.tableContent(tableName));
    }

    // This "/new" endpoint is used to create tables in our database
    @PostMapping("/new")
    public ResponseEntity<?> createNewTable(@RequestBody TableRequest tableRequest) {
        return ResponseEntity.ok(tableService.createNewTable(tableRequest.getTableName(), tableRequest.getSql()));
    }

    // This "/drop" endpoint is used to delete tables in our database
    @DeleteMapping("/drop")
    public ResponseEntity<?> dropTable(@RequestParam String tableName) {
        return ResponseEntity.ok(tableService.dropTable(tableName));
    }

    // This "/queries" endpoint is used to return the result of different advanced queries created in Assignment 4
    @GetMapping("/queries")
    public ResponseEntity<?> getQueryResults() {
        return ResponseEntity.ok(tableService.queryResults());
    }

}