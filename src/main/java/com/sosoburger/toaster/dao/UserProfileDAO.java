package com.sosoburger.toaster.dao;

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

    @Column(name = "posts")
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<PostDAO> posts = new ArrayList<>();

    @Column
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<PostDAO> feed = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserDAO user;
    public ResponseUserDTO toDTO(List<FriendDTO> friendDTOs){
        return new ResponseUserDTO(id, nickname, friendDTOs , image==null?null:image.toDTO());
    }

    public FriendDTO toFriendDTO(){
        return new FriendDTO(id, nickname, image==null?null:image.toDTO());
    }

    public UserSettingsDTO toUserSettingsDTO(){ return new UserSettingsDTO(id, user.getEmail(), nickname, image==null?null:image.toDTO() );}
}
