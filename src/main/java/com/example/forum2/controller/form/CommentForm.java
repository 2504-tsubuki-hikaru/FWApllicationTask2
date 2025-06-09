package com.example.forum2.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private int id;
    private String content;
    private int reportId;
    private Date createdDate;
    private Date updatedDate;
}
