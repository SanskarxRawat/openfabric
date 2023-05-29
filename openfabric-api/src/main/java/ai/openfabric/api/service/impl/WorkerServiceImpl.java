package ai.openfabric.api.service.impl;

import ai.openfabric.api.exception.WorkerNotFoundException;
import ai.openfabric.api.model.Worker;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.service.WorkerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private DockerClient dockerClient;

    @Override
    public Page<Worker> getAllWorkers(int pageNumber, int pageSize) {
        PageRequest pageRequest=PageRequest.of (pageNumber,pageSize);
        return workerRepository.findAll (pageRequest);
    }

    @Override
    public Optional<Worker> getWorkerById(String workerId) {
        return workerRepository.findById (workerId);
    }

    @Override
    public void startWorker(String workerId) {
        Worker worker = workerRepository.findById(workerId).orElseThrow(() -> new IllegalArgumentException("Worker not found with given Id"));

        CreateContainerCmd createContainerCmd=dockerClient
                .createContainerCmd (worker.getImageName ())
                .withCmd ("java", "-jar", "worker.jar")
                .withName ("worker-" + worker.getId());

        String containerId = createContainerCmd.exec().getId();
        dockerClient.startContainerCmd(containerId).exec();
    }

    @Override
    public void stopWorker(String workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new IllegalArgumentException("Worker not found with given Id"));

        String containerName = "worker-" + worker.getId();
        try {
            dockerClient.stopContainerCmd(containerName).exec();
        } catch (Exception e) {
            log.error("Failed to stop the worker: {}", e.getMessage());
            throw new WorkerNotFoundException ("Failed to stop the worker with given Id.");
        }
    }
}