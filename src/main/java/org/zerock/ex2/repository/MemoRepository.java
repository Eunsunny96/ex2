package org.zerock.ex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex2.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo,Long> {//<Entity type의 정보, @Id 타입> 인터페이스 선언으로도 자동으로 스프링 bean으로 등록
}
