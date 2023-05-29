package ai.openfabric.api.service.impl;

import ai.openfabric.api.model.WorkerStatistics;
import ai.openfabric.api.repository.WorkerStatisticsRepository;
import ai.openfabric.api.service.WorkerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class WorkerStatisticsServiceImpl implements WorkerStatisticsService {

    @Autowired
    private WorkerStatisticsRepository workerStatisticsRepository;
    @Override
    public Optional<WorkerStatistics> getWorkerStatistics(String workerId) {
        return workerStatisticsRepository.findById (workerId);
    }
}