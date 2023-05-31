package com.awsServer.group3.post;

import com.awsServer.group3.comment.Comment;
import com.awsServer.group3.forum.Forum;
import com.awsServer.group3.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_post")
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String body;
    private Date date;
    private Integer like;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    @JsonIgnore
    private Forum forum;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
