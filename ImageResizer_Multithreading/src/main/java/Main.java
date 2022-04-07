import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    private static int newWidth = 300;

    public static void main(String[] args) {
        String srcFolder = "/Users/dafva/IdeaProjects/src";
        String dstFolder = "/Users/dafva/IdeaProjects/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of available processors: " + availableProcessors);

        assert files != null;
        int part = files.length / availableProcessors;


        //Чтобы не писать для каждого кусочка отдельно, создам цикл для всех, кроме последнего (потому что у него иначе считается длина массива)
        //В таком случае мне не нужно заранее узнавать количество ядер - цикл посчитает для любого количества
        for(int i = 0; i < availableProcessors - 1; i++) {
            File[] filesNew = new File[part];
            System.arraycopy(files, part * i, filesNew, 0, filesNew.length);
            ImageResizer resizer = new ImageResizer(filesNew, newWidth, dstFolder, start);
            new Thread(resizer).start();
        }

        //Отдельно для последнего кусочка
        File[] filesLast = new File[files.length - part * (availableProcessors - 1)];
        System.arraycopy(files, part * (availableProcessors - 1), filesLast, 0, filesLast.length);
        ImageResizer resizer8 = new ImageResizer(filesLast, newWidth, dstFolder, start);
        new Thread(resizer8).start();

    }
}
