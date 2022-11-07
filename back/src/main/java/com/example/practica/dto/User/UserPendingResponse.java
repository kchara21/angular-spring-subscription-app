package com.example.practica.dto.User;

import java.util.List;

public class UserPendingResponse {
    private List<UserPendingDTO> content;

    public List<UserPendingDTO> getContent() {
        return content;
    }

    public void setContent(List<UserPendingDTO> content) {
        this.content = content;
    }

    public UserPendingResponse(List<UserPendingDTO> content) {
        this.content = content;
    }

    public UserPendingResponse() {
        super();
    }
}
