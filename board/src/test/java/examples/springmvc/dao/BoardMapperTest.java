package examples.springmvc.dao;

import examples.springmvc.config.ApplicationConfig;
import examples.springmvc.dto.Board;
import examples.springmvc.dto.SearchCondition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional // test코드가 실행된 후, 롤백되기 때문에 원본 데이터는 문제가 발생하지 않는다. 해당 어노테이션을 붙이지 않으면 25번째 줄에서 오류가 발생할 수 있다. 테스트 메소드의 실행순서는 메소드 선언 순이 아니기 때문이다.
public class BoardMapperTest {
    @Autowired
    BoardMapper boardMapper;

    @Test
    public void getBoards() throws Exception{
        SearchCondition searchCondition = new SearchCondition();
        searchCondition.setCount(5);
        searchCondition.setOffset(5);
        List<Board> boards = boardMapper.getBoards(searchCondition);

        // 직접 값을 출력해서 눈으로 확인하는 것이 아니라, Assert클래스를 이용해서 값이 일치 하는지 확인하는 것이 좋다.
        // 시간관계상 Assert클래스에 대해서 자세히 다루기 어렵지만, Test클래스를 작성하려면 Assert에 대해 찾아보고 학습을 해야한다.
        System.out.println(boards.size());
        for(Board board: boards){
            System.out.println(board);
        }
    }

    @Test
    public void getBoard() throws Exception{
        Board board = boardMapper.getBoard(1L);
        System.out.println(board);
    }

    @Test
    public void addBoard() throws Exception{
        Board board = new Board();
        board.setContent("hello content");
        board.setReadCount(0);
        board.setTitle("hello");
        board.setUserId("carami");
        int count = boardMapper.addBoard(board);
        System.out.println(count);
        System.out.println(board); // id에 자동으로 값이 들어가 있다.
        System.out.println(boardMapper.getBoard(board.getId()));
    }

    @Test
    public void getBoardCount() throws Exception{
        int boardCount = boardMapper.getBoardCount();
        System.out.println(boardCount);
    }
}

