package com.sosoburger.toaster.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostDTO {
    private Integer id;

    private String text;

    private FriendDTO creator;

    private Long date;

    private List<ResponseFileDTO> attachments;
}
