package com.sosoburger.toaster;

import com.google.auth.oauth2.GoogleCredentials;
import com.sosoburger.toaster.exception.NotFoundException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FireBase {
    public static GoogleCredentials getCredentials(){
        try {
            File file = ResourceUtils.getFile("classpath:" + "toasterappsocial.json");
            return GoogleCredentials.fromStream(new FileInputStream(file));
        } catch (IOException e){
            throw new NotFoundException("");
        }
    }
}
