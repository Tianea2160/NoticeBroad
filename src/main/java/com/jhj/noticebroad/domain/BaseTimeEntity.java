package com.jhj.noticebroad.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// 해당클래스를 상속할 경우 jpa entity에서 해당 칼럼도 인식하도록 해주는 어노테이션
@MappedSuperclass
//결국에 중복 되는 컬럼을 따로 상위 클래스를 만들어서 상속할 수 있게 하고 이를 구현하는 단계
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
