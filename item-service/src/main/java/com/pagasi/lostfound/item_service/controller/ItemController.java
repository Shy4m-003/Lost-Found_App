package com.pagasi.lostfound.item_service.controller;
import com.pagasi.lostfound.item_service.model.ItemDto;
import com.pagasi.lostfound.item_service.model.LogDto;
import com.pagasi.lostfound.item_service.service.impl.ItemServiceImpl;
import com.pagasi.lostfound.item_service.service.impl.ItemVerificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/items/v1")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private ItemVerificationServiceImpl verificationService;

    @PostMapping("/post-item")
    public ResponseEntity<String> post(@RequestBody ItemDto itemDto){
        try{
            itemService.post(itemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Item Posted Successfully");
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
    @PatchMapping("/{id}/update-item/{userId}")
    public ResponseEntity<Map<String,Object>> patch(@PathVariable String id, @RequestBody Map<String, Object> updates, @PathVariable String userId){
            itemService.updateItem(id, updates, userId);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Item updated successfully",
                        "status", 200
                )
        );

    }
    @DeleteMapping("/{id}/delete-item/{userId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id,@PathVariable String userId){
        itemService.deleteItem(id, userId);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Item deleted successfully",
                        "status", 200
                )
        );
    }
    @PostMapping("/claimed")
    public ResponseEntity<Map<String, Object>> claim(@RequestBody LogDto logDto){
        verificationService.claimedBy(logDto);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Item claimed successfully",
                        "status", 200
                )
        );
    }
}
