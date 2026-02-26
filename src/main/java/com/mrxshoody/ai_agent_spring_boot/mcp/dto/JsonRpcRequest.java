package com.mrxshoody.ai_agent_spring_boot.mcp.dto;

public class JsonRpcRequest {
    private String jsonrpc = "2.0";
    private String method;
    private Object params;
    private Object id;

    public JsonRpcRequest() {}

    public JsonRpcRequest(String method, Object params, Object id) {
        this.method = method;
        this.params = params;
        this.id = id;
    }

    public String getJsonrpc() { return jsonrpc; }
    public void setJsonrpc(String jsonrpc) { this.jsonrpc = jsonrpc; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public Object getParams() { return params; }
    public void setParams(Object params) { this.params = params; }

    public Object getId() { return id; }
    public void setId(Object id) { this.id = id; }
}

