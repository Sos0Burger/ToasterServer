package com.sosoburger.toaster.dto.rq;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RequestEditMessageDTO {
    @Schema(description = "Текст сообщения", example = "Привет!")
    private String text;
    @ArraySchema
    List<Integer> attachments;
}
