package com.zhixi.controller;

import com.zhixi.pojo.Books;
import com.zhixi.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhixi
 */
@Controller
@RequestMapping("/books")
public class BookController {

    // controller调dao层
    @Autowired
    @Qualifier("booksService")
    private BooksService booksService;

    // 1、查询全部的书籍，并跳转到书籍展示页面
    @RequestMapping("/allBook")
    public String queryBooks(Model model) {
        List<Books> list = booksService.queryBooks();

        model.addAttribute("list", list);
        return "queryAllBookPage";
    }

    // 2、添加书籍页面
    @RequestMapping("/toBookPage")
    public String toBookPage() {
        return "addBookPage";
    }

    // 2.1、添加书籍
    @RequestMapping("/addBook")
    public String addBook(Books books) {
        // 添加书籍
        booksService.addBook(books);
        // 跳转到查询全部书籍的页面【使用重定向】
        return "redirect:/books/allBook";
    }

    // 3、修改书籍页面
    @RequestMapping("/updateBookPage")
    // 这个id是从jsp页面拿到的，可以知道是对哪个用户进行修改数据
    public String updateBook(int id, Model model) {
        Books books = booksService.queryBookById(id);
        model.addAttribute("QUpdate", books);
        return "updateBookPage";
    }

    // 3.1、修改书籍
    @RequestMapping("/toUpdate")
    public String toUpdate(Books books) {
        booksService.updateBook(books);
        // 返回书籍首页
        return "redirect:/books/allBook";
    }

    // 4、删除书籍,删除完成后跳转到书籍首页
    @RequestMapping("/delBook")
    public String delBook(int id) {
        booksService.delBook(id);
        return "redirect:/books/allBook";
    }


    // 5、根据id查找书籍
    @RequestMapping("/queryById")
    // RequestParam是为了设置前端过来的请求与参数名不一致的情况
    public String queryBookById(@RequestParam("bookById") int id, Model model) {
        List<Books> list = new ArrayList<Books>();
        // 根据id查询
        Books books = booksService.queryBookById(id);
        list.add(books);

        if (books == null) {// 没有查询到书籍，就回到书籍首页并显示错误信息
            list = booksService.queryBooks();
            model.addAttribute("errorById", "没有查询到书籍！");
        }

        model.addAttribute("list", list);
        return "queryAllBookPage";
    }

    // 6、根据书名查找书籍
    @RequestMapping("/queryByName")
    public String queryBookByName(@RequestParam("bookName") String bookName, Model model) {
        List<Books> list = new ArrayList<Books>();
        Books books = booksService.queryBookByName(bookName);
        list.add(books);

        if (books == null) { // 没有查询到书籍，就回到书籍首页并显示错误信息
            list = booksService.queryBooks();
            model.addAttribute("errorByName", "没有查询到书籍！");
        }

        model.addAttribute("list", list);
        return "queryAllBookPage";
    }
}
