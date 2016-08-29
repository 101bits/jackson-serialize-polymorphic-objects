package com.workflow;

import com.datasources.DataSource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class WorkflowConfig {
    private String name;
    private DataSource dataSource;

    @JsonCreator
    public WorkflowConfig(@JsonProperty("name") String name,
                          @JsonProperty("dataSource") DataSource dataSource) {
        this.name = name;
        this.dataSource = dataSource;
    }

    public String getName() {
        return name;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataSource);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkflowConfig that = (WorkflowConfig) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(dataSource, that.dataSource);
    }
}
