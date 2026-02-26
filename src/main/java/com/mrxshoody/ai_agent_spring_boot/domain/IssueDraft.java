package com.mrxshoody.ai_agent_spring_boot.domain;

public class IssueDraft {
    private String body;
    private String title;

    public IssueDraft(String body, String title) {
        this.body = body;
        this.title = title;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}


