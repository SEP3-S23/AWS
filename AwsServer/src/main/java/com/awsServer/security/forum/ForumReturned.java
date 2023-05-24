package com.awsServer.security.forum;

import com.awsServer.security.post.Post;
import com.awsServer.security.post.PostReturned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumReturned {
    private Date timeCreation;
    private String name;
    private String description;
    private String category;
    private List<PostReturned> posts;
}
