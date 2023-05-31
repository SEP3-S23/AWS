package com.awsServer.group3.forum;

import com.awsServer.group3.post.Post;
import com.awsServer.group3.user.User;
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
@Table(name = "_forums")
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date timeCreation;
    private String name;
    private String description;
    private String category;

    @OneToMany(mappedBy = "forum")
    private List<Post> posts;

    @OneToOne
    @JoinColumn(name = "created_by")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "user_forum",
            joinColumns = @JoinColumn(name = "forum_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> followedUsers;

    public List<User> getFollowedUsers()
    {
        return followedUsers;
    }

}

