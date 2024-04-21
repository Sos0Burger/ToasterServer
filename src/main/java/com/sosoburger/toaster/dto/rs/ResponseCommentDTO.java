package com.sosoburger.toaster.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResponseCommentDTO {
    private Integer id;
    private String text;
    private Long date;
    private Integer post;
    private FriendDTO creator;
    private Integer likes;
    private Boolean isLiked;
}
