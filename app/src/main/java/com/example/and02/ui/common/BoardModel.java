package com.example.and02.ui.common;

import org.json.JSONArray;

import java.io.Serializable;

public class BoardModel implements Serializable {

    private int boardNo;

    private String writerNo;
    private BoardWriterModel writer;
    private String contentNo;
    private String title;
    private String content;
    private boolean replyWritable;
    private int likeCnt;
    private int viewCnt;
    private String registeDate;
    private String modifyDate;
    private String deleteDate;
    private boolean deleteYn;
    private JSONArray attachFiles;
    private String attachFile;

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getWriterNo() {
        return writerNo;
    }

    public void setWriterNo(String writerNo) {
        this.writerNo = writerNo;
    }

    public BoardWriterModel getWriter() {
        return writer;
    }

    public void setWriter(BoardWriterModel writer) {
        this.writer = writer;
    }

    public String getContentNo() {
        return contentNo;
    }

    public void setContentNo(String contentNo) {
        this.contentNo = contentNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isReplyWritable() {
        return replyWritable;
    }

    public void setReplyWritable(boolean replyWritable) {
        this.replyWritable = replyWritable;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getRegisteDate() {
        return registeDate;
    }

    public void setRegisteDate(String registeDate) {
        this.registeDate = registeDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(boolean deleteYn) {
        this.deleteYn = deleteYn;
    }

    public JSONArray getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(JSONArray attachFiles) {
        this.attachFiles = attachFiles;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }
}
