package utils;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class DynamoDBConfig {

    // Singleton Class, only instantiate the mapper ONCE!!
    public DynamoDBMapper mapper;
    public AmazonDynamoDB clientDb;
    public DynamoDBConfig(){
        this.mapper = dynamoDBMapper();
    }

    public DynamoDBMapper dynamoDBMapper(){
        // set the mapper from strong consistency since we dont really care about latency
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
        clientDb = buildAmazonDynamoDB();
        return new DynamoDBMapper(clientDb, mapperConfig);
    }

    private AmazonDynamoDB buildAmazonDynamoDB(){
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "dynamodb.us-east-2.amazonaws.com",
                                "us-east-2"
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        "AKIAZKKEKJ734T2LXKJO",
                                        "aCC/nYWJzRTSQ8kb/N3wgzIS/XzOrWjcdhE/uC/w"
                                )
                        )
                )
                .build();
    }
}
