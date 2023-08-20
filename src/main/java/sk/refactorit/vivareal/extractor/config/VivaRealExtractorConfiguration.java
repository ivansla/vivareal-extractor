package sk.refactorit.vivareal.extractor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.refactorit.vivareal.extractor.service.SearchResponseProcessor;
import sk.refactorit.vivareal.extractor.service.VivaRealExtractor;
import sk.refactorit.vivareal.extractor.service.VivaRealLastUpdateService;
import sk.refactorit.vivareal.extractor.service.VivaRealLoader;

@Configuration
public class VivaRealExtractorConfiguration {
    @Bean
    public VivaRealExtractor initVivaRealExtractor() {
        return new VivaRealExtractor();
    }
    @Bean
    public SearchResponseProcessor initSearchResponseProcessor() {
        return new SearchResponseProcessor();
    }
    @Bean
    public VivaRealLoader initVivaRealLoader() {
        return new VivaRealLoader();
    }
    @Bean
    public VivaRealLastUpdateService initVivaRealLastUpdateService() {
        return new VivaRealLastUpdateService();
    }
}
