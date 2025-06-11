package com.example.forum2.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private int id;
    @NotBlank(message="コメント内容を入力して下さい")
    private String content;
    private int reportId;
    private Date createdDate;
    private Date updatedDate;
}
