package com.fastcampus.ch4.domain;

public class PageHandler {
    int totalCnt;//총 게시물 수
    int pageSize;//한 페이지의 크기
    int naviSize = 10;//페이지 내비게이션 크기
    int totalPage;//현재 페이지의 개수
    int page;//현재 페이지
    int endPage;
    int beginPage;
    boolean showPrev;
    boolean showNexk;
    public PageHandler(int totalCnt, int page){
        this(totalCnt,page,10);
    }
    public PageHandler(int totalCnt, int page,int pageSize){
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;
    }
}
