package com.mrxshoody.ai_agent_spring_boot.mcp.dto;

public class JsonRpcResponse {
    private String jsonrpc = "2.0";
    private Object result;
    private Object error;
    private Object id;

    public JsonRpcResponse() {}

    public String getJsonrpc() { return jsonrpc; }
    public void setJsonrpc(String jsonrpc) { this.jsonrpc = jsonrpc; }

    public Object getResult() { return result; }
    public void setResult(Object result) { this.result = result; }

    public Object getError() { return error; }
    public void setError(Object error) { this.error = error; }

    public Object getId() { return id; }
    public void setId(Object id) { this.id = id; }
}

