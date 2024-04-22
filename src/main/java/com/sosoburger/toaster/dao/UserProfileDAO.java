package com.sosoburger.toaster.dao;

import com.sosoburger.toaster.FriendStatusEnum;
import com.sosoburger.toaster.dto.rs.FriendDTO;
import com.sosoburger.toaster.dto.rs.ResponseUserDTO;
import com.sosoburger.toaster.dto.rs.UserSettingsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "pending")
    private List<Integer> pending;
    @Column(name = "friends")
    private List<Integer> friends;
    @Column(name = "sent")
    private List<Integer> sent;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image")
    private FileDAO image;

    @Column(name = "sent_messages")
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<MessageDAO> sentMessages = new ArrayList<>();

    @Column(name = "received_messages")
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<MessageDAO> receivedMessages  = new ArrayList<>();

    @Column(name = "firebase_key")
    private String firebaseToken;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<PostDAO> posts = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<PostDAO> feed = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserDAO user;

    @ManyToMany (mappedBy = "users")
    private List<PostDAO> likedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<CommentDAO> comments = new ArrayList<>();

    @ManyToMany (mappedBy = "users")
    private List<CommentDAO> likedComments = new ArrayList<>();
    public ResponseUserDTO toDTO(List<FriendDTO> friendDTOs, UserProfileDAO userProfileDAO){
        if (Objects.equals(userProfileDAO.id, id)){
            return new ResponseUserDTO(id, nickname, friendDTOs , image==null?null:image.toDTO(), FriendStatusEnum.SELF);
        }
        FriendStatusEnum status = FriendStatusEnum.NOTHING;
        if (friends.stream().anyMatch(item-> item.equals(userProfileDAO.getId()))){
            status = FriendStatusEnum.FRIEND;
        }
        if (sent.stream().anyMatch(item-> item.equals(userProfileDAO.getId()))){
            status = FriendStatusEnum.PENDING;
        }
        if (pending.stream().anyMatch(item-> item.equals(userProfileDAO.getId()))){
            status = FriendStatusEnum.SENT;
        }

        return new ResponseUserDTO(id, nickname, friendDTOs , image==null?null:image.toDTO(), status);
    }

    public FriendDTO toFriendDTO(){
        return new FriendDTO(id, nickname, image==null?null:image.toDTO());
    }

    public UserSettingsDTO toUserSettingsDTO(){ return new UserSettingsDTO(id, user.getEmail(), nickname, image==null?null:image.toDTO() );}
}
