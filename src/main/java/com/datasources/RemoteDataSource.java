package com.datasources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RemoteDataSource implements DataSource {
    private String host;
    private int port;
    private String userName;
    private String password;

    @JsonCreator
    public RemoteDataSource(@JsonProperty("host") String host,
                            @JsonProperty("port") int port,
                            @JsonProperty("userName") String userName,
                            @JsonProperty("password") String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port, userName, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RemoteDataSource dataSource = (RemoteDataSource) obj;
        return port == dataSource.port &&
                Objects.equals(host, dataSource.host) &&
                Objects.equals(userName, dataSource.userName) &&
                Objects.equals(password, dataSource.password);
    }
}
