package com.messenger.Messenger.dao;

import com.messenger.Messenger.dto.rs.FriendDTO;
import com.messenger.Messenger.dto.rs.ResponseUserDTO;
import com.messenger.Messenger.dto.rs.UserSettingsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "pending")
    private List<Integer> pending;
    @Column(name = "friends")
    private List<Integer> friends;
    @Column(name = "sent")
    private List<Integer> sent;
    @Column(name = "image")
    private String image;
    @Column(name = "sent_messages")
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<MessageDAO> sentMessages = new HashSet<>();
    @Column(name = "received_messages")
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private Set<MessageDAO> receivedMessages  = new HashSet<>();
    @Column(name = "firebase_key")
    private String firebaseToken;

    @Column(name = "posts")
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private Set<PostDAO> posts = new HashSet<>();

    @Column
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<PostDAO> feed = new HashSet<>();
    public ResponseUserDTO toDTO(List<FriendDTO> friendDTOs){
        return new ResponseUserDTO(id, nickname, friendDTOs , image);
    }

    public FriendDTO toFriendDTO(){
        return new FriendDTO(id, nickname, image);
    }

    public UserSettingsDTO toUserSettingsDTO(){return new UserSettingsDTO(id, email, nickname, image );}
}
