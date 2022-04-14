package com.aybu9.aybualumni.core.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonWebServicesConfig {
    // TODO: 31.10.2021  https://medium.com/analytics-vidhya/aws-s3-with-java-using-spring-boot-7f6fcf734aec
    // TODO: 31.10.2021  https://reflectoring.io/spring-boot-s3/

    // Access Key ID: AKIATVHBTWIJPX5CXT3J
    // Secret Access Key: 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

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                "AKIATVHBTWIJPX5CXT3J",
                "K99rPnGkTvbYF6f6HVsmuUZzJyZzQiJ5bkKLyAsz"
        );
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("eu-central-1")
                .build();
    }
}
