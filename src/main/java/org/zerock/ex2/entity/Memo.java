package org.zerock.ex2.entity;


import lombok.*;

import javax.persistence.*;

@Entity //jpa로 관리되는 entity 객체  ,자동으로 테이블 생성 가능, 멤버 변수에 다라 자동으로 칼럼들 생성
@Table(name="tbl_memo") //데이터 베이스 상태에서 entity class를 어떠한 테이블로 생성할건지, 인덱스 생성설정도 가능
@Getter
@Builder //객체를 생성 ,@Builder 쓰려면 @AllArgsConstructor,@NoArgsConstructor 같이 있어야된
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Memo {
    @Id //@Entity는 pk설정 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) //사용자가 입력하는 값 x -> 자동생성 ,identity - 사용하는 db 키생성 결정
    private Long mno;

    @Column(length = 200, nullable = false )//다양한 속성 지정
    //@Column과 반대 @Transient db에 칼럼으로 생성되지 않는 필드 위에

    private String menoText;
}
