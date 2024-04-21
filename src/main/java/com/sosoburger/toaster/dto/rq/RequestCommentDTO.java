package com.sosoburger.toaster.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RequestCommentDTO {
    private String text;
    private Long date;
}
