package ai.openfabric.api.service;

import ai.openfabric.api.model.Worker;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WorkerService {

    Page<Worker> getAllWorkers(int pageNumber,int pageSize);

    Optional<Worker> getWorkerById(String workerId);

    void startWorker(String workerId);

    void stopWorker(String workerId);
}
