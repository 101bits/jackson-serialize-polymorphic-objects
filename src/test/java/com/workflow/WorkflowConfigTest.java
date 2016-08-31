package com.workflow;

import com.datasources.DataSource;
import com.datasources.FileDataSource;
import com.datasources.RemoteDataSource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WorkflowConfigTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

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
    public void testSerializeAndSerializeFileDataSourceFromFile() throws Exception {
        File fileDataSource = temporaryFolder.newFile("fileDataSource.json");

        FileDataSource dataSource = new FileDataSource("/tmp/", ",", ".*");
        WorkflowConfig config = new WorkflowConfig("fileWorkflowConfig", dataSource);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(fileDataSource, config);

        assertTrue(fileDataSource.length() > 0);

        WorkflowConfig configFromFile = mapper.readValue(fileDataSource, WorkflowConfig.class);
        assertNotNull(configFromFile);
        assertEquals(dataSource.getPath(), ((FileDataSource) configFromFile.getDataSource()).getPath());
        assertEquals(dataSource.getDelimiter(), ((FileDataSource) configFromFile.getDataSource()).getDelimiter());
        assertEquals(dataSource.getFileNamePattern(), ((FileDataSource) configFromFile.getDataSource()).getFileNamePattern());
    }

    @Test
    public void testSerializeAndSerializeRemoteDataSourceFromFile() throws Exception {
        File remoteDataSource = temporaryFolder.newFile("remoteDataSource.json");

        RemoteDataSource dataSource = new RemoteDataSource("host", 0, "root", "abc");
        WorkflowConfig config = new WorkflowConfig("fileWorkflowConfig", dataSource);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(remoteDataSource, config);

        assertTrue(remoteDataSource.length() > 0);

        WorkflowConfig configFromFile = mapper.readValue(remoteDataSource, WorkflowConfig.class);
        assertNotNull(configFromFile);
        assertEquals(dataSource.getHost(), ((RemoteDataSource) configFromFile.getDataSource()).getHost());
        assertEquals(dataSource.getPort(), ((RemoteDataSource) configFromFile.getDataSource()).getPort());
        assertEquals(dataSource.getUserName(), ((RemoteDataSource) configFromFile.getDataSource()).getUserName());
        assertEquals(dataSource.getPassword(), ((RemoteDataSource) configFromFile.getDataSource()).getPassword());
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