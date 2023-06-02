package com.messenger.Messenger.dto.rq;

import com.google.gson.annotations.SerializedName;
import com.messenger.Messenger.dto.rs.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationContent {
    @SerializedName("to")
    private String to;

    @SerializedName("data")
    private ResponseMessageDTO data;
}
