package com.messenger.Messenger.service.impl;

import com.messenger.Messenger.dao.MessageDAO;
import com.messenger.Messenger.dto.rq.NotificationContent;
import com.messenger.Messenger.dto.rq.RequestMessageDTO;
import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import com.messenger.Messenger.exception.ExceptionMessage;
import com.messenger.Messenger.repository.FileRepository;
import com.messenger.Messenger.repository.MessageRepository;
import com.messenger.Messenger.repository.UserRepository;
import com.messenger.Messenger.retrofit.FirebaseApiImpl;
import com.messenger.Messenger.service.MessageService;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;

    FirebaseApiImpl firebaseApi = new FirebaseApiImpl();

    @Override
    public ResponseEntity<?> create(RequestMessageDTO message) {
        if (userRepository.existsById(message.getSender()) && userRepository.existsById(message.getReceiver())) {
            if (fileRepository.findAllById(message.getAttachments()).size() == message.getAttachments().size()) {

                Integer id = messageRepository.save(message.toDAO()).getId();
                MessageDAO messageDAO = messageRepository.findById(id).get();
                NotificationContent notificationContent = new NotificationContent(messageDAO.getReceiver().getFirebaseToken(), messageDAO.toDTO());
                Call<ResponseBody> response = firebaseApi.sendNotification(notificationContent);
                response.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            System.out.println(response.code());
                        }
                        else {
                            System.out.println(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                } );
                return new ResponseEntity<>(messageDAO.toDTO(),HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new ExceptionMessage("Файла с таким ID не существует"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователя с таким ID не существует"), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> getAll() {
        var messages = messageRepository.findAll();
        List<ResponseMessageDTO> DTOs = new ArrayList<>();
        for (MessageDAO message : messages
        ) {
            DTOs.add(message.toDTO());
        }
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDialog(Integer userid, Integer companionid, Pageable pageable) {
        if (userRepository.existsById(userid) && userRepository.existsById(companionid)) {
            var user = userRepository.findById(userid).get();
            var companion = userRepository.findById(companionid).get();
            List<MessageDAO> messageDAOS = new ArrayList<>();
            //находим все сообщение между ними
            messageDAOS.addAll(messageRepository.findBySenderAndReceiver(user, companion, pageable).getContent());

            List<ResponseMessageDTO> messageDTOS = new ArrayList<>();
            for (MessageDAO message : messageDAOS
            ) {
                messageDTOS.add(message.toDTO());
            }
            messageDTOS.sort(Comparator.comparing(ResponseMessageDTO::getDate).reversed());
            return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionMessage("Пользователя с таким ID не существует"), HttpStatus.NOT_FOUND);
    }
}
