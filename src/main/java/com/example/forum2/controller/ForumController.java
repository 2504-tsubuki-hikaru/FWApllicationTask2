package com.example.forum2.controller;

import com.example.forum2.controller.form.CommentForm;
import com.example.forum2.controller.form.ReportForm;
import com.example.forum2.service.CommentService;
import com.example.forum2.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;
    @Autowired
    CommentService commentService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top(@RequestParam(name="start",required = false)String start,
                            @RequestParam(name="end",required = false)String end) throws ParseException {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport(start, end);
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        //　コメント一件取得
        List<CommentForm> commentData = commentService.findAllComment();
        // commentDataでmavにセットする。
        mav.addObject("comments",commentData);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm) {
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    //@PathVariable = /delete/{id}の{id}をdeleteContentメソッドの引数として渡す役割。
    public ModelAndView deleteContent(@PathVariable Integer id) {
        //投稿を削除
        reportService.deleteReport(id);
        //削除後、新規投稿全件取得をしたい
        return new ModelAndView("redirect:/");
    }

    /*
     * 編集画面表示処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        //編集する投稿のレコードを取得するメソッドの呼び出し。変種画面で表示させる為。
        ReportForm report = reportService.editReport(id);
        //mavにformModelという名前でreportをセットする。
        mav.addObject("formModel", report);
        //投稿編集画面に遷移する処理。
        mav.setViewName("/edit");
        return mav;
    }

    /*
     * 新規投稿編集処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateContent(@PathVariable Integer id,
                                      @ModelAttribute("formModel") ReportForm report) {
        //UrlParameterのidを更新するentityにセット
        report.setId(id);
        //新規投稿編集で渡ってきた値をDBにsaveする。
        reportService.saveReport(report);
        //全件取得メソッドへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント投稿処理
     */
    @PostMapping("/commentAdd")
    public ModelAndView addComment(@ModelAttribute("formModel") CommentForm comment) {
        // 投稿をテーブルに格納
        commentService.saveComment(comment);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント編集画面表示処理
     */
    @GetMapping("/commentEdit/{id}")
    public ModelAndView editComment(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        //新規投稿編集で渡ってきた値をDBにsaveする。
        CommentForm comment = commentService.editComment(id);
        //mavにformModelという名前でreportをセットする。
        mav.addObject("formModel", comment);
        //commentEdit(html)画面に遷移
        mav.setViewName("/commentEdit");
        return mav;
    }

    /*
     * コメント編集処理
     */
    @PutMapping("/commentUpdate/{id}")
    public ModelAndView commentUpdateContent(@PathVariable Integer id,
                                            @ModelAttribute("formModel") CommentForm comment) {
        //UrlParameterのidを更新するentityにセット
        comment.setId(id);
        //新規投稿編集で渡ってきた値をDBにsaveする。
        commentService.updateComment(comment);
        //全件取得メソッドへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント削除処理
     */
    @DeleteMapping("/commentDelete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        commentService.commentDeleteById(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}


