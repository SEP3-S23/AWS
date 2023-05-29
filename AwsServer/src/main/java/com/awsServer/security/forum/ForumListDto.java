package com.awsServer.security.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumListDto {
    private Date timeCreation;
    private String name;
    private String description;
    private String category;
}
