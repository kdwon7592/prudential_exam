package examples.springmvc.dao;

import examples.springmvc.config.ApplicationConfig;
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
public class UserMapperTest {
}

