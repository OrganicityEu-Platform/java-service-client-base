# java-service-client-base

This repository offers a base Class to create clients for APIs that use the Organicity AAA component.

It is based on SpringBoot and RestTemplate in order to exchange data.

To use it on your own project simply add :

    <dependencies>
        <dependency>
            <groupId>eu.organicity</groupId>
            <artifactId>java-service-client-base</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>organicity</id>
            <url>https://maven.organicity.eu/content/repositories/snapshots</url>
        </repository>
    </repositories>


And create a class by extending the `OrganicityServiceBaseClient` class:

    public class MyAwesomeServiceClient extends OrganicityServiceBaseClient {
        public MyAwesomeServiceClient(final String token) {
            super(token);
        }
    }
    
    
