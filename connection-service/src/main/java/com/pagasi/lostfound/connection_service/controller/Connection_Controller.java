package com.pagasi.lostfound.connection_service.controller;

import com.pagasi.lostfound.connection_service.model.Comments;
import com.pagasi.lostfound.connection_service.model.DeleteDto;
import com.pagasi.lostfound.connection_service.model.RequestPostDTO;
import com.pagasi.lostfound.connection_service.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/connection-service/")
public class Connection_Controller {

    @Autowired
    private ConnectionService connectionService;

    @PostMapping("/post-request")
    public ResponseEntity<Map<String, String>> post(@RequestBody RequestPostDTO dto){
        try{
            String postId = connectionService.post(dto);
            Map<String, String> response = Map.of(
                    "postId",postId,
                    "message", "Post created successfully"
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (IllegalStateException e){
            Map<String, String> errorResponse = Map.of(
                    "error", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT)

                    .body(errorResponse);
        }
    }
    @PostMapping("/post-comment")
    public ResponseEntity<?> postComment(@RequestBody Comments comments){
            connectionService.comment(comments);
            return ResponseEntity.ok("comment posted");
    }

    @DeleteMapping("/delete-comment")
    public ResponseEntity<?> deleteComment(@RequestBody DeleteDto dto) {
        try {
            connectionService.delete(dto);
            return ResponseEntity.ok("Comment deleted");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
