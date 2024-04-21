package com.sosoburger.toaster.dao;

import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "gmt_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostDAO post;

    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    private UserProfileDAO creator;

    @ManyToMany
    @JoinTable(
            name = "comments_users",
            joinColumns = @JoinColumn(
                    name = "comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<UserProfileDAO> users = new ArrayList<>();

    public ResponseCommentDTO toDTO(Integer userProfile) {
        return new ResponseCommentDTO(
                id,
                text,
                date.getTime(),
                post.getId(),
                creator.toFriendDTO(),
                users.size(),
                users
                        .stream()
                        .anyMatch(item -> item.getId().equals(userProfile)));
    }

}
