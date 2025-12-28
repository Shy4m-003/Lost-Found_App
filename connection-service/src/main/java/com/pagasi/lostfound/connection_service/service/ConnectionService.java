package com.pagasi.lostfound.connection_service.service;

import com.pagasi.lostfound.connection_service.model.Comments;
import com.pagasi.lostfound.connection_service.model.DeleteDto;
import com.pagasi.lostfound.connection_service.model.RequestPostDTO;

public interface ConnectionService {
    String post(RequestPostDTO dto);
    void comment(Comments comments);
    void delete(DeleteDto dto);
}
