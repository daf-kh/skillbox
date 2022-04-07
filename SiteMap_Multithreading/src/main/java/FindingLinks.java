import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class FindingLinks extends RecursiveTask<Set<String>> {
    private static String site;
    private static String domain;
    private final Set<String> linksList;
    private final List<FindingLinks> tasks = new ArrayList<>();

    public FindingLinks(String site, Set<String> linksList) {
        FindingLinks.site = site;
        //Находим общий для всех ссылок домен, чтобы по нему искать дочерние ссылки
        domain = site.substring(0, site.indexOf("."));
        this.linksList = linksList;
    }

    @Override
    protected Set<String> compute() {
        try {
            Document document = Jsoup
                    .connect(site)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .maxBodySize(0)
                    .get();

            Thread.sleep(100);

            //Находим ссылки в коде
            Elements links = document.select("a");
            for (Element element : links) {
                String link = element.attr("abs:href");
                //Идем работать и анализировать каждую найденную ссылку
                //Здесь же правим ссылки со слэшем в конце (из-за них некоторые ссылки могли двоиться
                //и неправильно выставлялось количество табов)
                analyzeLink(clearLink(link));
            }
            //Ждем окончания работы всех потоков
            for (FindingLinks task : tasks) {
                System.out.println("Waiting for the end of " + Thread.currentThread());
                linksList.addAll(task.join());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Can't connect " + site);
        }
        return linksList;
    }

    private void analyzeLink(String link) {
        if (isLinkOk(link)) {
            //В этом случае добавляем ссылку в список и запускаем новое задание
            linksList.add(link);
            FindingLinks findingLinks = new FindingLinks(link, linksList);
            findingLinks.fork();
            //И добавляем его в список заданий для ожидания в compute
            tasks.add(findingLinks);
        }
    }

    //Проверяем, есть ли ссылка в списке, начинается ли с заданного домена и не содержит ли лишних символов
    private boolean isLinkOk(String link) {
        return !linksList.contains(link) && link.startsWith(domain) && !link.contains("#") && !link.endsWith(".pdf")
                && !link.endsWith(".png") && !link.contains("?");
    }

    private String clearLink(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '/') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}


