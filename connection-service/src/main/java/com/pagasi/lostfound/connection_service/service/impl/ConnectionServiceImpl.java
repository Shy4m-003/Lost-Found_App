package com.pagasi.lostfound.connection_service.service.impl;

import com.pagasi.lostfound.connection_service.converter.Comment_Converter;
import com.pagasi.lostfound.connection_service.converter.RequestPostConverter;
import com.pagasi.lostfound.connection_service.entities.CommentType;
import com.pagasi.lostfound.connection_service.entities.CommentsEntity;
import com.pagasi.lostfound.connection_service.entities.PostStatus;
import com.pagasi.lostfound.connection_service.entities.RequestPostEntity;
import com.pagasi.lostfound.connection_service.exceptions.InvalidOperationException;
import com.pagasi.lostfound.connection_service.model.Comments;
import com.pagasi.lostfound.connection_service.model.DeleteDto;
import com.pagasi.lostfound.connection_service.model.RequestPostDTO;
import com.pagasi.lostfound.connection_service.repository.CommentRepository;
import com.pagasi.lostfound.connection_service.repository.PostRepository;
import com.pagasi.lostfound.connection_service.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private PostRepository requestPostRepository;
    @Autowired
    private RequestPostConverter postConverter;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private Comment_Converter converter;

    @Override
    public String post(RequestPostDTO dto) {
        Optional<RequestPostEntity> existingOpt = requestPostRepository
                .findByUserIdAndContentAndCategoryAndLocationTag(
                        dto.getUserId(),
                        dto.getContent(),
                        dto.getCategory(),
                        dto.getLocationTag()
                );

        if (existingOpt.isPresent()) {
            RequestPostEntity existing = existingOpt.get();
            if (existing.getStatus() == PostStatus.ACTIVE) {
                return existing.getId();
            }
            existing.setStatus(PostStatus.ACTIVE);
            existing.setCreatedAt(LocalDateTime.now()); // optional: reset timestamp
            requestPostRepository.save(existing);
            return existing.getId();
        }
        RequestPostEntity newPost = postConverter.dtoToEntity(dto);
        requestPostRepository.save(newPost);
        return newPost.getId();
    }

    @Override
    public void comment(Comments comments) {
        Optional<CommentsEntity> existing = commentRepository.findByRequestIdAndUserId(comments.getRequestId(), comments.getUserId());
        if(comments.getType() == CommentType.ITEM_REFERENCE){
            if (comments.getReferencedItemId() == null || comments.getReferencedItemId().isBlank()) {
                throw new IllegalArgumentException("Referenced item id is required for ITEM_REFERENCE comment");
            }
        }
        if(existing.isPresent()){
            CommentsEntity comment = existing.get();
            comment.setContent(comments.getContent());
            comment.setType(comments.getType());
            comment.setReferencedItemId(comments.getReferencedItemId());
            comment.setUpdatedAt(LocalDateTime.now());
            commentRepository.save(comment);
        }else{
            CommentsEntity newComment = converter.toEntity(comments);
            commentRepository.save(newComment);
            RequestPostEntity requestPostEntity =
                    requestPostRepository.findById(comments.getRequestId())
                            .orElseThrow(() -> new InvalidOperationException("Post Not Found"));

            requestPostEntity.setCommentCount(requestPostEntity.getCommentCount() + 1);
            requestPostRepository.save(requestPostEntity);
        }
    }

    @Override
    public void delete(DeleteDto dto) {
        RequestPostEntity post = requestPostRepository.findById(dto.getPostId()).orElseThrow(() -> new InvalidOperationException("Post Not Found"));
        CommentsEntity comment = commentRepository.findById(dto.getCommentId()).orElseThrow(() -> new InvalidOperationException("Comment not found"));
        if (!comment.getUserId().equals(dto.getId()) &&
                !post.getUserId().equals(dto.getId())) {
            throw new IllegalStateException("Only post owner or commenter can delete comments");
        }
        commentRepository.delete(comment);
        post.setCommentCount(post.getCommentCount() - 1);
        requestPostRepository.save(post);
    }
}
