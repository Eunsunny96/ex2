package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void testInsertDummis() {//100개의 새로운 memo 객체 생성, memoRepository를 이용해 inesrt

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().menoText("sample" + i).build();//text에는 not null 조건임
            memoRepository.save(memo);
        });


    }

    @Test
    void testSelect() {
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);//Optional 타입으로 반환됨

        System.out.println("=====================");//findById()를 실행한 순한 sql 처리

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }

    }

    @Transactional
    @Test
    void testSelect2() {
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno); //getOne() 은 실제 객체가 필요한 순간까지 sql을 실행하지 않음

        System.out.println("=============="); //먼저 실행되고
        System.out.println(memo);//실제객체를 사용하는 순간 sql 실행
    }

    @Test
    void testUpdate() {//수정 작업

        Memo memo = Memo.builder().mno(100L).menoText("update Text").build();//selct로 해당 번호를  확인한 후 update
        //해당 @Id를 가진 객체가 있다면 update 없다면 insert실행한다
        System.out.println(memoRepository.save(memo));

    }

    @Test
    void testDelete() {//삭제
        Long mno = 100L;//삭제 하려는 번호
        memoRepository.deleteById(mno);//객체가 있는지 확인후 삭제
        //만일 데이터가 존재하지 않으면 예외 발생(에러남)

    }

    @Test
    void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);//1페이지에 10개 ,페이지는 0부터 시작함

        Page<Memo> result = memoRepository.findAll(pageable);//목록만 가져오는게 아니고,실제 페이지에 필요한 전체 데이터의 갯수도 가져옴

        System.out.println(result);//첫번째 쿼리 : limit구문 사용해 페이징 처리, 두번째 쿼리 count를 이용해 전체 갯수 처리 ,returntype을 Page<엔티티 타입>지정

        System.out.println("================"); //Pagd<엔티티 타입>을 이용해서 주로 사용하는 메서드들

        System.out.println("Total Pages : " + result.getTotalPages());// 총 몇페이지

        System.out.println("Total count : " + result.getTotalElements()); //전체 갯수

        System.out.println("Page Number : " + result.getNumber()); //현제 페이지 번호 (0부터 시작)

        System.out.println("Page Size : " + result.getSize()); //페이지당 데이터 갯수

        System.out.println("has next page? : " + result.hasNext()); //다음 페이지 존재 여부

        System.out.println("fist page : " + result.isFirst()); //시작 페이지(0) 여부

        System.out.println("========================");
        for (Memo memo : result.getContent()) { //List<엔티티 타입>으로 처리하거나 Stream <엔티티 타입>을 반환하는 get을 이용할수 있음
            System.out.println(memo);
        }


    }

    @Test
    void testSort() {//정렬
        Sort sort1 = Sort.by("mno").descending();//mno를 역순으로 정렬

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);

        });
    }

    @Test
    void testQueryMethods(){ //쿼리 메소드
        List<Memo> list=memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L);

        for(Memo memo: list){
            System.out.println(memo);
        }
    }

    @Test
    void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result =  memoRepository.findByMnoBetween(10L,50L,pageable);

        result.get().forEach(System.out::println);
    }


}