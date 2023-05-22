package com.messenger.Messenger.dao;

import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private UserDAO sender;

    @ManyToOne
    @JoinColumn(name="receiver", nullable=false)
    private UserDAO receiver;

    @Column(name = "gmt_date")
    private Date date;

    @Column(name = "attachment")
    private List<String> attachments;

    public ResponseMessageDTO toDTO(){
        return new ResponseMessageDTO(text, sender.toDTO(), receiver.toDTO(), date.getTime(), attachments);
    }

}
