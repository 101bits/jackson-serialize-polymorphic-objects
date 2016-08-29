package com.datasources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class FileDataSource implements DataSource {
    private String path;
    private String delimiter;
    private String fileNamePattern;

    @JsonCreator
    public FileDataSource(@JsonProperty("path") String path,
                          @JsonProperty("delimiter") String delimiter,
                          @JsonProperty("fileNamePattern") String fileNamePattern) {
        this.path = path;
        this.delimiter = delimiter;
        this.fileNamePattern = fileNamePattern;
    }

    public String getPath() {
        return path;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, delimiter, fileNamePattern);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FileDataSource dataSource = (FileDataSource) obj;
        return Objects.equals(path, dataSource.path) &&
                Objects.equals(delimiter, dataSource.delimiter) &&
                Objects.equals(fileNamePattern, dataSource.fileNamePattern);
    }
}
