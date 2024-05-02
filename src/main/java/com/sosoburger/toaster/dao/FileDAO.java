package com.sosoburger.toaster.dao;

import com.sosoburger.toaster.dto.rs.ResponseFileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "hash", columnDefinition = "TEXT")
    private String hash;

    @OneToMany(mappedBy = "file")
    private List<TagDAO> tags;

    public ResponseFileDTO toDTO(){
        return new ResponseFileDTO(id, name, type, data.length);
    }
}
