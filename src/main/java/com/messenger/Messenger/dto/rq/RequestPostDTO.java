package com.messenger.Messenger.dto.rq;

import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.dao.PostDAO;
import com.messenger.Messenger.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostDTO {

    private String text;
    private Integer creator;
    private Long date;
    List<Integer> attachments;

    public PostDAO toDAO(UserDAO creator, Set<FileDAO> attachments){
        return new PostDAO(null, text, creator, new Date(date), attachments);
    }
}
