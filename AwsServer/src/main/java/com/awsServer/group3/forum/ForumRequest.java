package com.awsServer.group3.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumRequest {
    private String username;
    private String name;
    private String category;
    private String description;
}
