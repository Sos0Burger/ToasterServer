package com.sosoburger.toaster.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResponsePostDTO {
    private Integer id;

    private String text;

    private FriendDTO creator;

    private Long date;

    private List<ResponseFileDTO> attachments;

    private Integer likes;

    private Boolean isLiked;

    private Integer comments;

    private ResponseCommentDTO popularComment;

}
