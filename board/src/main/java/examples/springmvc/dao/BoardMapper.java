package examples.springmvc.dao;

import examples.springmvc.dto.Board;
import examples.springmvc.dto.SearchCondition;
import examples.springmvc.mapper.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public int addBoard(Board board);
    public Board getBoard(Long id);
    public List<Board> getBoards(SearchCondition searchCondition);
    public int getBoardCount();
}

