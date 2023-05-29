package ai.openfabric.api.controller;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.model.WorkerStatistics;
import ai.openfabric.api.service.WorkerService;
import ai.openfabric.api.service.WorkerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WorkerStatisticsService workerStatisticsService;

    @PostMapping(path = "/hello")
    public @ResponseBody String hello(@RequestBody String name) {
        return "Hello!" + name;
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public Page<Worker> getPaginatedWorkers(
            @PathVariable int pageNumber,
            @PathVariable int pageSize) {
        return workerService.getAllWorkers (pageNumber, pageSize);
    }
    @GetMapping("/{workerId}")
    public ResponseEntity<Worker> getWorkerDetails(@PathVariable String workerId) {
        Optional<Worker> worker = workerService.getWorkerById (workerId);
        return worker.map (ResponseEntity::ok).orElseGet (() -> ResponseEntity.notFound ().build ());
    }

    @PostMapping("/{workerId}/start")
    public ResponseEntity<String> startWorker(@PathVariable String workerId) {
        try {
            workerService.startWorker(workerId);
            return ResponseEntity.ok("Worker started successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start worker: " + e.getMessage());
        }
    }

    @PostMapping("/{workerId}/stop")
    public ResponseEntity<String> stopWorker(@PathVariable String workerId) {
        try {
            workerService.stopWorker(workerId);
            return ResponseEntity.ok("Worker stopped successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to stop worker: " + e.getMessage());
        }
    }

    @GetMapping("/statistics/{id}")
    public ResponseEntity<WorkerStatistics> getWorkerStatistics(@PathVariable String workerId) {
        Optional<WorkerStatistics> optionalWorkerStatistics = workerStatisticsService.getWorkerStatistics(workerId);
        return optionalWorkerStatistics.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
