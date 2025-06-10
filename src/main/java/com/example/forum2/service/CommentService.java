package com.example.forum2.service;

import com.example.forum2.controller.form.CommentForm;
import com.example.forum2.controller.form.ReportForm;
import com.example.forum2.repository.CommentRepository;
import com.example.forum2.repository.ReportRepository;
import com.example.forum2.repository.entity.Comment;
import com.example.forum2.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * レコード追加
     */
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setContent(reqComment.getContent());
        comment.setReportId(reqComment.getReportId());
        //引数には現在時刻が入ってないので、ここで直接commentにsetする必要がある。
        comment.setCreatedDate(new Date());
        comment.setUpdatedDate(new Date());
        return comment;
    }

    /*
     * コメント一件取得
     */
    public List<CommentForm> findAllComment() {
        List<Comment> results = commentRepository.findAllByOrderByIdDesc();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setContent(result.getContent());
            comment.setReportId(result.getReportId());
            comment.setCreatedDate(result.getCreatedDate());
            comment.setUpdatedDate(result.getUpdatedDate());
            comments.add(comment);
        }
        return comments;

    }
    /*
     * 編集したい投稿のレコード1件取得
     */
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> Comments = setCommentForm(results);
        //リストの最初の一件目だけ返す。
        return Comments.get(0);
    }

    /*
     * コメント編集処理
     */
    public void updateComment(CommentForm comment) {
        //setCommentEntityを再利用する
        Comment Comments= setCommentEntity(comment);
        commentRepository.save(Comments);
    }

    //投稿を削除
    public void commentDeleteById (Integer id) {
        commentRepository.deleteById(id);
    }
}

