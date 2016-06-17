import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring Boot main bootstrap.Application
 *
 */
@Configuration @EnableAutoConfiguration @EnableWebMvc
@EnableElasticsearchRepositories(basePackages = "repository")
@ComponentScan(basePackages = { "controller", "repository", "batch", "service" })
@PropertySource("classpath:/resources/application.properties")
@ImportResource("classpath:/resources/bean.xml")
public class Application {
    public static void main( String[] args ) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        Settings.Builder elasticsearchSettings =
                ImmutableSettings.settingsBuilder()
                        .put("http.port", 9300)
                        .put("transport.tcp.port", 8085)
                        .put("cluster.name", "ms.elastic")
                        .put("node.local", "true")
                        .put("http.enabled", "false")
                        .put("path.data", "/home/alex/projects/serverSideTranslator/elasticSearchMicroService/src/main/resources") //TODO rewrite on property
                        .put("path.home", "/usr/share/elasticsearch");

//        logger.debug(tmpDir.toAbsolutePath().toString());

        return new ElasticsearchTemplate(nodeBuilder()
                .local(true)
                .settings(elasticsearchSettings.build())
                .node()
                .client());
    }
}
