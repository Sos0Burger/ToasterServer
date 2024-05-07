package com.sosoburger.toaster;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.audit.ResourceLocation;
import com.sosoburger.toaster.exception.NotFoundException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FireBase {
    public static GoogleCredentials getCredentials(){
        try {
            return GoogleCredentials.fromStream(new ClassPathResource("toasterappsocial.json").getInputStream());
        } catch (IOException e){
            throw new NotFoundException("");
        }
    }
}
