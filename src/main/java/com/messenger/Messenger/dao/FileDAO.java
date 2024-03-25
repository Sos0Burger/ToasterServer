package com.messenger.Messenger.dao;

import com.messenger.Messenger.dto.rs.ResponseFileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDAO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "type")
    private String type;

    @Column(name = "data")
    @Lob
    private byte[] data;

    public ResponseFileDTO toDTO(){
        return new ResponseFileDTO(id, name, type, data.length);
    }
}
