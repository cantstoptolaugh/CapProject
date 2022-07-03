package com.cap.project.demo.domain;

import com.cap.project.demo.domain.enums.AttachmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    private String originFilename;

    private String storeFilename;

    // join with Expert table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="expert_id" , foreignKey = @ForeignKey(name = "fk_attachment_expert"))
    private Expert expert;

    // join with User table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name = "fk_attachment_user"))
    private User user;


    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @Builder
    public Attachment(String originFileName, String storePath, AttachmentType attachmentType) {
        this.originFilename = originFileName;
        this.storeFilename = storePath;
        this.attachmentType = attachmentType;

    }

    public void addExpert(Expert expert) {
        this.expert = expert;
        expert.getAttachedFiles().add(this);
    }
}
