package com.sosoburger.toaster.dao;

import com.sosoburger.toaster.dto.rs.ResponseFileDTO;
import com.sosoburger.toaster.dto.rs.ResponsePostDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name="creator", nullable=false)
    private UserProfileDAO creator;

    @Column(name = "gmt_date")
    private Date date;

    @Column(name = "attachment")
    @OneToMany
    @JoinColumn(name = "post_id")
    private List<FileDAO> attachments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "posts_users",
            joinColumns = @JoinColumn(
                    name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private List<UserProfileDAO> users = new ArrayList<>();


    @OneToMany(mappedBy = "post")
    private List<CommentDAO> comments = new ArrayList<>();



    public ResponsePostDTO toDTO(Integer userProfile){
        List<ResponseFileDTO> attachmentsDTO = new ArrayList<>();
        for (FileDAO file:attachments
        ) {
            attachmentsDTO.add(file.toDTO());
        }
        CommentDAO popular = null;
        for (var item: comments){
            if(popular==null||popular.getUsers().size()<item.getUsers().size()){
                popular = item;
            }
        }
        return new ResponsePostDTO(
                id,
                text,
                creator.toFriendDTO(),
                date.getTime(),
                attachmentsDTO,
                users.size(),
                users.stream().anyMatch(item-> item.getId().equals(userProfile)),
                comments.size(),
                popular==null?null:popular.toDTO(userProfile));
    }

}
