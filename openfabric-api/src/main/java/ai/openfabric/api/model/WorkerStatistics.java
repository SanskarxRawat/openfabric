package ai.openfabric.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "worker_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private Worker worker;

    @Column(name = "total_tasks")
    private Integer totalTasks;

    @Column(name = "successful_tasks")
    private Integer successfulTasks;

    @Column(name = "failed_tasks")
    private Integer failedTasks;

    @Column(name = "pending_tasks")
    private Integer pendingTasks;

    @Column(name = "in_progress_tasks")
    private Integer inProgressTasks;

    @Column(name = "completed_tasks")
    private Integer completedTasks;

    @Column(name = "error_tasks")
    private Integer errorTasks;
}
