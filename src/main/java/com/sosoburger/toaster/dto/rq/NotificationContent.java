package com.sosoburger.toaster.dto.rq;

import com.google.gson.annotations.SerializedName;
import com.sosoburger.toaster.dto.rs.ResponseMessageDTO;
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
