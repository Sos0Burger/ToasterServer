package com.sosoburger.toaster.dto.rq;

import com.sosoburger.toaster.dao.FileDAO;
import com.sosoburger.toaster.dao.PostDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostDTO {

    private String text;
    private Long date;
    List<Integer> attachments;

    public PostDAO toDAO(UserProfileDAO creator, List<FileDAO> attachments){
        return new PostDAO(null, text, creator, new Date(date), attachments, new ArrayList<>(), new ArrayList<>());
    }
}
