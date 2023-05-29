package ai.openfabric.api.service;

import ai.openfabric.api.model.WorkerStatistics;

import java.util.Optional;

public interface WorkerStatisticsService {

    Optional<WorkerStatistics> getWorkerStatistics(String workerId);
}
