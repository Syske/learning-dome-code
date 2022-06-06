package io.github.syske.mybatis.datasource.datasource.vo;

public class CorpDataSourceNode {
    private String node;

    private DataSourceAuthInfo config;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public DataSourceAuthInfo getConfig() {
        return config;
    }

    public void setConfig(DataSourceAuthInfo config) {
        this.config = config;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CorpDataSourceNode{");
        sb.append("node='").append(node).append('\'');
        sb.append(", config=").append(config);
        sb.append('}');
        return sb.toString();
    }
}
