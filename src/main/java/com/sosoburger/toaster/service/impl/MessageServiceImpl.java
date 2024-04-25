package com.sosoburger.toaster.service.impl;

import com.sosoburger.toaster.dao.MessageDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.NotificationContent;
import com.sosoburger.toaster.dto.rq.RequestEditMessageDTO;
import com.sosoburger.toaster.dto.rq.RequestMessageDTO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.FileRepository;
import com.sosoburger.toaster.repository.MessageRepository;
import com.sosoburger.toaster.retrofit.FirebaseApiImpl;
import com.sosoburger.toaster.service.FileService;
import com.sosoburger.toaster.service.MessageService;
import com.sosoburger.toaster.service.UserProfileService;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    final FirebaseApiImpl firebaseApi = new FirebaseApiImpl();

    @SneakyThrows
    @Override
    public MessageDAO create(RequestMessageDTO message, Integer sender) {
        if (fileRepository.findAllById(message.getAttachments()).size() == message.getAttachments().size()) {
            Integer id = messageRepository.save(
                    message
                            .toDAO(
                                    userProfileService.getUser(sender),
                                    userProfileService.getUser(message.getReceiver()),
                                    fileService))
                    .getId();
            MessageDAO messageDAO = messageRepository.findById(id).get();
            NotificationContent notificationContent = new NotificationContent(messageDAO.getReceiver().getFirebaseToken(), messageDAO.toDTO());
            Call<ResponseBody> response = firebaseApi.sendNotification(notificationContent);
            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println(response.code());
                    } else {
                        System.out.println(response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
            return messageDAO;
        }
        throw new NotFoundException("Файла с таким ID не существует");
    }

    @Override
    public List<MessageDAO> getAll() {
        return messageRepository.findAll();
    }

    @SneakyThrows
    @Override
    public List<MessageDAO> getDialog(Integer userid, Integer companion, Pageable pageable) {
        var user = userProfileService.getUser(userid);
        var companionDAO = userProfileService.getUser(companion);
        var messages = messageRepository.findBySenderAndReceiver(user, companionDAO, pageable);
        messages.forEach(item -> {
            if(!userid.equals(item.getSender().getId())){
                item.setRead(true);
                messageRepository.save(item);
            }

        });
        return new ArrayList<>(messages.getContent());
    }

    @Override
    public MessageDAO getLatest(UserProfileDAO sender, UserProfileDAO receiver) {
        return messageRepository.findLastMessageBetweenUsers(sender, receiver, PageRequest.of(0, 1)).get().findFirst().get();
    }

    @Override
    public Integer getUnread(UserProfileDAO sender, UserProfileDAO receiver) {
        return messageRepository.countUnreadMessages(sender, receiver);
    }

    @Override
    public MessageDAO edit(Integer id, RequestEditMessageDTO message) {
        var savedMessage = get(id);
        savedMessage.setText(message.getText());
        savedMessage.setAttachments(new HashSet<>(fileRepository.findAllById(message.getAttachments())));
        return messageRepository.save(savedMessage);
    }

    @Override
    public void delete(Integer id) {
        messageRepository.delete(get(id));
    }

    @Override
    public MessageDAO get(Integer id) {
        if (messageRepository.existsById(id)){
            return messageRepository.findById(id).get();
        }
        throw new NotFoundException("Сообщение не найдено");
    }
}
