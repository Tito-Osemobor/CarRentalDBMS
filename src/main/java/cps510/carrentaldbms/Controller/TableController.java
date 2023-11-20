package cps510.carrentaldbms.Controller;

import cps510.carrentaldbms.Entity.TableRequest;
import cps510.carrentaldbms.Service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<String>> getTableNames() {
        return ResponseEntity.ok(tableService.getAllTableNames());
    }

    @GetMapping("/{tableName}")
    public ResponseEntity<?> getTableContent(@PathVariable String tableName) {
        return ResponseEntity.ok(tableService.tableContent(tableName));
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewTable(@RequestBody TableRequest tableRequest) {
        return ResponseEntity.ok(tableService.createNewTable(tableRequest.getTableName(), tableRequest.getSql()));
    }

    @DeleteMapping("/drop")
    public ResponseEntity<?> dropTable(@RequestParam String tableName) {
        return ResponseEntity.ok(tableService.dropTable(tableName));
    }

    @GetMapping("/queries")
    public ResponseEntity<?> getQueryResults() {
        return ResponseEntity.ok(tableService.queryResults());
    }
}