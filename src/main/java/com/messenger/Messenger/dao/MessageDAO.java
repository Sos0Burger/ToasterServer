package com.messenger.Messenger.dao;

import com.messenger.Messenger.dto.rs.ResponseFileDTO;
import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDAO{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    String text;

    @ManyToOne
    @JoinColumn(name="sender", nullable=false)
    private UserProfileDAO sender;

    @ManyToOne
    @JoinColumn(name="receiver", nullable=false)
    private UserProfileDAO receiver;

    @Column(name = "gmt_date")
    private Date date;

    @Column(name = "attachment")
    @OneToMany
    @JoinColumn(name = "message_id")
    private Set<FileDAO> attachments = new HashSet<>();

    public ResponseMessageDTO toDTO(){
        List<ResponseFileDTO> attachmentsDTO = new ArrayList<>();
        for (FileDAO file:attachments
             ) {
            attachmentsDTO.add(file.toDTO());
        }

        return new ResponseMessageDTO(text, sender.toFriendDTO(), receiver.toFriendDTO(), date.getTime(), attachmentsDTO);
    }

}
