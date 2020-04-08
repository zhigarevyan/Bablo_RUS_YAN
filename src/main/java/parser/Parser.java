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

    public Parser(File file) {
        this.file = file;
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
    Element searchLine(){
        try {
            Elements form = doc.getElementsByClass("text-success text-right");
            for(Element elem : form){
                String s = elem.text();
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void searchPlayers(){
        Document doc = null;
        try {
            doc = Jsoup.parse(file,"UTF-8");
            Elements namesOfPlayers = doc.getElementsByClass("text-success text-right");
            Elements form = doc.getElementsByClass("small");
            for(Element elem : form){
                String s = elem.text();
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File f = new File("D:\\UNIVER\\java\\Bablo_RUS_YAN\\src\\main\\resources\\Результаты матчей.html");
        Parser parser = new Parser(f);
        //parser.searchScore();
        parser.searchPlayers();

    }

}
