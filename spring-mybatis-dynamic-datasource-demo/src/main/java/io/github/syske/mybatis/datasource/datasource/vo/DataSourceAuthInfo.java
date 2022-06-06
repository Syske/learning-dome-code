package io.github.syske.mybatis.datasource.datasource.vo;

public class DataSourceAuthInfo {
    private String username;

    private String password;

    private String jdbcUrl;

    private Integer maxPoolSize;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DataSourceAuthInfo{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", url='").append(jdbcUrl).append('\'');
        sb.append(", maxPoolSize=").append(maxPoolSize);
        sb.append('}');
        return sb.toString();
    }
}
