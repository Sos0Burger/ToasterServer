package com.sosoburger.toaster.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sosoburger.toaster.dto.rs.ResponseWebsocketMessageDTO;
import com.sosoburger.toaster.service.MessageService;
import com.sosoburger.toaster.service.impl.UserServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandler extends BinaryWebSocketHandler {

    private final Map<Integer, WebSocketSession> userSessions = new HashMap<>();
    private final AuthenticationManager authenticationManager;


    private final UserServiceImpl userService;

    private final MessageService messageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Integer count = 229;

    public WebSocketHandler(AuthenticationManager authenticationManager, UserServiceImpl userService, MessageService messageService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        Integer userId = extractUserId(session);
        if (userId != null && userSessions.get(userId) == null) {
            userSessions.put(userId, session);
        }
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        Integer userId = extractUserId(session);
        if (userId != null && userSessions.get(userId).equals(session)) {
            userSessions.remove(userId);
        }
    }

    @Override
    protected void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) throws Exception {
        var messageDTO = objectMapper.readValue(message.getPayload().array(), ResponseWebsocketMessageDTO.class);
        var response = objectMapper.writeValueAsBytes(messageDTO);
        var sender = userSessions.get(messageDTO.getSender().getId());
        var receiver = userSessions.get(messageDTO.getReceiver().getId());
        if (sender!=null && sender.isOpen()) {
            sender.sendMessage(new BinaryMessage(response));
        }
        if (receiver!=null && receiver.isOpen()) {
            receiver.sendMessage(new BinaryMessage(response));
        }

    }

    private Integer extractUserId(WebSocketSession session) {
        String authorizationHeader = session.getHandshakeHeaders().getFirst("Authorization");
        Authentication authentication = authenticateUser(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    private Authentication authenticateUser(String authorizationHeader) {
        String[] credentials = parseAuthorizationHeader(authorizationHeader);
        String username = credentials[0];
        String password = credentials[1];

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authentication);
    }

    private String[] parseAuthorizationHeader(String authorizationHeader) {

        String[] parts = authorizationHeader.split("\\s+");
        String credentialsBase64 = parts[1];
        String credentials = new String(Base64.getDecoder().decode(credentialsBase64), StandardCharsets.UTF_8);
        return credentials.split(":");
    }
}
