package com.messenger.Messenger.dao;

import com.messenger.Messenger.dto.rs.ResponseUserDTO;
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
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<MessageDAO> sentMessages = new HashSet<>();
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private Set<MessageDAO> receivedMessages  = new HashSet<>();


    public ResponseUserDTO toDTO(){
        return new ResponseUserDTO(id, email, nickname, friends, image);
    }
}