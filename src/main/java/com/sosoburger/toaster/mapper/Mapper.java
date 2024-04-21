package com.sosoburger.toaster.mapper;

import com.sosoburger.toaster.dao.CommentDAO;
import com.sosoburger.toaster.dto.rs.ResponseCommentDTO;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static List<ResponseCommentDTO> commentsToDTOList(List<CommentDAO> comments, Integer userProfile){
        List<ResponseCommentDTO> response = new ArrayList<>();
        comments.forEach(item->{response.add(item.toDTO(userProfile));});
        return response;
    }
}
