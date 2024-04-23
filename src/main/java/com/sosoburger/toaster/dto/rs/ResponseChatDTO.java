package com.sosoburger.toaster.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResponseChatDTO {
    private Integer user;
    private String nickname;
    private String latest;
    private Long date;
    private Integer unread;
    private ResponseFileDTO image;
    private Boolean online;

}
