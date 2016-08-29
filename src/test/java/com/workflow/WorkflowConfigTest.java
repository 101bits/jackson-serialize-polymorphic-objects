package com.workflow;

import com.datasources.DataSource;
import com.datasources.FileDataSource;
import com.datasources.RemoteDataSource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorkflowConfigTest {
    @Test
    public void testSerializeFileDataSource() throws Exception {
        DataSource dataSource = new FileDataSource("/tmp/", ",", ".*");
        WorkflowConfig config = new WorkflowConfig("fileWorkflowConfig", dataSource);

        ObjectMapper mapper = new ObjectMapper();
        String configAsJsonString = mapper.writeValueAsString(config);

        System.out.println(configAsJsonString);
        // assert JSON deserialization works
        {
            JsonNode node = mapper.readTree(configAsJsonString);
            assertEquals(config.getName(), node.get("name").asText());
            assertEquals(((FileDataSource) dataSource).getPath(), node.get("dataSource").get("path").asText());
            assertEquals(((FileDataSource) dataSource).getDelimiter(), node.get("dataSource").get("delimiter").asText());
            assertEquals(((FileDataSource) dataSource).getFileNamePattern(), node.get("dataSource").get("fileNamePattern").asText());
        }
    }

    @Test
    public void testSerializeRemoteDataSource() throws Exception {
        DataSource dataSource = new RemoteDataSource("host", 0, "root", "123");
        WorkflowConfig config = new WorkflowConfig("fileWorkflowConfig", dataSource);

        ObjectMapper mapper = new ObjectMapper();
        String configAsJsonString = mapper.writeValueAsString(config);

        System.out.println(configAsJsonString);
        // assert JSON deserialization works
        {
            JsonNode node = mapper.readTree(configAsJsonString);
            assertEquals(config.getName(), node.get("name").asText());
            assertEquals(((RemoteDataSource) dataSource).getHost(), node.get("dataSource").get("host").asText());
            assertEquals(((RemoteDataSource) dataSource).getPort(), node.get("dataSource").get("port").asInt());
            assertEquals(((RemoteDataSource) dataSource).getUserName(), node.get("dataSource").get("userName").asText());
            assertEquals(((RemoteDataSource) dataSource).getPassword(), node.get("dataSource").get("password").asText());
        }
    }
}