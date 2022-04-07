import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static String site = "https://skillbox.ru/";

    public static void main(String[] args) throws IOException {

        FileWriter fileForSiteMap = new FileWriter("src/main/resources/SiteMap.txt");
        Set<String> linksList = new TreeSet<>();
        FindingLinks findingLinks = new FindingLinks(site, linksList);
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        forkJoinPool.invoke(findingLinks).forEach(link -> {
            int countTabs = 0;
            for (int i = 0; i < link.length(); i++) {
                //Записываем количество табов в зависимости от количества слэшей в адресе
                if (link.charAt(i) == '/') {
                    countTabs++;
                }
            }
            //Т.к. в начале ссылок два слэша точно есть, вычитаем их для подсчета табов
            for (int i = 0; i < countTabs - 2; i++) {
                try {
                    fileForSiteMap.write("\t");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileForSiteMap.write(link + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileForSiteMap.close();
        System.out.println("File is done");
    }
}
