package com.sosoburger.toaster.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.sosoburger.toaster.dao.MessageDAO;
import com.sosoburger.toaster.dao.UserProfileDAO;
import com.sosoburger.toaster.dto.rq.RequestEditMessageDTO;
import com.sosoburger.toaster.dto.rq.RequestMessageDTO;
import com.sosoburger.toaster.exception.NotFoundException;
import com.sosoburger.toaster.repository.FileRepository;
import com.sosoburger.toaster.repository.MessageRepository;
import com.sosoburger.toaster.retrofit.ImaggaApiImpl;
import com.sosoburger.toaster.service.FileService;
import com.sosoburger.toaster.service.MessageService;
import com.sosoburger.toaster.service.UserProfileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    final ImaggaApiImpl firebaseApi = new ImaggaApiImpl();


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
            if (messageDAO.getReceiver().getFirebaseToken()!=null){
                var notification = Message.builder()
                        .putData("sender", messageDAO.getSender().getNickname() == null ?
                                messageDAO.getSender().getId().toString() :
                                messageDAO.getSender().getNickname())
                        .putData("message", messageDAO.getText() == null || messageDAO.getText().isBlank() ?
                                messageDAO.getAttachments().size() + " Фотографий" :
                                messageDAO.getText())
                        .setToken(messageDAO.getReceiver().getFirebaseToken()).build();
                try {
                    String response = FirebaseMessaging.getInstance().send(notification);
                    System.out.println("Successfully sent message: " + response);
                } catch (FirebaseMessagingException ex){
                    System.out.println("Failed message: " + ex.getMessage());
                }

            }

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
        var response = new ArrayList<MessageDAO>();
        messages.forEach(item -> {
            response.add(item.copy());
            if (userid.equals(item.getReceiver().getId()) && !item.getRead()) {
                item.setRead(true);
                messageRepository.save(item);
            }

        });
        return response;
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
        if (messageRepository.existsById(id)) {
            return messageRepository.findById(id).get();
        }
        throw new NotFoundException("Сообщение не найдено");
    }

    @Override
    public MessageDAO read(Integer id) {
        var message = get(id);
        message.setRead(true);
        return messageRepository.save(message);
    }
}
