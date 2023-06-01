package com.messenger.Messenger.dto.rq;

import com.messenger.Messenger.config.SpringConfiguration;
import com.messenger.Messenger.dao.FileDAO;
import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.repository.UserRepository;
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
    private static final UserRepository userRepository = SpringConfiguration.contextProvider().getApplicationContext().getBean("userRepository", UserRepository.class);
    private static final FileRepository fileRepository = SpringConfiguration.contextProvider().getApplicationContext().getBean("fileRepository", FileRepository.class);
    @Schema(description = "Текст сообщения", example = "Привет!")
    private String text;

    @NotNull
    @Schema(description = "Id отправителя", example = "1")
    private Integer sender;

    @NotNull
    @Schema(description = "Id получателя", example = "2")
    private Integer receiver;

    @NotNull
    @Schema(description = "Время отправки сообщение в по ГРИНВИЧУ!!!!!", example = "1684778400000")
    private Long date;
    @ArraySchema
    List<Integer> attachments;
    public MessageDAO toDAO() {
        Set<FileDAO> fileDAOS = new HashSet<>();
        for (Integer id: attachments
             ) {
            fileDAOS.add(fileRepository.findById(id).get());
        }
        return new MessageDAO(null, text, userRepository.findById(sender).get(), userRepository.findById(receiver).get(),new Date(date), fileDAOS);
    }
}
