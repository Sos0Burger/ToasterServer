package com.messenger.Messenger.dao;

import com.messenger.Messenger.dto.rs.ResponseFileDTO;
import com.messenger.Messenger.dto.rs.ResponsePostDTO;
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
public class PostDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    String text;

    @ManyToOne
    @JoinColumn(name="creator", nullable=false)
    private UserDAO creator;

    @Column(name = "gmt_date")
    private Date date;

    @Column(name = "attachment")
    @OneToMany
    @JoinColumn(name = "post_id")
    private Set<FileDAO> attachments = new HashSet<>();

    public ResponsePostDTO toDTO(){
        List<ResponseFileDTO> attachmentsDTO = new ArrayList<>();
        for (FileDAO file:attachments
        ) {
            attachmentsDTO.add(file.toDTO());
        }
        return new ResponsePostDTO(id, text, creator.toFriendDTO(), date.getTime(), attachmentsDTO);
    }

}
