package parser;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

@Data
public class Parser {
    private static File file;
    private static Document doc;

    public Parser(File file, Document doc) {
        this.file = file;
        this.doc = doc;
    }

    void searchScore(){
        Document doc = null;
        try {
            doc = Jsoup.parse(file,"UTF-8");
            Elements form = doc.getElementsByClass("text-success text-right");
            for(Element elem : form){
                String s = elem.text();
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void searchLine(){
            Elements form = doc.getElementsByClass("list-group-item");
            for(Element elem : form){
                searchPlayers(elem);
            }
    }

    void searchPlayers(Element line){

        Elements players = line.getElementsByClass("small");
            for(Element elem : players){
                String s = elem.text();
                System.out.println(s);
            }
    }


    public static void main(String[] args) {
        try {
            File f = new File("D:\\UNIVER\\java\\Bablo_RUS_YAN\\src\\main\\resources\\Результаты матчей.html");
            Document doc = Jsoup.parse(f, "UTF-8");
            Parser parser = new Parser(f, doc);
            //parser.searchScore();
            //parser.searchPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
