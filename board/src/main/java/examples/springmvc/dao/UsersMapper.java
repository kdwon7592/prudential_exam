package examples.springmvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;

import examples.springmvc.dto.User;
import examples.springmvc.mapper.Mapper;

@Mapper
public interface UsersMapper {
	public List<User> getUsers();
	public User getUser(String userId);
	public void addUser(User user);
}
