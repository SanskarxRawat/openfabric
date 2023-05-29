package ai.openfabric.api.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Configuration
@PropertySource("classpath:docker.properties")
public class DockerConfiguration {

    @Value("${docker.host}")
    private String dockerHost;

    @Value("${docker.tls-verify}")
    private boolean dockerTlsVerify;

    @Value("${docker.cert-path}")
    private String dockerCertPath;

    @Value("${docker.registry-username}")
    private String registryUsername;

    @Value("${docker.registry-password}")
    private String registryPassword;

    @Value("${docker.registry-email}")
    private String registryEmail;

    @Value("${docker.registry-url}")
    private String registryUrl;

    @Bean
    public DockerClient dockerClient() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                .withDockerTlsVerify(dockerTlsVerify)
                .withDockerCertPath(dockerCertPath)
                .withRegistryUsername(registryUsername)
                .withRegistryPassword(registryPassword)
                .withRegistryEmail(registryEmail)
                .withRegistryUrl(registryUrl)
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        return DockerClientImpl.getInstance(config, httpClient);
    }
}
