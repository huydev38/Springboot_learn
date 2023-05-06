package com.example.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass //đánh dấu đây là class cha, dùng để kế thừa chứ không tạo entity
@EntityListeners(AuditingEntityListener.class)
//Lưu ý ở đây cũng không được @Entity
public abstract class  TimeAuditable {
    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @LastModifiedDate
    private Date updatedAt;
}
