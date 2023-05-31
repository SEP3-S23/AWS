package com.awsServer.group3.comment;

import com.awsServer.group3.forum.Forum;
import com.awsServer.group3.post.Post;
import com.awsServer.group3.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_comment")
public class Comment {

    @Id
    private Integer id;
    private String text;

    @OneToOne
    @JoinColumn(name = "created_by")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

}
