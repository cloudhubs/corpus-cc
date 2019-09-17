package edu.baylor.ecs.models;

public class BCEToken {

    private String tokenValue;
    private String node;

    private BCEToken() {
        // Private for serialization
    }

    public BCEToken(String tokenValue, String node) {
        this.tokenValue = tokenValue;
        this.node = node.getClass().getSimpleName();
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

}
