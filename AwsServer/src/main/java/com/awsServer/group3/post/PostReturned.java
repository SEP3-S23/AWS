package com.awsServer.group3.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReturned {
    private String title;
    private String body;
    private Date date;
    private String username;
}
