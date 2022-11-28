package com.comet.auctionfinder.model;

import com.comet.auctionfinder.dto.FileRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member author;

    private String title;

    private String content;

    private int viewCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<FileEntity> fileList = new ArrayList<>();

    public void update(String title, String content, List<FileRequestDto> requestDtos) {
        this.title = title;
        this.content = content;
        requestDtos.forEach((file) -> addFile(file.toEntity()));
    }

    public void addFile(FileEntity entity) {
        if (entity.getBoard() != null)
            entity.getBoard().fileList.remove(entity);
        entity.setBoard(this);
        fileList.add(entity);
    }

    public void removeFile(FileEntity entity) {
        if (entity.getBoard() != null)
            entity.getBoard().fileList.remove(entity);
        entity.setBoard(null);
    }
    public void addReply(Reply reply) {
        if (reply.getBoard() != null)
            reply.getBoard().replyList.remove(reply);
        replyList.add(reply);
        reply.setBoard(this);
    }

    public void removeReply(Reply reply) {
        if (reply.getBoard() != null)
            reply.getBoard().replyList.remove(reply);
        reply.setBoard(null);
    }

}
