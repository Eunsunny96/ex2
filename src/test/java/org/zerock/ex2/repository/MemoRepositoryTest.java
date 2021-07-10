package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void testInsertDummis(){//100개의 새로운 memo 객체 생성, memoRepository를 이용해 inesrt

        IntStream.rangeClosed(1,100).forEach(i ->{
            Memo memo =Memo.builder().menoText("sample"+i).build();//text에는 not null 조건임
            memoRepository.save(memo);
        });


    }

    @Test
    void testSelect(){
        Long mno =100L;
        Optional<Memo> result=memoRepository.findById(mno);//Optional 타입으로 반환됨

        System.out.println("=====================");//findById()를 실행한 순한 sql 처리

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }

    }
    @Transactional
    @Test
    void testSelect2(){
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno); //getOne() 은 실제 객체가 필요한 순간까지 sql을 실행하지 않음

        System.out.println("=============="); //먼저 실행되고
        System.out.println(memo);//실제객체를 사용하는 순간 sql 실행
    }

    @Test
    void testUpdate(){//수정 작업

        Memo memo = Memo.builder().mno(100L).menoText("update Text").build();//selct로 해당 번호를  확인한 후 update
        //해당 @Id를 가진 객체가 있다면 update 없다면 insert실행한다
        System.out.println(memoRepository.save(memo));

    }

    @Test
    void testDelete(){//삭제
        Long mno = 100L;//삭제 하려는 번호
        memoRepository.deleteById(mno);//객체가 있는지 확인후 삭제
        //만일 데이터가 존재하지 않으면 예외 발생(에러남)

    }

    @Test
    void testPageDefault(){
        Pageable pageable= PageRequest.of(0,10);//1페이지에 10개 ,페이지는 0부터 시작함
        Page<Memo> result =memoRepository.findAll(pageable);
        System.out.println(result);
    }
}