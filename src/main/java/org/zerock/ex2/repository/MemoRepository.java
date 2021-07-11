package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex2.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {//<Entity type의 정보, @Id 타입> 인터페이스 선언으로도 자동으로 스프링 bean으로 등록

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);//메서드 이름이 길어지고 혼동하기 쉬움


    Page<Memo> findByMnoBetween(Long from, Long to , Pageable pageable); //쿼리 메서드는 pageable결합해서 사용 가능

    void deleteMemoByMnoLessThan(Long num);



}
