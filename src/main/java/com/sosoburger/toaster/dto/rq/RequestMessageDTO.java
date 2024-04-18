package com.sosoburger.toaster.dto.rq;

import com.sosoburger.toaster.config.SpringConfig;
import com.sosoburger.toaster.dao.FileDAO;
import com.sosoburger.toaster.dao.MessageDAO;
import com.sosoburger.toaster.dao.UserDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.repository.FileRepository;
import com.sosoburger.toaster.repository.UserProfileRepository;
import com.sosoburger.toaster.service.FileService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessageDTO {
    private static final UserProfileRepository USER_PROFILE_REPOSITORY = SpringConfig.contextProvider().getApplicationContext().getBean("userRepository", UserProfileRepository.class);
    private static final FileRepository fileRepository = SpringConfig.contextProvider().getApplicationContext().getBean("fileRepository", FileRepository.class);
    @Schema(description = "Текст сообщения", example = "Привет!")
    private String text;
    @NotNull
    @Schema(description = "Id получателя", example = "2")
    private Integer receiver;

    @NotNull
    @Schema(description = "Время отправки сообщение в по ГРИНВИЧУ!!!!!", example = "1684778400000")
    private Long date;
    @ArraySchema
    List<Integer> attachments;
    public MessageDAO toDAO(UserProfileDAO sender, UserProfileDAO receiver, FileService fileService) {
        Set<FileDAO> fileDAOS = new HashSet<>();
        for (Integer id: attachments
             ) {
            fileDAOS.add(fileService.findById(id));
        }
        return new MessageDAO(null, text, sender, receiver,new Date(date), fileDAOS);
    }
}
