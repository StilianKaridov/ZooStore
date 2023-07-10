package com.tinqin.zoostore.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AppConfig {

    private static final String CLOUD_NAME_LABEL = "cloud_name";
    private static final String API_KEY_LABEL = "api_key";
    private static final String API_SECRET_LABEL = "api_secret";

    private final CloudinaryConfig config;

    @Autowired
    public AppConfig(CloudinaryConfig config) {
        this.config = config;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        CLOUD_NAME_LABEL, config.getCloudName(),
                        API_KEY_LABEL, config.getApiKey(),
                        API_SECRET_LABEL, config.getApiSecret()
                )
        );
    }
}
