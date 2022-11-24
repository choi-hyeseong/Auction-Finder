package com.comet.auctionfinder.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FileEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private String modifiedName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
