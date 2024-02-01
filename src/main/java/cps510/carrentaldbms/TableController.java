package cps510.carrentaldbms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * This method services as the API controller.
 * This is where our endpoints are defined
 */

@RestController
@RequestMapping("/carrentaldbms/tables")
@CrossOrigin(origins = "*")
public class TableController {
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

    // This "/{tableName}/columns" endpoint returns the names of the columns in a table specified
    @GetMapping("/{tableName}/columns")
    public ResponseEntity<?> getTableColumns(@PathVariable String tableName) {
        return ResponseEntity.ok(tableService.tableColumns(tableName));
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

    /*
     * This "/{tableName}/new" endpoint is used to create new records
     * It does this by taking in two parameters:
     * @params "tableName" - Name of table in question
     * @params "record" - This holds the content of the record in String Key : Object Value pairs
     */
    @PostMapping("/{tableName}/new")
    public ResponseEntity<?> addNewRecord(@PathVariable String tableName,
                                          @RequestBody Map<String, Object> record) {
        return ResponseEntity.ok(tableService.addRecord(tableName, record));
    }

    /*
     * This "/{tableName}/delete" endpoint is used to delete specific records
     * It does this by taking in two parameters:
     * @params "tableName" - Name of table in question
     * @params "record" - This holds the content of the record in String Key : Object Value pairs
     */
    @DeleteMapping("/{tableName}/delete")
    public ResponseEntity<?> deleteExistingRecord(@PathVariable String tableName,
                                                  @RequestBody Map<String, Object> record) {
        return ResponseEntity.ok(tableService.deleteRecord(tableName, record));
    }

    /*
     * This "/{tableName}/update" endpoint is used to update specific records
     * It does this by taking in two parameters:
     * @params "tableName" - Name of table in question
     * @params "updateRequest" - This object that holds two keys - selectedRecord and updatedRecord which are needed to update the record using the new values
     */
    @PutMapping("/{tableName}/update")
    public ResponseEntity<?> updateExistingRecord(
            @PathVariable String tableName,
            @RequestBody Map<String, Object> updateRequest) {
        Map<String, Object> selectedRecord = (Map<String, Object>) updateRequest.get("selectedRecord");
        Map<String, Object> updatedRecord = (Map<String, Object>) updateRequest.get("updatedRecord");

        return ResponseEntity.ok(tableService.updateRecord(tableName, selectedRecord, updatedRecord));
    }

}