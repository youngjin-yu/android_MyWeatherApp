package com.myproject.myweatherapp.thread;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParsingThread implements Runnable{
    @Override
    public void run() {
        try {
            /*Document document = Jsoup.connect("http://coronaboard.kr/en/").get();

            //동적으로 불러올 것
            Elements element = document.select("div.col-4.col-sm-4.col-md-3.text-center>p.confirmed.number");*/

            Document document = Jsoup.connect("https://search.naver.com/search.naver?where=news&sm=tab_jum&query=%EC%BD%94%EB%A1%9C%EB%82%98+%EB%B0%94%EC%9D%B4%EB%9F%AC%EC%8A%A4").get();


            //Elements elements1 = document.select("div.news.mynews.section._prs_nws>ul.type01");
            Elements elements2 = document.body().select("div.col-4 col-sm-4 col-md-2 text-center");
            //Element div = document.getElementById("col-4 col-sm-4 col-md-3 text-center");
            //Elements elementTopContainer = worldometers.getElementsByClass("confirmed number");

            //Elements elementRowDashboard = elementTopContainer.select("div.row dashboard world");

            /*for(Element element1 : elements1){
                System.out.println(element1);

            }*/

            /*Elements paragraphs=body.getElementsByTag("div").select("col-4 col-sm-4 col-md-3 text-center");
            for(Element paragraph : paragraphs){
                System.out.println(paragraph.text());
            }*/
            //Elements elements1 = worldometers.select("p").select(".confirmed number");



            //System.out.println(elements.text());

            /*for (int i = 0; i < broad_list.size(); i++){

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 게시글 제목
                String title = broad_list.get(i).getElementsByClass("inner").text().replace("[JAVA]","").replace("[Android]","").replace("[PHP]","").replace("[","\n[");

                // 작성자
                String writer = broad_list.get(i).getElementsByClass("m-tcol-c").text();

                // 게시 날짜
                String create_date = broad_list.get(i).getElementsByClass("date").text();

                // 조회수
                // 조회수와 댓글수의 tag class 가 모두 "num"으로 되어 있기 때문에 get(0)을 해서 조회수 값만 가지고 옴.
                // 조회수 값을 크롤링 할 때 숫자 앞에 "조회" 텍스트가 같이 와서 띄어쓰기로 쪼갠 후 숫자 값만 가지고 옴.
                int view_count = Integer.parseInt(broad_list.get(i).getElementsByClass("num").get(0).text().split(" ")[1]);

                // 댓글 수
                // 댓글수 값을 크롤링 할 때 숫자 앞에 "댓글" 텍스트가 같이 와서 띄어쓰기로 쪼갠 후 숫자 값만 가지고 옴.
                int reply_count = Integer.parseInt(broad_list.get(i).getElementsByClass("comment_area").text().split(" ")[1]);

                // 좋아요 수
                int like_count = Integer.parseInt(broad_list.get(i).getElementsByClass("u_cnt num-recomm").text());

                //게시글 url
                String post_url = "https://m.cafe.naver.com/teamnovaopen" + broad_list.get(i).getElementsByClass("tit").attr("href");

                Document image_tag = Jsoup.parse(broad_list.get(i).getElementsByClass("movie-img").html());

                // 동영상이 2개 이상 올린 게시글이 있어서 이미지태그에 "동영상" 이라는 텍스트가 포함 되면 썸네일 주소를 가지고 옴.
                // 동영상 1개 --> "동영상"
                // 동영상 2개 --> "동영상 1개의 추가 이미지가 있습니다"
                // 동영상 3개 --> "동영상 2개의 추가 이미지가 있습니다"
                String img_url = "";
                if(image_tag.text().contains("동영상")){
                    // "img"태그에 접근
                    Elements img_tag = image_tag.select("a > img");

                    // "img"태그의 "src" 값을 저장
                    img_url = img_tag.get(0).attr("src");

                }
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
