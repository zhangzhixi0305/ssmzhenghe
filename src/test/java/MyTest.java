import com.zhixi.controller.BookController;
import com.zhixi.pojo.Books;
import com.zhixi.service.BooksService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BooksService serviceImpl = context.getBean("booksService", BooksService.class);
        List<Books> list = serviceImpl.queryBooks();
        for (Books books : list) {
            System.out.println(books);
        }
    }
}
