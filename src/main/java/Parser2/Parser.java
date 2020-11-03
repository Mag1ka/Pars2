package Parser2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
// Метод открытия страниц
public class Parser {
    public Document getPage(Integer pageNumber) throws IOException {
        String url = "https://www.sql.ru/forum/job-offers/" + pageNumber;
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }
}
